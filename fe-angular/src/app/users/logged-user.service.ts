import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { RoleListDto, UserDetailDto } from "./users.model";

@Injectable({
  providedIn: 'root'
})export class LoggedUserService {

  protected apiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api';
  }

  public getLoggedUser(): Observable<UserDetailDto> {
    return this.http.get<UserDetailDto>(`${this.apiUrl}/user`, {withCredentials: true});
  }

  public getLoggedUserRoles(): Observable<RoleListDto> {
    return this.http.get<RoleListDto>(`${this.apiUrl}/user/roles`, {withCredentials: true});
  }
}
