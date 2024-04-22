import {Component, Input, ViewChild} from "@angular/core";
import {Router, RouterLink} from "@angular/router";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import {MatButtonModule} from "@angular/material/button";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {NgIf} from "@angular/common";
import {RegistrationStatusEnum} from "../races.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {RacesService} from "../races.service";
import {MatDialog} from "@angular/material/dialog";
import {ResultUpdateDialogComponent} from "../result-update-dialog/result-update-dialog.component";

enum Action {
  RedirectToDetail = 'DETAIL',
  UpdateResults = 'RESULTS',
  AcceptCrew = 'ACCEPT',
  DeclineCrew = 'DECLINE'
}

@Component({
  selector: 'app-crews-table',
  standalone: true,
  templateUrl: './crews-table.component.html',
  styleUrls: ['./crews-table.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective,
    RouterLink,
    MatButtonModule,
    TableSearchDirective,
    NgIf
  ]
})
export class CrewsTableComponent {

  @ViewChild('table') table?: TableComponent;

  @Input()
  public staticFilters: Array<any> = [];

  @Input()
  public canManageCrews: boolean = false;

  @Input()
  public isAfterRace: boolean = false;

  @Input()
  public isPrivate: boolean = false;

  protected readonly Action = Action;

  protected readonly RegistrationStatusEnum = RegistrationStatusEnum;

  constructor(private router: Router,
              private racesService: RacesService,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) {
  }

  buttonClicked(data: any) {
    switch (data?.action) {
      case Action.RedirectToDetail:
        this.router.navigate(['/crew', data?.rowData?.id]);
        break;
      case Action.UpdateResults:
        this.racesService.getCrewResults(data.rowData?.id).subscribe(result => {
          const dialogRef = this.dialog.open(ResultUpdateDialogComponent,
            {data: result});
          dialogRef.afterClosed().subscribe(result => {
            this.table?.tableDataRefresh();
          });
        })
        break;
      case Action.AcceptCrew:
        this.racesService.acceptCrew(data?.rowData?.id).subscribe(result => {
          this.table?.tableDataRefresh();
        }, error => {
          let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
        })
        break;
      case Action.DeclineCrew:
        this.racesService.declineCrew(data?.rowData?.id).subscribe(result => {
          this.table?.tableDataRefresh();
        }, error => {
          let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
        })
        break;
    }
  }
}
