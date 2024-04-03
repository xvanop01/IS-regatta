import {Component, OnInit, ViewChild} from "@angular/core";
import { Router } from "@angular/router";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import { TableComponent } from "../../core/support/table/table.component";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {RacesCreateDialogComponent} from "../../races/races-create-dialog/races-create-dialog.component";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {UserUpdateDialogComponent} from "../user-update-dialog/user-update-dialog.component";

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
export class UsersManagementScreenComponent implements OnInit {

  @ViewChild('usersTable') usersTableComponent?: TableComponent;

  protected users: any;

  protected readonly Action = Action;

  constructor(private router: Router,
              private dialog: MatDialog) {

  }

  ngOnInit(): void {
  }

  public buttonClicked(data: any): void {
    switch (data?.action) {
      case Action.RedirectToDetail:
        this.router.navigate(['/user', data?.rowData?.id]);
        break;
      case Action.EditUser:
        const crDialogRef = this.dialog.open(UserUpdateDialogComponent,
          {data: data?.rowData});
        crDialogRef.afterClosed().subscribe(result => {
          if (this.usersTableComponent) {
            this.usersTableComponent.tableDataRefresh();
          }
        })
        break;
      case Action.ChangePermissions:
        this.router.navigate(['/user', data?.rowData?.id, 'roles', 'update']);
        break;
    }
  }
}
