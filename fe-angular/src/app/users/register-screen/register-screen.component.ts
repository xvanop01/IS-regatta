import { Component, OnInit } from "@angular/core";
import { FormBuilder, ReactiveFormsModule } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { UsersService } from "../users.service";

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register-screen.component.html',
  styleUrls: ['./register-screen.component.css'],
  imports: [
    ReactiveFormsModule
  ]
})
export class RegisterScreenComponent implements OnInit {

  registerForm = this.formBuilder.group({
    username: '',
    password: ''
  });

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private usersService: UsersService,
              private snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    let credentials = 'username=' + this.registerForm.controls.username.value + '&password=' + this.registerForm.controls.password.value
    this.usersService.register(credentials).subscribe(
      result => {
        let snackBarRef = this.snackBar.open('User ' + result.username + ' successfully registered', 'Log In',
          {panelClass: ['success-snack']});
        snackBarRef.onAction().subscribe(
          () => this.router.navigate(['/login'])
        );
      },
      error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
  }
}
