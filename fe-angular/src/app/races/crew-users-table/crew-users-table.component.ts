import {Component, Input, ViewChild} from "@angular/core";
import {TableComponent} from "../../core/support/table/table.component";
import {TableColumnDirective} from "../../core/support/table/table-column.directive";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";

@Component({
  selector: 'app-crew-users-table',
  standalone: true,
  templateUrl: 'crew-users-table.component.html',
  styleUrls: ['crew-users-table.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective,
    TableSearchDirective
  ]
})
export class CrewUsersTableComponent {

  @ViewChild('table') table?: TableComponent

  @Input()
  public staticFilters: Array<any> = [];

  constructor() {
  }

  buttonClicked(data: any): void {
    switch (data?.action) {

    }
  }
}
