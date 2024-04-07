import {ChangeDetectorRef, Component, OnInit} from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router, RouterLink } from "@angular/router";
import { LoggedUserService } from "../logged-user.service";
import { UsersService } from "../users.service";
import { NgFor, NgIf } from "@angular/common";
import {MatButton} from "@angular/material/button";
import {UserUpdateDialogComponent} from "../user-update-dialog/user-update-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RolesUpdateDialogComponent} from "../roles-update-dialog/roles-update-dialog.component";

@Component({
  selector: 'app-user-detail',
  standalone: true,
  templateUrl: './user-detail-screen.component.html',
  styleUrls: ['./user-detail-screen.component.css'],
  imports: [
    NgFor,
    NgIf,
    RouterLink,
    MatButton
  ]
})
export class UserDetailScreenComponent implements OnInit {

  public user: any;

  public userRoles: any;

  public allRoles: any;

  public isActiveAdmin: boolean = false;

  constructor(protected loggedUserService: LoggedUserService,
              protected usersService: UsersService,
              private route: ActivatedRoute,
              private router: Router,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private cd: ChangeDetectorRef) {

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
                this.userRoles = result.roles;
              }
              for (let i = 0; i < result.roles.length; i++) {
                if (result.roles.at(i)?.role == 'ADMIN') {
                  this.isActiveAdmin = true;
                }
              }
              if (this.isActiveAdmin) {
                this.usersService.getAllRoles().subscribe(result => {
                  this.allRoles = result.roles;
                }, error => {
                  let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
                });
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
              this.userRoles = result.roles;
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

  public updateUser(): void {
    const uuDialogRef = this.dialog.open(UserUpdateDialogComponent,
      {data: this.user});
    uuDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.user = result;
        this.cd.detectChanges();
      }
    })
  }

  public updateRoles(): void {
    const ruDialogRef = this.dialog.open(RolesUpdateDialogComponent,
      {data: {
          id: this.user.id,
          allRoles: this.allRoles,
          userRoles: this.userRoles
        }});
    ruDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.userRoles = result.roles;
        this.cd.detectChanges();
      }
    })
  }
}
