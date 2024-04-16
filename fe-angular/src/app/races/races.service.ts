import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import {
  CreateUdateRaceDto,
  RaceDetailDto,
  RaceUserInfoDto
} from "./races.model";

@Injectable({
  providedIn: 'root'
})
export class RacesService {

  protected apiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api/races';
  }

  public createRace(createRaceDto: CreateUdateRaceDto): Observable<RaceDetailDto> {
    return this.http.post<RaceDetailDto>(`${this.apiUrl}`, createRaceDto);
  }

  public getRace(raceId: number): Observable<RaceDetailDto> {
    return this.http.get<RaceDetailDto>(`${this.apiUrl}/race/${raceId}`);
  }

  public updateRace(raceId: number, updateRaceDto: CreateUdateRaceDto): Observable<RaceDetailDto> {
    return this.http.patch<RaceDetailDto>(`${this.apiUrl}/race/${raceId}`, updateRaceDto);
  }

  public signUpActiveUser(raceId: number): Observable<RaceUserInfoDto> {
    return this.http.post<RaceUserInfoDto>(`${this.apiUrl}/race/${raceId}/sign-up`, null);
  }

  public isSignedUp(raceId: number): Observable<RaceUserInfoDto> {
    return this.http.get<RaceUserInfoDto>(`${this.apiUrl}/race/${raceId}/sign-up`);
  }

  public cancelRegistration(raceId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/race/${raceId}/cancel-registration`, null);
  }
}
