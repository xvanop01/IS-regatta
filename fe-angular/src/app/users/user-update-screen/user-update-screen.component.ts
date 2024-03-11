import { Component, OnInit } from "@angular/core";
import { FormBuilder, ReactiveFormsModule } from "@angular/forms";
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from "@angular/router";
import { LoggedUserService } from "../logged-user.service";
import { UpdateUserDto } from "../users.model";
import { UsersService } from "../users.service";
import { NgIf } from "@angular/common";

@Component({
  selector: 'app-user-update',
  standalone: true,
  templateUrl: './user-update-screen.component.html',
  styleUrls: ['./user-update-screen.component.css'],
  imports: [
    ReactiveFormsModule,
    NgIf
  ]
})
export class UserUpdateScreenComponent implements OnInit {

  protected user: any;

  protected activeUser: any;

  updateForm = this.formBuilder.group({
    username: '',
    password: '',
    email: '',
    fullName: ''
  });

  constructor(private route: ActivatedRoute,
              private router: Router,
              private usersService: UsersService,
              private loggedUserService: LoggedUserService,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const userId = Number(routeParams.get('userId'));
    this.usersService.getUser(userId).subscribe(
      result => {
        this.user = result;
        this.updateForm.patchValue({
          username: this.user.username,
          email: this.user.email,
          fullName: this.user.fullName
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
    this.loggedUserService.getLoggedUser().subscribe(
      result => {
        this.activeUser = result;
      },
      error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
  }

  onSubmit(): void {
    var username: string = '';
    var password: string = '';
    var email: string = '';
    var fullName: string = '';
    if (this.updateForm.value.username !== undefined && this.updateForm.value.username !== null) {
      username = this.updateForm.value.username;
    }
    if (this.updateForm.value.password !== undefined && this.updateForm.value.password !== null) {
      password = this.updateForm.value.password;
    }
    if (this.updateForm.value.email !== undefined && this.updateForm.value.email !== null) {
      email = this.updateForm.value.email;
    }
    if (this.updateForm.value.fullName !== undefined && this.updateForm.value.fullName !== null) {
      fullName = this.updateForm.value.fullName;
    }
    const updateUser: UpdateUserDto = {
      username: username,
      password: password,
      email: email,
      fullName: fullName
    };
    this.usersService.updateUser(this.user.id, updateUser).subscribe(
      result => {
        this.router.navigate(['/user', this.user.id]);
      },
      error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
  }
}
