import { Component, OnInit } from '@angular/core';
import { LoggedUserService } from './users/logged-user.service';
import { RoleListDto } from './users/users.model';
import { UsersService } from './users/users.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'my-project';

  public user: any;

  public authenticated: boolean = false;

  constructor(protected loggedUserService: LoggedUserService) {

  }

  ngOnInit(): void {
    this.loggedUserService.getLoggedUser().subscribe(
      result => {
        this.user = result;
        this.authenticated = true;
      });
  }
}
