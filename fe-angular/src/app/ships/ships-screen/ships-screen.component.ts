import {Component, OnInit, ViewChild} from "@angular/core";
import { RouterLink } from "@angular/router";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import {NgFor, NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {ShipsTableComponent} from "../ships-table/ships-table.component";
import {ShipCreateUpdateDialogComponent} from "../ship-create-update-dialog/ship-create-update-dialog.component";
import {LoggedUserService} from "../../users/logged-user.service";

@Component({
  selector: 'app-ships',
  standalone: true,
  templateUrl: './ships-screen.component.html',
  styleUrls: ['./ships-screen.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective,
    NgFor,
    RouterLink,
    MatButtonModule,
    TableSearchDirective,
    MatDialogModule,
    NgIf,
    ShipsTableComponent
  ]
})
export class ShipsScreenComponent implements OnInit {

  @ViewChild('shipsTable') shipsTableComponent?: ShipsTableComponent;
  protected loggedUser: any = null;

  constructor(private dialog: MatDialog,
              private loggedUserService: LoggedUserService) {
  }

  ngOnInit(): void {
    this.loggedUserService.getLoggedUser().subscribe(user => {
      this.loggedUser = user;
    });
  }

  createShip(): void {
    const crDialogRef = this.dialog.open(ShipCreateUpdateDialogComponent);
    crDialogRef.afterClosed().subscribe(result => {
      if (this.shipsTableComponent && this.shipsTableComponent.table) {
        this.shipsTableComponent.table.tableDataRefresh();
      }
    });
  }
}
