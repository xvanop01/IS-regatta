import {Component, Input, ViewChild} from "@angular/core";
import { RouterLink } from "@angular/router";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import {NgFor, NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {ShipCreateUpdateDialogComponent} from "../ship-create-update-dialog/ship-create-update-dialog.component";

@Component({
  selector: 'app-ships-table',
  standalone: true,
  templateUrl: './ships-table.component.html',
  styleUrls: ['./ships-table.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective,
    NgFor,
    RouterLink,
    MatButtonModule,
    TableSearchDirective,
    MatDialogModule,
    NgIf
  ]
})
export class ShipsTableComponent {

  @ViewChild('table') table?: TableComponent;

  @Input()
  public staticFilters: Array<any> = [];

  protected readonly edit = 'EDIT';

  constructor(private dialog: MatDialog) {
  }

  buttonClicked(data: any) {
    switch (data?.action) {
      case this.edit:
        const crDialogRef = this.dialog.open(ShipCreateUpdateDialogComponent,
          {data: data.rowData});
        crDialogRef.afterClosed().subscribe(result => {
          if (this.table) {
            this.table.tableDataRefresh();
          }
        })
        break;
    }
  }
}
