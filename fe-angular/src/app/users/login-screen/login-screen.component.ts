import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {FormControl, FormGroup, NgForm, ReactiveFormsModule} from "@angular/forms";
import {UsersService} from "../users.service";

enum VisiblePassword {
  Hide = 'password',
  Show = 'text'
}

enum IconPassword {
  Hide = 'fa-solid fa-eye-slash',
  Show = 'fa-solid fa-eye'
}

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login-screen.component.html',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    ReactiveFormsModule,
    RouterLink
  ],
  styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent implements OnInit {

  @ViewChild('formElement') formElement?: ElementRef;

  public visible: VisiblePassword = VisiblePassword.Hide;

  public icon: IconPassword = IconPassword.Show;

  public fg: FormGroup;

  constructor(private activatedRoute: ActivatedRoute,
              private snackBar: MatSnackBar,
              private usersService: UsersService) {
    this.fg = new FormGroup({
      username: new FormControl(''),
      password: new FormControl('')
    })
  }

  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe(params => {
      const error = params.get('error');
      if (error != null) {
        let snackBarRef = this.snackBar.open('Login error: ' + error, 'X');
      }
    })
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

  public onSubmit(): void {
    if (this.formElement) {
      this.formElement.nativeElement.submit();
    }
  }
}
