import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { LoggedUserService } from "../logged-user.service";
import { RoleDto } from "../users.model";
import { UsersService } from "../users.service";

@Component({
  selector: 'app-user',
  templateUrl: './user-screen.component.html',
  styleUrls: ['./user-screen.component.css']
})
export class UserScreenComponent implements OnInit {

  public user: any;

  public roles: any;

  public isAdmin: boolean = false;

  constructor(private route: ActivatedRoute,
              protected loggedUserService: LoggedUserService,
              protected usersService: UsersService) {

  }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const userId = routeParams.get('userId');
    if (userId == null) {
      this.loggedUserService.getLoggedUser().subscribe(
        result => {
          this.user = result;
          this.usersService.getUserRoles(this.user.id).subscribe(
            result => {
              this.roles = result.roles;
              for (let i = 0; i < result.roles.length; i++) {
                if (result.roles.at(i)?.role == 'ADMIN') {
                  this.isAdmin = true;
                }
              }
            })
        });
    } else {
      this.usersService.getUser(Number(userId)).subscribe(
        result => {
          this.user = result;
          this.usersService.getUserRoles(this.user.id).subscribe(
            result => {
              this.roles = result.roles;
              for (let i = 0; i < result.roles.length; i++) {
                if (result.roles.at(i)?.role == 'ADMIN') {
                  this.isAdmin = true;
                }
              }
            })
        });
    }
  }
}
