import {Component, OnInit, ViewChild} from "@angular/core";
import {CrewDetailDto, RegistrationStatus, UserRaceInfoDto} from "../races.model";
import {MatButtonModule} from "@angular/material/button";
import {ActivatedRoute, Router} from "@angular/router";
import {RacesService} from "../races.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {formatDate, NgIf} from "@angular/common";
import {CrewUsersTableComponent} from "../crew-users-table/crew-users-table.component";
import {SearchType} from "../../core/support/table/table.model";
import {LoggedUserService} from "../../users/logged-user.service";

@Component({
  selector: 'app-crew-detail',
  standalone: true,
  templateUrl: 'crew-detail-screen.component.html',
  styleUrls: ['crew-detail-screen.component.css'],
  imports: [
    MatButtonModule,
    NgIf,
    CrewUsersTableComponent
  ]
})
export class CrewDetailScreenComponent implements OnInit {

  @ViewChild("crewUsersTable") crewUserTableComponent?: CrewUsersTableComponent;

  public crew: CrewDetailDto | undefined;

  public userRace: UserRaceInfoDto | undefined;

  public filters: Array<any> = [];

  public canManageCrewUsers: boolean = false;

  public isOpenForRegistration: boolean = false;

  protected readonly RegistrationStatus = RegistrationStatus;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private racesService: RacesService,
              private loggedUserService: LoggedUserService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    const crewId = Number(this.route.snapshot.paramMap.get('crewId'));
    this.filters.push({
      title: '',
      column: 'crewId',
      type: SearchType.STRING,
      value: crewId
    });
    this.racesService.getCrew(crewId).subscribe(crewDetailDto => {
      this.crew = crewDetailDto;
      this.racesService.getRace(crewDetailDto.raceId).subscribe(raceDetailDto => {
        this.isOpenForRegistration = formatDate(new Date(), 'yyyy-MM-dd', 'en_US')
          <= formatDate(raceDetailDto.signUpUntil, 'yyyy-MM-dd', 'en_US');
      })
      this.loggedUserService.getLoggedUser().subscribe(user => {
        if (crewDetailDto.shipOwnerId === user.id) {
          this.canManageCrewUsers = true;
        } else {
          this.loggedUserService.getLoggedUserRoles().subscribe(roles => {
            for (let i = 0; i < roles.roles.length; i++) {
              if (roles.roles.at(i)?.role == 'ADMIN') {
                this.canManageCrewUsers = true;
              }
            }
          });
        }
      });
      this.racesService.getActiveUserRaceInfo(crewDetailDto.raceId).subscribe(userRaceInfoDto => {
        this.userRace = userRaceInfoDto;
      }, error => {
        this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    }, error => {
      if (error.status === 401) {
        let snackBarRef = this.snackBar.open('User unauthorised', 'Log In');
        snackBarRef.afterDismissed().subscribe(
          () => this.router.navigate(['/login'])
        );
      } else {
        this.snackBar.open(error.status + ': ' + error.error, 'X');
      }
    });
  }

  public applyToCrew(): void {
    if (this.crew) {
      this.racesService.applyToCrew(this.crew.id).subscribe(result => {
        this.userRace = result;
        this.crewUserTableComponent?.table?.tableDataRefresh();
      }, error => {
        this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    }
  }

  public leaveCrew(): void {
    if (this.crew) {
      this.racesService.leaveCrew(this.crew.id).subscribe(result => {
        this.userRace = undefined;
        this.crewUserTableComponent?.table?.tableDataRefresh();
      }, error => {
        this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    }
  }
}
