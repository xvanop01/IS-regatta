import { Component, OnInit } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { RacesService } from "../races.service";
import { LoggedUserService } from "src/app/users/logged-user.service";

@Component({
  selector: 'app-races',
  templateUrl: './races-screen.component.html',
  styleUrls: ['./races-screen.component.css']
})
export class RacesScreenComponent implements OnInit {
  
  protected isOrganizer: boolean = false;

  protected races: any;

  constructor(private router: Router,
              private racesService: RacesService,
              private loggedUserService: LoggedUserService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.racesService.getAllRaces().subscribe(
      result => {
        this.races = result.races;
      },
      error => {
        if (error.status === 401) {
          let snackBarRef = this.snackBar.open('User unauthorised', 'Log In');
          snackBarRef.afterDismissed().subscribe(
            () => this.router.navigate(['/login'])
          );
        } else {
          let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
        }
      }
    )

    this.loggedUserService.getLoggedUserRoles().subscribe(
      roles => {
        if (roles != null) {
          for (const role in roles) {
            if (role === 'ADMIN' || role === 'ORGANIZER') {
              this.isOrganizer = true;
            }
          }
        }
      }
    )
  }
}