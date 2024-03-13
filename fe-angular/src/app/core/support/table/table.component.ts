import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { PageSpecs, TableColumn } from "./table.model";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { CoreService } from "../../core.service";
import { NgFor, NgIf } from "@angular/common";
import { MatIconButton } from "@angular/material/button";
import { MatSelectModule } from "@angular/material/select";
import { FormsModule } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";

@Component({
  selector: 'app-table',
  standalone: true,
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
  imports: [
    NgIf,
    NgFor,
    MatIconButton,
    FormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule
  ]
})
export class TableComponent implements OnInit {

  @Input()
  public serviceName: string = "";

  @Output()
  public onButtonClick = new EventEmitter();

  public columns: Array<TableColumn> = [];

  protected rows: Array<any> = [];

  protected pageSpecs: PageSpecs = {
    pageSize: 25,
    pageNumber: 0
  };

  protected totalPages: number = 1;

  protected totalItems: number = 0;

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
    this.pageSpecs.pageNumber = 0;
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
