import {Component, OnInit} from "@angular/core";
import {RacesService} from "../races.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatButton} from "@angular/material/button";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {LoggedUserService} from "../../users/logged-user.service";

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
  public canDoChanges: boolean = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private snackBar: MatSnackBar,
              private racesService: RacesService,
              private loggedUserService: LoggedUserService) {
  }

  ngOnInit(): void {
    const raceId = this.route.snapshot.paramMap.get('raceId');
    this.racesService.getRace(Number(raceId)).subscribe(race => {
      this.race = race;
      this.loggedUserService.getLoggedUser().subscribe(user => {
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
    // TODO
  }
}
