import {Component, ViewChild} from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import {NgFor, NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {SearchType} from "../../core/support/table/table.model";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";

@Component({
  selector: 'app-races-table',
  standalone: true,
  templateUrl: './races-table.component.html',
  styleUrls: ['./races-table.component.css'],
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
export class RacesTableComponent {

  @ViewChild('table') table?: TableComponent;

  protected readonly SearchType = SearchType;

  protected readonly detail = 'DETAIL';

  constructor(private dialog: MatDialog,
              private router: Router) {
  }

  buttonClicked(data: any) {
    switch (data?.action) {
      case this.detail:
        this.router.navigate(['/race', data?.rowData?.id]);
        break;
    }
  }
}
