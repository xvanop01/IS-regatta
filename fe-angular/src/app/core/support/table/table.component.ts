import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {dir, Filter, PageSpecs, SearchType, SortSpecs, TableColumn, TableSearch} from "./table.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {CoreService} from "../../core.service";
import {DatePipe, NgFor, NgIf} from "@angular/common";
import {MatButtonModule, MatIconButton} from "@angular/material/button";
import {MatSelectModule} from "@angular/material/select";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";
import {MatDatepicker, MatDatepickerModule} from "@angular/material/datepicker";
import {provideMomentDateAdapter} from '@angular/material-moment-adapter';
import {Moment} from "moment";

export const MONTH_FORMAT = {
  parse: {
    dateInput: 'MM-YYYY',
  },
  display: {
    dateInput: 'MM-YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMM YYYY',
  },
};

@Component({
  selector: 'app-table',
  standalone: true,
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
  providers: [
    provideMomentDateAdapter(MONTH_FORMAT)
  ],
  imports: [
    NgIf,
    NgFor,
    FormsModule,
    MatIconButton,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatIcon,
    MatButtonModule,
    MatDatepickerModule,
    DatePipe,
    ReactiveFormsModule
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

  protected readonly SearchType = SearchType;

  private filters: Array<Filter> = [];

  constructor(private cd: ChangeDetectorRef,
              private coreService: CoreService,
              private router: Router,
              private snackBar: MatSnackBar,
              private datePipe: DatePipe) {
  }

  ngOnInit(): void {
    this.tableDataRefresh();
  }

  public addColumn(column: TableColumn): void {
    this.columns.push(column);
  }

  public addSearch(searchField: TableSearch): void {
    this.searchColumns.push(searchField);
  }

  public onClick(data: any, action: any): void {
    if (data && action) {
      this.onButtonClick.emit({rowData: data, action: action});
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
    this.filters = [];
    for (const search of this.searchColumns) {
      if (search.fc.value != '') {
        const filter = {
          title: search.title,
          column: search.column,
          type: search.type,
          value: search.fc.value
        }
        if (search.type === SearchType.MONTH && search.fc.value != null) {
          filter.value = search.fc.value + '-01';
        }
        this.filters.push(filter);
      }
    }
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

  public setMonthAndYear(dateForm: string, datepicker: MatDatepicker<Moment>, searchColumn: String) {
    const date = Date.parse(dateForm);
    let search = this.searchColumns.find(f => f.column == searchColumn);
    if (search) {
      const dateTransformed = this.datePipe.transform(date, 'YYYY-MM');
      if (dateTransformed != null) {
        search.fc.setValue(dateTransformed);
      }
    }
    datepicker.close();
  }

  public setDateFilter(searchColumn: String): void {
    let search = this.searchColumns.find(f => f.column == searchColumn);
    if (search) {
      const date = Date.parse(search.fc.value);
      const dateTransformed = this.datePipe.transform(date, 'YYYY-MM');
      if (dateTransformed != null) {
        search.fc.setValue(dateTransformed);
      }
    }
  }

  protected isScrollActivated(): boolean {
    let tableScrollArea = document.getElementById('table-pager-wrapper');
    if (tableScrollArea != null) {
      return tableScrollArea.scrollHeight >= tableScrollArea.clientHeight;
    }
    return false;
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
}
