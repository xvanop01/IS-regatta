import {Component, OnInit, ViewChild} from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { RacesService } from "../races.service";
import { LoggedUserService } from "../../users/logged-user.service";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import { NgFor } from "@angular/common";
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
    MatDialogModule
  ]
})
export class RacesScreenComponent implements OnInit {

  @ViewChild('racesTable') racesTableComponent?: TableComponent;

  protected isOrganizer: boolean = false;

  protected readonly SearchType = SearchType;

  constructor(private dialog: MatDialog,
              private router: Router,
              private racesService: RacesService,
              private loggedUserService: LoggedUserService) {
  }

  ngOnInit(): void {
    this.loggedUserService.getLoggedUserRoles().subscribe(
      roles => {
        if (roles != null) {
          for (const role in roles) {
            if (role === 'ADMIN' || role === 'ORGANIZER') {
              this.isOrganizer = true;
            }
          }
        }
      }
    )
  }

  buttonClicked($event: any) {

  }

  createRace(): void {
    const crDialogRef = this.dialog.open(RacesCreateDialogComponent);
    crDialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (this.racesTableComponent) {
        this.racesTableComponent.tableDataRefresh();
      }
    })
  }
}
