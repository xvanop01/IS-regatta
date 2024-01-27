import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router } from "@angular/router";
import { LoggedUserService } from "../logged-user.service";
import { CreateUserDto, UserDetailDto } from "../users.model";
import { UsersService } from "../users.service";

enum Action {
  RedirectToDetail = 'DETAIL',
  EditUser = 'EDIT',
  ChangePermissions = 'PERMISSIONS'
}

@Component({
  selector: 'app-users-management',
  templateUrl: './users-management-screen.component.html',
  styleUrls: ['./users-management-screen.component.css']
})
export class UsersManagementScreenComponent implements OnInit {

  protected users: any;

  protected readonly Action = Action;

  constructor(private router: Router,
              private usersService: UsersService,
              private snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
    this.usersService.getAllUsers().subscribe(
      result => {
        this.users = result.users;
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

  public buttonClicked(data: any): void {
    switch (data?.action) {
      case Action.RedirectToDetail:
        this.router.navigate(['/user', data?.id]);
        break;
      case Action.EditUser:
        this.router.navigate(['/user', data?.id, 'update']);
        break;
      case Action.ChangePermissions:
        this.router.navigate(['/user', data?.id, 'roles', 'update']);
        break;
    }
  }
}
