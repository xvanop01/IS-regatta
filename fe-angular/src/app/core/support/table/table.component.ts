import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {dir, PageSpecs, SortSpecs, TableColumn, TableSearch} from "./table.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {CoreService} from "../../core.service";
import {NgFor, NgIf} from "@angular/common";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatSelectModule} from "@angular/material/select";
import {FormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";

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
    MatInputModule,
    MatIcon,
    MatButton
  ]
})
export class TableComponent implements OnInit {

  @Input()
  public serviceName: string = "";

  @Output()
  public onButtonClick = new EventEmitter();

  public columns: Array<TableColumn> = [];

  public searchColumns: Array<TableSearch> = [];

  protected rows: Array<any> = [];

  protected pageSpecs: PageSpecs = {
    pageSize: 25,
    pageNumber: 0
  };

  protected sortSpecs: SortSpecs = {
    column: '',
    direction: dir.ASC
  }

  protected totalPages: number = 1;

  protected totalItems: number = 0;

  protected scrollActivated: boolean = this.isScrollActivated();

  private filters: Array<TableSearch> = [];

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

  addSearch(searchField: TableSearch): void {
    this.searchColumns.push(searchField);
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

  public doFilter() {
    this.filters = this.searchColumns;
    this.showPage(0);
  }

  public tableDataRefresh(): void {
    this.coreService.getTableData(this.serviceName, this.pageSpecs, this.sortSpecs, this.filters).subscribe(
      result => {
        this.rows = result.data;
        this.totalItems = result.totalItems;
        this.totalPages = result.totalPages;
        this.scrollActivated = this.isScrollActivated();
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

  public onHeaderClick(column: any): void {
    let placeholder = document.getElementById(column + '-placeholder');
    let sortArrowUp = document.getElementById(column + '-up');
    let sortArrowDown = document.getElementById(column + '-down');
    if (placeholder !== null && sortArrowUp !== null && sortArrowDown !== null) {
      if (this.sortSpecs.column !== column) {
        this.resetSort();
        this.sortSpecs.column = column;
        placeholder.style.setProperty('display', 'none');
        sortArrowUp.style.setProperty('display', 'inline');
      } else if (this.sortSpecs.direction === dir.ASC) {
        this.sortSpecs.direction = dir.DESC;
        sortArrowUp.style.setProperty('display', 'none');
        sortArrowDown.style.setProperty('display', 'inline');
      } else {
        this.resetSort();
      }

      this.tableDataRefresh();
    }
  }

  private resetSort(): void {
    if (this.sortSpecs.column !== '') {
      let placeholder = document.getElementById(this.sortSpecs.column + '-placeholder');
      let sortArrow = document.getElementById(this.sortSpecs.column +
        (this.sortSpecs.direction === dir.ASC ? '-up' : '-down'));
      if (sortArrow !== null && placeholder !== null) {
        placeholder.style.setProperty('display', 'inline');
        sortArrow.style.setProperty('display', 'none');
      }
      this.sortSpecs.column = '';
      this.sortSpecs.direction = dir.ASC;
    }
  }

  protected isScrollActivated(): boolean {
    let tableScrollArea = document.getElementById('table-pager-wrapper');
    if (tableScrollArea != null) {
      return tableScrollArea.scrollHeight >= tableScrollArea.clientHeight;
    }
    return false;
  }
}
