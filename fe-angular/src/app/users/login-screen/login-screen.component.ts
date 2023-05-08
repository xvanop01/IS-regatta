import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { UsersService } from "../users.service";

@Component({
  selector: 'app-login',
  templateUrl: './login-screen.component.html',
  styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent implements OnInit {

  loginForm = this.formBuilder.group({
    username: '',
    password: ''
  });

  constructor(private formBuilder: FormBuilder,
              private usersService: UsersService,
              private snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    let credentials = 'username=' + this.loginForm.controls.username.value + '&password=' + this.loginForm.controls.password.value
    this.usersService.login(credentials).subscribe();
  }
}
