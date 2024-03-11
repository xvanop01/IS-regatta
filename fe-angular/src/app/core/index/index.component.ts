import { Component, OnInit } from "@angular/core";
import { LoggedUserService } from "../../users/logged-user.service";
import { UsersService } from "../../users/users.service";
import { NgIf } from "@angular/common";

@Component({
  selector: 'app-index',
  standalone: true,
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
  imports: [
    NgIf
  ]
})
export class IndexComponent implements OnInit {

  protected user: any;

  protected roles: any;

  protected isAdmin: boolean = false;

  constructor(protected loggedUserService: LoggedUserService, protected usersService: UsersService) {

  }

  ngOnInit(): void {
    this.loggedUserService.getLoggedUser().subscribe(
      result => {
        if (result != null) {
          this.user = result;
          this.usersService.getUserRoles(this.user.id).subscribe(
            result => {
              this.roles = result.roles;
              for (const role of this.roles) {
                if (role.role == 'ADMIN') {
                  this.isAdmin = true;
                }
              }
            });
        }
      });
  }

}
