import {Component, OnInit} from "@angular/core";
import {CrewDetailDto, RegistrationStatus, UserRaceInfoDto} from "../races.model";
import {MatButtonModule} from "@angular/material/button";
import {ActivatedRoute, Router} from "@angular/router";
import {RacesService} from "../races.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-crew-detail',
  standalone: true,
  templateUrl: 'crew-detail-screen.component.html',
  styleUrls: ['crew-detail-screen.component.css'],
  imports: [
    MatButtonModule,
    NgIf
  ]
})
export class CrewDetailScreenComponent implements OnInit {

  public crew: CrewDetailDto | undefined;

  public userRace: UserRaceInfoDto | undefined;

  protected readonly RegistrationStatus = RegistrationStatus;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private racesService: RacesService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    const crewId = Number(this.route.snapshot.paramMap.get('crewId'));
    this.racesService.getCrew(crewId).subscribe(crewDetailDto => {
      this.crew = crewDetailDto;
      this.racesService.getActiveUserRaceInfo(crewDetailDto.raceId).subscribe(userRaceInfoDto => {
        this.userRace = userRaceInfoDto;
        console.log(this.userRace);
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
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      }
    });
  }

  public applyToCrew(): void {

  }

  public leaveCrew(): void {

  }
}
