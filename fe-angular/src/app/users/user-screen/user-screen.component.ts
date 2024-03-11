import { Component, OnInit } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { LoggedUserService } from "../logged-user.service";
import { UsersService } from "../users.service";
import { NgFor, NgIf } from "@angular/common";

@Component({
  selector: 'app-user',
  standalone: true,
  templateUrl: './user-screen.component.html',
  styleUrls: ['./user-screen.component.css'],
  imports: [
    NgFor,
    NgIf,
    RouterLink
  ]
})
export class UserScreenComponent implements OnInit {

  public user: any;

  public roles: any;

  public isActiveAdmin: boolean = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              protected loggedUserService: LoggedUserService,
              protected usersService: UsersService,
              private snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const userId = routeParams.get('userId');
    this.loggedUserService.getLoggedUser().subscribe(
      result => {
        if (result == null) {
          let snackBarRef = this.snackBar.open('User unauthorised', 'Log In');
          snackBarRef.afterDismissed().subscribe(
            () => this.router.navigate(['/login'])
          );
        } else {
          if (userId == null) {
            this.user = result;
          }
          this.usersService.getUserRoles(result.id).subscribe(
            result => {
              if (userId == null) {
                this.roles = result.roles;
              }
              for (let i = 0; i < result.roles.length; i++) {
                if (result.roles.at(i)?.role == 'ADMIN') {
                  this.isActiveAdmin = true;
                }
              }
            },
            error => {
              let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
            });
        }
      },
      error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    if (userId != null) {
      this.usersService.getUser(Number(userId)).subscribe(
        result => {
          this.user = result;
          this.usersService.getUserRoles(this.user.id).subscribe(
            result => {
              this.roles = result.roles;
            })
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
        });
    }
  }
}
