import { Component } from "@angular/core";
import { FormBuilder, ReactiveFormsModule } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { UsersService } from "../users.service";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";

enum VisiblePassword {
  Hide = 'password',
  Show = 'text'
}

enum IconPassword {
  Hide = 'fa-solid fa-eye-slash',
  Show = 'fa-solid fa-eye'
}

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register-screen.component.html',
  styleUrls: ['./register-screen.component.css'],
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class RegisterScreenComponent {

  public visible: VisiblePassword = VisiblePassword.Hide;

  public icon: IconPassword = IconPassword.Show;

  protected registerForm = this.formBuilder.group({
    username: '',
    password: ''
  });

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private usersService: UsersService,
              private snackBar: MatSnackBar) {

  }

  public onIconClicked(): void {
    if (this.visible === VisiblePassword.Hide) {
      this.visible = VisiblePassword.Show;
      this.icon = IconPassword.Hide;
    } else {
      this.visible = VisiblePassword.Hide;
      this.icon = IconPassword.Show;
    }
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
        this.registerForm.patchValue({
          username: '',
          password: ''}
        );
      },
      error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
  }
}
