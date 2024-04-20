import {Component, OnInit, ViewChild} from "@angular/core";
import {RacesService} from "../races.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatButton} from "@angular/material/button";
import {DatePipe, formatDate, NgForOf, NgIf} from "@angular/common";
import {LoggedUserService} from "../../users/logged-user.service";
import {RacesCreateUpdateDialogComponent} from "../races-create-update-dialog/races-create-update-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RacesSignUpDialogComponent} from "../races-sign-up-dialog/races-sign-up-dialog.component";
import {MatTab, MatTabContent, MatTabGroup} from "@angular/material/tabs";
import {SearchType} from "../../core/support/table/table.model";
import {CrewsTableComponent} from "../crews-table/crews-table.component";

@Component({
  selector: 'app-race-detail',
  standalone: true,
  templateUrl: './race-detail-screen.component.html',
  styleUrls: ['./race-detail-screen.component.css'],
  imports: [
    MatButton,
    NgForOf,
    NgIf,
    DatePipe,
    MatTab,
    MatTabContent,
    MatTabGroup,
    CrewsTableComponent
  ]
})
export class RaceDetailScreenComponent implements OnInit {

  @ViewChild('crewsTable') crewsTableComponent?: CrewsTableComponent;

  public race: any;

  public userId: any;

  public canDoChanges: boolean = false;

  public isOpenForRegistration: boolean = false;

  public filters: Array<any> = [];

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

  public signUpShips() {
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
