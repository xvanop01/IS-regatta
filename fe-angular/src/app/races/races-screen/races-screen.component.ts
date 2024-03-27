import { Component, OnInit } from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { RacesService } from "../races.service";
import { LoggedUserService } from "../../users/logged-user.service";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import { NgFor } from "@angular/common";
import {MatButton} from "@angular/material/button";
import {TableSearchDirective} from "../../core/support/table/table-search.directive";
import {SearchType} from "../../core/support/table/table.model";

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
    MatButton,
    TableSearchDirective
  ]
})
export class RacesScreenComponent implements OnInit {

  protected isOrganizer: boolean = false;

  constructor(private router: Router,
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
    this.router.navigate(['races', 'create']);
  }

  protected readonly SearchType = SearchType;
}
