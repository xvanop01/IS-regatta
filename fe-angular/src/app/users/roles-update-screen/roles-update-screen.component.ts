import { Component, OnInit } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router } from "@angular/router";
import { RoleDto, RoleListDto } from "../users.model";
import { UsersService } from "../users.service";
import { FormsModule } from "@angular/forms";
import { NgFor } from "@angular/common";

@Component({
  selector: 'app-roles-update',
  standalone: true,
  templateUrl: './roles-update-screen.component.html',
  styleUrls: ['./roles-update-screen.component.css'],
  imports: [
    NgFor,
    FormsModule
  ]
})
export class RolesUpdateScreenComponent implements OnInit {

  protected userId: number;

  protected user: any;

  protected allRoles: RoleDto[] = [];

  protected roles: RoleDto[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private usersService: UsersService,
              private snackBar: MatSnackBar) {
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
      },
      error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    this.usersService.getUser(this.userId).subscribe(
      result => {
        this.user = result;
        this.usersService.getUserRoles(this.userId).subscribe(
          result => {
            this.roles = result.roles;
          },
          error => {
            let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
          });
      },
      error => {
        if (error.status === 401) {
          let snackBarRef = this.snackBar.open('User unauthorised', 'Log In');
          snackBarRef.afterDismissed().subscribe(
            () => this.router.navigate(['/login'])
          );
        } else {
          let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
        }
      });
  }

  onSubmit(): void {
    const roleList: RoleListDto = {
      roles: this.roles
    }
    this.usersService.updateUserRoles(this.userId, roleList).subscribe(
      result => {
        this.router.navigate(['/user', this.user.id]);
      },
      error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
  }
}
