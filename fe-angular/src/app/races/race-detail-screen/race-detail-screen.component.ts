import {Component, OnInit, ViewChild} from "@angular/core";
import {RacesService} from "../races.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatButtonModule} from "@angular/material/button";
import {DatePipe, formatDate, NgForOf, NgIf} from "@angular/common";
import {LoggedUserService} from "../../users/logged-user.service";
import {RacesCreateUpdateDialogComponent} from "../races-create-update-dialog/races-create-update-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RacesSignUpDialogComponent} from "../races-sign-up-dialog/races-sign-up-dialog.component";
import {MatTabsModule} from "@angular/material/tabs";
import {SearchType} from "../../core/support/table/table.model";
import {CrewsTableComponent} from "../crews-table/crews-table.component";
import {RegistrationStatus, UserRaceInfoDto} from "../races.model";
import {ParticipantsTableComponent} from "../participants-table/participants-table.component";
import {ResultsTableComponent} from "../results-table/results-table.component";
import {MapComponent} from "../map/map.component";

@Component({
  selector: 'app-race-detail',
  standalone: true,
  templateUrl: './race-detail-screen.component.html',
  styleUrls: ['./race-detail-screen.component.css'],
  imports: [
    MatButtonModule,
    NgForOf,
    NgIf,
    DatePipe,
    MatTabsModule,
    CrewsTableComponent,
    ParticipantsTableComponent,
    ResultsTableComponent,
    MapComponent
  ]
})
export class RaceDetailScreenComponent implements OnInit {

  @ViewChild('crewsTable') crewsTableComponent?: CrewsTableComponent;

  @ViewChild('participantsTable') participantsTableComponent?: ParticipantsTableComponent;

  @ViewChild('resultsTable') resultsTableComponent?: ResultsTableComponent;

  public race: any;

  public userRace: UserRaceInfoDto | undefined;

  public userId: any;

  public canDoChanges: boolean = false;

  public isAfterRace: boolean = false;

  public isOpenForRegistration: boolean = false;

  public isCourse: boolean = false;

  public filters: Array<any> = [];

  protected readonly RegistrationStatus = RegistrationStatus;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private snackBar: MatSnackBar,
              private dialog: MatDialog,
              private racesService: RacesService,
              private loggedUserService: LoggedUserService) {
  }

  ngOnInit(): void {
    const raceId = Number(this.route.snapshot.paramMap.get('raceId'));
    this.filters.push({
      title: '',
      column: 'raceId',
      type: SearchType.STRING,
      value: raceId
    });
    this.racesService.getRace(raceId).subscribe(race => {
      this.race = race;
      this.isOpenForRegistration = formatDate(new Date(), 'yyyy-MM-dd', 'en_US')
        <= formatDate(this.race.signUpUntil, 'yyyy-MM-dd', 'en_US');
      this.isAfterRace = formatDate(new Date(), 'yyyy-MM-dd', 'en_US')
        >= formatDate(this.race.date, 'yyyy-MM-dd', 'en_US');
      this.loggedUserService.getLoggedUser().subscribe(user => {
        this.userId = user.id;
        if (this.race.mainOrganizerId === user.id) {
          this.canDoChanges = true;
        } else {
          this.loggedUserService.getLoggedUserRoles().subscribe(roles => {
            for (let i = 0; i < roles.roles.length; i++) {
              if (roles.roles.at(i)?.role == 'ADMIN') {
                this.canDoChanges = true;
              }
            }
          });
        }
      });
    }, error => {
      if (error.status === 401) {
        let snackBarRef = this.snackBar.open('User unauthorised', 'Log In');
        snackBarRef.afterDismissed().subscribe(
          () => this.router.navigate(['/login'])
        );
      } else {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      }
    });
    this.racesService.getActiveUserRaceInfo(raceId).subscribe(result => {
      this.userRace = result;
    }, error => {
      let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
    })
    this.racesService.getCourse(raceId).subscribe(result => {
      if (result) {
        this.isCourse = true;
      }
    })
  }

  public updateRaceDetail(): void {
    const dialogRef = this.dialog.open(RacesCreateUpdateDialogComponent,
      {data: this.race});
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.race = result;
        this.isOpenForRegistration = formatDate(new Date(), 'yyyy-MM-dd', 'en_US')
          <= formatDate(this.race.signUpUntil, 'yyyy-MM-dd', 'en_US');
        this.crewsTableComponent?.table?.tableDataRefresh();
      }
    });
  }

  public signUpShips(): void {
    this.racesService.getShipsForRace(this.race.id).subscribe(result => {
      const dialogRef = this.dialog.open(RacesSignUpDialogComponent,
        {
          data: {
            raceId: this.race.id,
            ships: result.crews
          }
        });
      dialogRef.afterClosed().subscribe(result => {
        this.crewsTableComponent?.table?.tableDataRefresh();
      });
    }, error => {
      this.snackBar.open(error.status + ': ' + error.error, 'X');
    });
  }
}
