import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  CreateUserDto,
  RoleListDto,
  UpdateUserDto,
  UserDetailDto,
  UserDetailListDto
} from './users.model';

@Injectable()
export class UsersService {

  protected apiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api/users';
  }

  public register(credentials: any): Observable<UserDetailDto> {
    return this.http.post<UserDetailDto>(`http://localhost:8080/register`, credentials, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}});
  }

  public createUser(createUserDto: CreateUserDto): Observable<UserDetailDto> {
    return this.http.post<UserDetailDto>(`${this.apiUrl}`, createUserDto, {withCredentials: true});
  }

  public getUser(userId: number): Observable<UserDetailDto> {
    return this.http.get<UserDetailDto>(`${this.apiUrl}/user/${userId}`, {withCredentials: true});
  }

  public getAllUsers(): Observable<UserDetailListDto> {
    return this.http.get<UserDetailListDto>(`${this.apiUrl}`, {withCredentials: true});
  }

  public updateUser(userId: number, updateUserDto: UpdateUserDto): Observable<UserDetailDto> {
    return this.http.patch<UserDetailDto>(`${this.apiUrl}/user/${userId}`, updateUserDto, {withCredentials: true});
  }

  public getUserRoles(userId: number): Observable<RoleListDto> {
    return this.http.get<RoleListDto>(`${this.apiUrl}/user/${userId}/roles`, {withCredentials: true});
  }

  public updateUserRoles(userId: number, roleListDto: RoleListDto): Observable<RoleListDto> {
    return this.http.put<RoleListDto>(`${this.apiUrl}/user/${userId}/roles`, roleListDto, {withCredentials: true});
  }

  public getAllRoles(): Observable<RoleListDto> {
    return this.http.get<RoleListDto>(`${this.apiUrl}/roles`, { withCredentials: true });
  }
}
