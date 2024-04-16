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
    this.apiUrl = 'http://localhost:8080/api/races'
  }

  public createRace(createRaceDto: CreateUdateRaceDto): Observable<RaceDetailDto> {
    return this.http.post<RaceDetailDto>(`${this.apiUrl}`, createRaceDto, {withCredentials: true});
  }

  public getRace(raceId: number): Observable<RaceDetailDto> {
    return this.http.get<RaceDetailDto>(`${this.apiUrl}/race/${raceId}`, {withCredentials: true});
  }

  public updateRace(raceId: number, updateRaceDto: CreateUdateRaceDto): Observable<RaceDetailDto> {
    return this.http.patch<RaceDetailDto>(`${this.apiUrl}/race/${raceId}`, updateRaceDto, {withCredentials: true});
  }

  public signUpActiveUser(raceId: number): Observable<RaceUserInfoDto> {
    return this.http.post<RaceUserInfoDto>(`${this.apiUrl}/race/${raceId}/sign-up`, null);
  }

  public isSignedUp(raceId: number): Observable<RaceUserInfoDto> {
    return this.http.get<RaceUserInfoDto>(`${this.apiUrl}/race/${raceId}/sign-up`);
  }
}
