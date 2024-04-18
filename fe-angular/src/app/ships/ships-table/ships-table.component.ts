import {Component, Input, ViewChild} from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import {NgFor, NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";

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

  protected readonly detail = 'DETAIL';

  constructor(private dialog: MatDialog,
              private router: Router) {
  }

  buttonClicked(data: any) {
    switch (data?.action) {
      case this.detail:
        this.router.navigate(['/ship', data?.rowData?.id]);
        break;
    }
  }
}
