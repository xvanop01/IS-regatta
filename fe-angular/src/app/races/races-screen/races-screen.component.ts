import { Component, OnInit } from "@angular/core";
import { Router, RouterLink } from "@angular/router";
import { RacesService } from "../races.service";
import { LoggedUserService } from "../../users/logged-user.service";
import { TableComponent } from "../../core/support/table/table.component";
import { TableColumnDirective } from "../../core/support/table/table-column.directive";
import { NgFor } from "@angular/common";

@Component({
  selector: 'app-races',
  standalone: true,
  templateUrl: './races-screen.component.html',
  styleUrls: ['./races-screen.component.css'],
  imports: [
    TableComponent,
    TableColumnDirective,
    NgFor,
    RouterLink
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
}
