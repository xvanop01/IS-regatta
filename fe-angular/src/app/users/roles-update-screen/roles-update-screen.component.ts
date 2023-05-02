import { Component, OnInit } from "@angular/core";
import {FormArray, FormBuilder } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { CreateUserDto, RoleDto, RoleListDto, UserDetailDto } from "../users.model";
import { UsersService } from "../users.service";

@Component({
  selector: 'app-roles-update',
  templateUrl: './roles-update-screen.component.html',
  styleUrls: ['./roles-update-screen.component.css']
})
export class RolesUpdateScreenComponent implements OnInit {
  
  protected userId: number;

  protected user: any;
  
  protected allRoles: RoleDto[] = [];
  
  protected roles: RoleDto[] = [];

  constructor(private route: ActivatedRoute,
              private usersService: UsersService,
              private formBuilder: FormBuilder) {
    const routeParams = this.route.snapshot.paramMap;
    this.userId = Number(routeParams.get('userId'));
  }

  isChecked(role: RoleDto): boolean {
    for (let i = 0; i < this.roles.length; i++) {
      if (role.id == this.roles[i].id) {
        return true;
      }
    }
    return false;
  }
  
  checkRole(role: RoleDto): void {
    if (this.isChecked(role)) {
      this.roles.forEach((value, index) => {
        if (value?.id == role.id) {
          this.roles.splice(index, 1);
        }
      })
    } else {
      this.roles.push(role);
    }
  }

  ngOnInit(): void {
    this.usersService.getAllRoles().subscribe(
      result => {
        this.allRoles = result.roles;
      });
    this.usersService.getUser(this.userId).subscribe(
      result => {
        this.user = result;
        this.usersService.getUserRoles(this.userId).subscribe(
          result => {
            this.roles = result.roles;
          });
      });
  }

  onSubmit(): void {
    const roleList: RoleListDto = {
      roles: this.roles
    }
    this.usersService.updateUserRoles(this.userId, roleList).subscribe();
  }
}
