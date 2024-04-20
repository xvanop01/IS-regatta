import {Component, ViewChild} from "@angular/core";
import { Router } from "@angular/router";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import { TableComponent } from "../../core/support/table/table.component";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {UserUpdateDialogComponent} from "../user-update-dialog/user-update-dialog.component";
import {UsersService} from "../users.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {RolesUpdateDialogComponent} from "../roles-update-dialog/roles-update-dialog.component";

enum Action {
  RedirectToDetail = 'DETAIL',
  EditUser = 'EDIT',
  ChangePermissions = 'PERMISSIONS'
}

@Component({
  selector: 'app-users-management',
  standalone: true,
  templateUrl: './users-management-screen.component.html',
  styleUrls: ['./users-management-screen.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective,
    TableSearchDirective,
    MatDialogModule
  ]
})
export class UsersManagementScreenComponent {

  @ViewChild('usersTable') usersTableComponent?: TableComponent;

  protected users: any;

  protected readonly Action = Action;

  protected roles: any;

  constructor(private router: Router,
              private dialog: MatDialog,
              private usersService: UsersService,
              private snackBar: MatSnackBar) {
    usersService.getAllRoles().subscribe(result => {
        this.roles = result.roles;
      }, error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
  }

  public buttonClicked(data: any): void {
    switch (data?.action) {
      case Action.RedirectToDetail:
        this.router.navigate(['/user', data?.rowData?.id]);
        break;
      case Action.EditUser:
        const dialogRef = this.dialog.open(UserUpdateDialogComponent,
          {data: data?.rowData});
        dialogRef.afterClosed().subscribe(result => {
          if (this.usersTableComponent && result) {
            this.usersTableComponent.tableDataRefresh();
          }
        })
        break;
      case Action.ChangePermissions:
        this.usersService.getUserRoles(data?.rowData?.id).subscribe(result => {
          const dialogRef = this.dialog.open(RolesUpdateDialogComponent,
            {data: {
                id: data?.rowData?.id,
                allRoles: this.roles,
                userRoles: result?.roles
              }});
          dialogRef.afterClosed().subscribe(result => {
            if (this.usersTableComponent && result) {
              this.usersTableComponent.tableDataRefresh();
            }
          })
        }, error => {
          let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
        })
        break;
    }
  }
}
