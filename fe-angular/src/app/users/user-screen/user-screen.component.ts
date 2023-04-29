import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { LoggedUserService } from "../logged-user.service";

@Component({
  selector: 'user-screen',
  templateUrl: './user-screen.component.html',
  styleUrls: ['./user-screen.component.css']
})
export class UserScreenComponent implements OnInit {

  public user: any;

  public authenticated: boolean = false;

  constructor(protected loggedUserService: LoggedUserService) {

  }

  ngOnInit(): void {
    this.loggedUserService.getLoggedUser().subscribe(
      result => {
        this.user = result;
        this.authenticated = true;
      });
  }
}
