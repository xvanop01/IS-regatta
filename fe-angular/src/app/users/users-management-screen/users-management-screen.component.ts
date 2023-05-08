import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router } from "@angular/router";
import { LoggedUserService } from "../logged-user.service";
import { CreateUserDto, UserDetailDto } from "../users.model";
import { UsersService } from "../users.service";

@Component({
  selector: 'app-users-management',
  templateUrl: './users-management-screen.component.html',
  styleUrls: ['./users-management-screen.component.css']
})
export class UsersManagementScreenComponent implements OnInit {

  protected users: any;

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
}
