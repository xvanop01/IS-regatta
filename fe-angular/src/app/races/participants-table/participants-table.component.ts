import {Component, Input, ViewChild} from "@angular/core";
import {TableComponent} from "../../core/support/table/table.component";
import {TableColumnDirective} from "../../core/support/table/table-column.directive";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";

@Component({
  selector: 'app-participants-table',
  standalone: true,
  templateUrl: 'participants-table.component.html',
  styleUrls: ['participants-table.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective,
    TableSearchDirective
  ]
})
export class ParticipantsTableComponent {

  @ViewChild('table') table?: TableComponent

  @Input()
  public staticFilters: Array<any> = [];

  constructor() {
  }
}
