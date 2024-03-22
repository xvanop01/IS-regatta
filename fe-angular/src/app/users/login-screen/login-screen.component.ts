import { Component, OnInit } from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login-screen.component.html',
  styleUrls: ['./login-screen.component.css']
})
export class LoginScreenComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute,
              private snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe(params => {
      const error = params.get('error');
      if (error != null) {
        let snackBarRef = this.snackBar.open('Login error: ' + error, 'X');
      }
    })
  }

}
