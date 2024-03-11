import { Component, OnInit } from '@angular/core';
import { LoggedUserService } from './users/logged-user.service';
import { RouterOutlet } from "@angular/router";
import { ToolbarComponent } from "./core/toolbar/toolbar.component";

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [
    RouterOutlet,
    ToolbarComponent
  ]
})
export class AppComponent implements OnInit {
  title = 'IS regatta';

  public user: any;

  public authenticated: boolean = false;

  constructor(protected loggedUserService: LoggedUserService) {

  }

  ngOnInit(): void {
    this.loggedUserService.getLoggedUser().subscribe(
      result => {
        if (result != null) {
          this.user = result;
          this.authenticated = true;
        }
      });
  }
}
