import { Component, OnInit } from '@angular/core';
import { UsersService } from './users/users.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'my-project';

  roles: any;

  constructor(protected service: UsersService) {

  }

  ngOnInit(): void {
      this.service.getUserRoles().subscribe(
        result => {
          this.roles = result;
        },
        error => {
          console.log(error)
          if (error.status == 401) {
            window.location.href = `http://localhost:8080/login`;
          }
        });
  }
}
