import {Component, Input, ViewChild} from "@angular/core";
import {TableComponent} from "../../core/support/table/table.component";
import {TableColumnDirective} from "../../core/support/table/table-column.directive";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {RegistrationStatusEnum} from "../races.model";
import {NgIf} from "@angular/common";
import {RacesService} from "../races.service";
import {MatSnackBar} from "@angular/material/snack-bar";

enum Action {
  AcceptUser = 'ACCEPT',
  DeclineUser = 'DECLINE'
}

@Component({
  selector: 'app-crew-users-table',
  standalone: true,
  templateUrl: 'crew-users-table.component.html',
  styleUrls: ['crew-users-table.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective,
    TableSearchDirective,
    NgIf
  ]
})
export class CrewUsersTableComponent {

  @ViewChild('table') table?: TableComponent

  @Input()
  public staticFilters: Array<any> = [];

  @Input()
  public canManageCrewUsers: boolean = false;

  protected readonly Action = Action;

  constructor(private racesService: RacesService,
              private snackBar: MatSnackBar) {
  }

  buttonClicked(data: any): void {
    switch (data?.action) {
      case Action.AcceptUser:
        this.racesService.acceptUserToCrew(data?.rowData?.id).subscribe(result => {
          this.table?.tableDataRefresh();
        }, error => {
          this.snackBar.open(error.status + ': ' + error.error, 'X');
        });
        break;
      case Action.DeclineUser:
        this.racesService.declineUserFromCrew(data?.rowData?.id).subscribe(result => {
          this.table?.tableDataRefresh();
        }, error => {
          this.snackBar.open(error.status + ': ' + error.error, 'X');
        });
        break;
    }
  }

  protected readonly RegistrationStatusEnum = RegistrationStatusEnum;
}
