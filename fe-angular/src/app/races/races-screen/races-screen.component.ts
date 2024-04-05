import {ChangeDetectorRef, Component, OnInit, ViewChild} from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { RacesService } from "../races.service";
import { LoggedUserService } from "../../users/logged-user.service";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import {NgFor, NgIf} from "@angular/common";
import {MatButton, MatButtonModule} from "@angular/material/button";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {SearchType} from "../../core/support/table/table.model";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {RacesCreateDialogComponent} from "../races-create-dialog/races-create-dialog.component";

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
    MatButton,
    TableSearchDirective,
    MatDialogModule,
    NgIf
  ]
})
export class RacesScreenComponent implements OnInit {

  @ViewChild('racesTable') racesTableComponent?: TableComponent;

  protected isOrganizer: boolean = false;

  protected readonly SearchType = SearchType;

  protected readonly detail = 'DETAIL';

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

  buttonClicked(data: any) {
    switch (data?.action) {
      case this.detail:
        this.router.navigate(['/race', data?.rowData?.id]);
        break;
    }
  }

  createRace(): void {
    const crDialogRef = this.dialog.open(RacesCreateDialogComponent);
    crDialogRef.afterClosed().subscribe(result => {
      if (this.racesTableComponent) {
        this.racesTableComponent.tableDataRefresh();
      }
    })
  }
}
