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

  user: any;

  roles: any;

  constructor(protected userService: UsersService, protected loggedUserService: LoggedUserService) {

  }

  ngOnInit(): void {
    this.loggedUserService.getLoggedUser().subscribe(
      result => {
        this.user = result;
        this.userService.getUserRoles(this.user.id).subscribe(
          result => {
            this.roles = result;
          });
      },
      error => {
        console.log(error)
        if (error.status == 401) {
          window.location.href = `http://localhost:8080/login`;
        }
      });
  }
}
