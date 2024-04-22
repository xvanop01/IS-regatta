import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  RoleListDto,
  UpdateUserDto,
  UserDetailDto
} from './users.model';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  protected apiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api/users';
  }

  public register(credentials: any): Observable<UserDetailDto> {
    return this.http.post<UserDetailDto>(`http://localhost:8080/register`, credentials, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}});
  }

  public getUser(userId: number): Observable<UserDetailDto> {
    return this.http.get<UserDetailDto>(`${this.apiUrl}/user/${userId}`);
  }

  public updateUser(userId: number, updateUserDto: UpdateUserDto): Observable<UserDetailDto> {
    return this.http.put<UserDetailDto>(`${this.apiUrl}/user/${userId}`, updateUserDto);
  }

  public getUserRoles(userId: number): Observable<RoleListDto> {
    return this.http.get<RoleListDto>(`${this.apiUrl}/user/${userId}/roles`);
  }

  public updateUserRoles(userId: number, roleListDto: RoleListDto): Observable<RoleListDto> {
    return this.http.put<RoleListDto>(`${this.apiUrl}/user/${userId}/roles`, roleListDto);
  }

  public getAllRoles(): Observable<RoleListDto> {
    return this.http.get<RoleListDto>(`${this.apiUrl}/roles`);
  }
}
