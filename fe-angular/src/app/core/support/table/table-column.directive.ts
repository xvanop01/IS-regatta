import { Directive, Host, Input, OnInit } from "@angular/core";
import { TableComponent } from "./table.component";

@Directive({
  selector: 'app-table-column'
})
export class TableColumnDirective implements OnInit {

  @Input()
  public title: string | undefined;
  
  @Input()
  public field: string | undefined;
  
  @Input()
  public width: number | string | undefined;

  @Input()
  public icon: string | undefined;
  
  @Input()
  public isButton = false;
  
  protected table: TableComponent;
  
  constructor(@Host() parent: TableComponent) {
    this.table = parent;
  }
  ngOnInit(): void {
    this.table.addColumn({
      title: this.title === undefined ? '' : this.title,
      field: this.field === undefined ? '' : this.field,
      width: this.width === undefined ? 'auto' : this.width,
      icon: this.icon === undefined ? '' : this.icon,
      isButton: this.isButton
    })
  }
}