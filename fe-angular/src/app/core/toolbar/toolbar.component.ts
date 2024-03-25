import { Component, Input } from "@angular/core";
import { NgIf } from "@angular/common";
import {MatIconButton} from "@angular/material/button";
import {Router} from "@angular/router";

@Component({
  selector: 'app-toolbar',
  standalone: true,
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css'],
  imports: [
    NgIf,
    MatIconButton
  ]
})
export class ToolbarComponent {

  @Input()
  public authenticated = false;

  @Input()
  public user: any;

  constructor(private router: Router) {

  }

  protected redirectToIndex(): void {
    this.router.navigate(['/']);
  }

  protected redirectToUser(): void {
    this.router.navigate(['/home'])
  }

  protected redirectToLogin(): void {
    this.router.navigate(['/login']);
  }

  protected redirectToLogout(): void {
    window.location.href = 'http://localhost:8080/logout';
  }

}
