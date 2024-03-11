import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import { TableComponent } from "../../core/support/table/table.component";

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
    TableColumnDirective
  ]
})
export class UsersManagementScreenComponent implements OnInit {

  protected users: any;

  protected readonly Action = Action;

  constructor(private router: Router) {

  }

  ngOnInit(): void {
  }

  public buttonClicked(data: any): void {
    switch (data?.action) {
      case Action.RedirectToDetail:
        this.router.navigate(['/user', data?.id]);
        break;
      case Action.EditUser:
        this.router.navigate(['/user', data?.id, 'update']);
        break;
      case Action.ChangePermissions:
        this.router.navigate(['/user', data?.id, 'roles', 'update']);
        break;
    }
  }
}
