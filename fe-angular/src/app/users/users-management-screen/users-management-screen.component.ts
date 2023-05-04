import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { LoggedUserService } from "../logged-user.service";
import { CreateUserDto, UserDetailDto } from "../users.model";
import { UsersService } from "../users.service";

@Component({
  selector: 'app-users-management',
  templateUrl: './users-management-screen.component.html',
  styleUrls: ['./users-management-screen.component.css']
})
export class UsersManagementScreenComponent implements OnInit {

  protected users: any;

  constructor(private usersService: UsersService) {

  }

  ngOnInit(): void {
    this.usersService.getAllUsers().subscribe(result => {
      this.users = result.users;
    });
  }
}
