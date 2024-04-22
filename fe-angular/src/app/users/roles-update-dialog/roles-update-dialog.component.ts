import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UsersService} from "../users.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Component, Inject} from "@angular/core";
import {DialogComponent} from "../../core/support/dialog/dialog.component";
import {DialogFieldDirective} from "../../core/support/dialog/dialog-field.directive";
import {DialogEnum, DialogFieldType} from "../../core/support/dialog/dialog.model";
import {RoleListDto} from "../users.model";

@Component({
  selector: 'app-roles-update-dialog',
  standalone: true,
  templateUrl: './roles-update-dialog.component.html',
  styleUrls: ['./roles-update-dialog.component.css'],
  imports: [
    DialogComponent,
    DialogFieldDirective
  ]
})
export class RolesUpdateDialogComponent {

  public allRoles: Array<DialogEnum> = [];

  protected userRoles: Array<DialogEnum> = [];

  protected readonly DialogFieldType = DialogFieldType;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<RolesUpdateDialogComponent>,
              protected usersService: UsersService,
              private snackBar: MatSnackBar) {
    for (const role of data?.allRoles) {
      this.allRoles.push({title: role.role, value: role.id?.toString()})
    }
    for (const role of data?.userRoles) {
      this.userRoles.push({title: role.role, value: role.id?.toString()})
    }
  }

  public onSubmitButtonClick(data: any): void {
    let roles: RoleListDto = {roles: []};
    for (const val in data?.roles) {
      const role = this.allRoles.find((value, index, obj) => value.value === data?.roles[val]);
      if (role) {
        roles.roles.push({id: parseInt(role.value), role: role.title})
      }
    }
    this.usersService.updateUserRoles(this.data?.id, roles).subscribe(
      result => {
        this.dialogRef.close(result);
      },
      error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
  }
}
