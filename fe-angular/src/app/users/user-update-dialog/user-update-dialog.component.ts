import {DialogFieldType} from "../../core/support/dialog/dialog.model";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UsersService} from "../users.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Component, Inject} from "@angular/core";
import {DialogComponent} from "../../core/support/dialog/dialog.component";
import {DialogFieldDirective} from "../../core/support/dialog/dialog-field.directive";

@Component({
  selector: 'app-user-update-dialog',
  standalone: true,
  templateUrl: './user-update-dialog.component.html',
  styleUrls: ['./user-update-dialog.component.css'],
  imports: [
    DialogComponent,
    DialogFieldDirective
  ]
})
export class UserUpdateDialogComponent {

  protected readonly DialogFieldType = DialogFieldType;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<UserUpdateDialogComponent>,
              protected usersService: UsersService,
              private snackBar: MatSnackBar) {

  }

  public onSubmitButtonClick(data: any): void {
    this.usersService.updateUser(this.data?.id, {
      username: data?.username,
      password: data?.password,
      email: data?.email,
      fullName: data?.fullName
    }).subscribe(result => {
      this.dialogRef.close(result);
    }, error => {
      let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
    })
  }
}
