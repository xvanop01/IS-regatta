import {Directive, Host, Input, OnInit} from "@angular/core";
import {TableComponent} from "./table.component";
import {SearchType} from "./table.model";
import {FormControl} from "@angular/forms";

@Directive({
  selector: 'app-table-search',
  standalone: true
})
export class TableSearchDirective implements OnInit {

  @Input()
  public title: string | undefined;

  @Input()
  public column: string | undefined;

  @Input()
  public value: string | undefined;

  @Input()
  public type: SearchType | undefined;

  protected table: TableComponent;

  constructor(@Host() parent: TableComponent) {
    this.table = parent;
  }

  ngOnInit(): void {
    this.table.addSearch({
      title: this.title === undefined ? '' : this.title,
      column: this.column === undefined ? '' : this.column,
      type: this.type === undefined ? SearchType.STRING : this.type,
      fc: new FormControl(this.value)
    })
  }
}
