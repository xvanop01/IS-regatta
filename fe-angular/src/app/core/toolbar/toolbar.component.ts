import { Component, Input } from "@angular/core";
import { NgIf } from "@angular/common";

@Component({
  selector: 'app-toolbar',
  standalone: true,
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css'],
  imports: [
    NgIf
  ]
})
export class ToolbarComponent {

  @Input()
  public authenticated = false;

  @Input()
  public user: any;

  constructor() {

  }

}
