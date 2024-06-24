import { Component, Input } from "@angular/core";
import { NgIf } from "@angular/common";
import {MatIconButton} from "@angular/material/button";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

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

  constructor(private http: HttpClient,
              private router: Router) {
  }

  protected redirectToHome(): void {
    this.router.navigate(['/home'])
  }

  protected redirectToUsers(): void {
    this.router.navigate(['/users'])
  }

  protected redirectToRaces(): void {
    this.router.navigate(['/races'])
  }

  protected redirectToShips(): void {
    this.router.navigate(['/ships'])
  }

  protected redirectToLogin(): void {
    this.router.navigate(['/login']);
  }

  protected redirectToLogout(): void {
    localStorage.removeItem('token');
    this.http.post(`/logout`, null).subscribe(
      result => {
        this.router.navigate(['/login']).then(() => location.reload());
      }
    );
  }

}
