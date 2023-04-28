import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class UsersService {

  protected apiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api/users';
  }

  public getUserRoles(): Observable<any> {
    return this.http.get(`${this.apiUrl}/roles`, { withCredentials: true });
  }
}
