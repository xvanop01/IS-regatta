import {ChangeDetectorRef, Component, OnInit, ViewChild} from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { RacesService } from "../races.service";
import { LoggedUserService } from "../../users/logged-user.service";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import {NgFor, NgIf} from "@angular/common";
import {MatButtonModule} from "@angular/material/button";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {RacesCreateUpdateDialogComponent} from "../races-create-update-dialog/races-create-update-dialog.component";
import {RacesTableComponent} from "../races-table/races-table.component";

@Component({
  selector: 'app-races',
  standalone: true,
  templateUrl: './races-screen.component.html',
  styleUrls: ['./races-screen.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective,
    NgFor,
    RouterLink,
    MatButtonModule,
    TableSearchDirective,
    MatDialogModule,
    NgIf,
    RacesTableComponent
  ]
})
export class RacesScreenComponent implements OnInit {

  @ViewChild('racesTable') racesTableComponent?: RacesTableComponent;

  protected isOrganizer: boolean = false;

  constructor(private dialog: MatDialog,
              private router: Router,
              private racesService: RacesService,
              private loggedUserService: LoggedUserService,
              private cd: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.loggedUserService.getLoggedUserRoles().subscribe(
      roles => {
        if (roles != null) {
          for (const role of roles.roles) {
            if (role.role === 'ADMIN' || role.role === 'ORGANIZER') {
              this.isOrganizer = true;
              this.cd.detectChanges();
            }
          }
        }
      }
    )
  }

  createRace(): void {
    const dialogRef = this.dialog.open(RacesCreateUpdateDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (this.racesTableComponent && this.racesTableComponent.table) {
        this.racesTableComponent.table.tableDataRefresh();
      }
    });
  }
}
