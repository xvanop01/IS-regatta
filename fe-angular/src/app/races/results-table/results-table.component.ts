import {Component, Input, ViewChild} from "@angular/core";
import {TableComponent} from "../../core/support/table/table.component";
import {TableColumnDirective} from "../../core/support/table/table-column.directive";

@Component({
  selector: 'app-results-table',
  standalone: true,
  templateUrl: 'results-table.component.html',
  styleUrls: ['results-table.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective
  ]
})
export class ResultsTableComponent {

  @ViewChild('table') table?: TableComponent;

  @Input()
  public staticFilters: Array<any> = [];

  constructor() {
  }

  buttonClicked(data: any): void {
  }
}
