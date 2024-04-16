import {Component, OnInit} from "@angular/core";
import {RacesService} from "../races.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatButton} from "@angular/material/button";
import {DatePipe, formatDate, NgForOf, NgIf} from "@angular/common";
import {LoggedUserService} from "../../users/logged-user.service";
import {RacesCreateUpdateDialogComponent} from "../races-create-update-dialog/races-create-update-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RaceUserInfoDto, RaceUserStatus} from "../races.model";

@Component({
  selector: 'app-race-detail',
  standalone: true,
  templateUrl: './race-detail-screen.component.html',
  styleUrls: ['./race-detail-screen.component.css'],
  imports: [
    MatButton,
    NgForOf,
    NgIf,
    DatePipe
  ]
})
export class RaceDetailScreenComponent implements OnInit {

  public race: any;
  public userId: any;
  public signedInfo: RaceUserInfoDto | null = null;
  public canDoChanges: boolean = false;
  public isOpenForRegistration: boolean = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private snackBar: MatSnackBar,
              private dialog: MatDialog,
              private racesService: RacesService,
              private loggedUserService: LoggedUserService) {
  }

  ngOnInit(): void {
    const raceId = this.route.snapshot.paramMap.get('raceId');
    this.racesService.getRace(Number(raceId)).subscribe(race => {
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
      this.racesService.isSignedUp(Number(raceId)).subscribe(signedInfo => {
        this.signedInfo = signedInfo;
      }, error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      })
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
    const crDialogRef = this.dialog.open(RacesCreateUpdateDialogComponent,
      {data: this.race});
    crDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.race = result;
      }
    });
  }

  public signUpForRace(): void {
    this.racesService.signUpActiveUser(this.race.id).subscribe(signedInfo => {
      this.signedInfo = signedInfo;
    }, error => {
      let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
    })
  }

  protected readonly RaceUserStatus = RaceUserStatus;
}
