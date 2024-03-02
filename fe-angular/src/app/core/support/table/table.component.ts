import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import {PageSpecs, TableColumn } from "./table.model";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import {CoreService} from "../../core.service";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {

  @Input()
  public serviceName: string = "";

  @Output()
  public onButtonClick = new EventEmitter();

  public columns: Array<TableColumn> = [];

  protected rows: Array<any> = [];

  private pageSpecs: PageSpecs = {
    pageSize: 25,
    pageNumber: 0
  };

  private totalPages: number = 1;

  private totalItems: number = 0;

  constructor(private cd: ChangeDetectorRef,
              private coreService: CoreService,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.tableDataRefresh();
  }

  addColumn(column: TableColumn): void {
    this.columns.push(column);
  }

  onClick(id: any, action: any): void {
    if (id && action) {
      this.onButtonClick.emit({id: id, action: action});
    }
  }

  public setPageSize(size: number) {
    this.pageSpecs.pageSize = size;
    this.tableDataRefresh();
  }

  public showPage(pageNumber: number) {
    this.pageSpecs.pageNumber = pageNumber;
    this.tableDataRefresh();
  }

  public tableDataRefresh(): void {
    this.coreService.getTableData(this.serviceName, this.pageSpecs).subscribe(
      result => {
        this.rows = result.data;
        this.totalItems = result.totalItems;
        this.totalPages = result.totalPages;
        this.cd.detectChanges();
      },
      error => {
        if (error.status === 401) {
          let snackBarRef = this.snackBar.open('User unauthorised', 'Log In');
          snackBarRef.afterDismissed().subscribe(
            () => this.router.navigate(['/login'])
          );
        } else {
          let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
        }
      });
  }
}
