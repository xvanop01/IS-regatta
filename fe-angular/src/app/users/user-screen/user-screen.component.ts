import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { LoggedUserService } from "../logged-user.service";
import { UsersService } from "../users.service";

@Component({
  selector: 'app-user',
  templateUrl: './user-screen.component.html',
  styleUrls: ['./user-screen.component.css']
})
export class UserScreenComponent implements OnInit {

  public user: any;

  public roles: any;

  public authenticated: boolean = false;

  constructor(protected loggedUserService: LoggedUserService, protected usersService: UsersService) {

  }

  ngOnInit(): void {
    this.loggedUserService.getLoggedUser().subscribe(
      result => {
        this.user = result;
        this.authenticated = true;
        this.usersService.getUserRoles(this.user.id).subscribe(
          result => {
            this.roles = result.roles;
          })
      });
  }
}
