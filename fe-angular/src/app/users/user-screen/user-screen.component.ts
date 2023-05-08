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

  public isActiveAdmin: boolean = false;

  constructor(private route: ActivatedRoute,
              protected loggedUserService: LoggedUserService,
              protected usersService: UsersService) {

  }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const userId = routeParams.get('userId');
    this.loggedUserService.getLoggedUser().subscribe(
      result => {
        if (userId == null) {
          this.user = result;
        }
        this.usersService.getUserRoles(result.id).subscribe(
          result => {
            if (userId == null) {
              this.roles = result.roles;
            }
            for (let i = 0; i < result.roles.length; i++) {
              if (result.roles.at(i)?.role == 'ADMIN') {
                this.isActiveAdmin = true;
              }
            }
          })
      });
    if (userId != null) {
      this.usersService.getUser(Number(userId)).subscribe(
        result => {
          this.user = result;
          this.usersService.getUserRoles(this.user.id).subscribe(
            result => {
              this.roles = result.roles;
            })
        });
    }
  }
}
