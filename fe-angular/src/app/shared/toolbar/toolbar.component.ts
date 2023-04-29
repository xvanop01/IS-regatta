import {ChangeDetectionStrategy, Component, Input, OnInit } from "@angular/core";
import { LoggedUserService } from "src/app/users/logged-user.service";
import { UserDetailDto } from "src/app/users/users.model";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  @Input()
  public authenticated = false;

  @Input()
  public user: any;

  constructor(protected loggedUserService: LoggedUserService) {

  }

  ngOnInit(): void {
  }

}
