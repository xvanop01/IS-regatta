import { Component, EventEmitter, Input, Output } from "@angular/core";
import { TableColumn } from "./table.model";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  
  public columns: Array<TableColumn> = [];

  @Input()
  public rows: Array<any> = [];
  
  @Output()
  public onButtonClick = new EventEmitter();
  
  addColumn(column: TableColumn): void {
    this.columns.push(column);
  }

  onClick(id: any, action: any): void {
    if (id && action) {
      this.onButtonClick.emit({id: id, action: action});
    }
  }
}