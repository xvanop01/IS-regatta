import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import {
  CreateUdateRaceDto,
  CrewDetailDto, CrewDetailListDto,
  RaceDetailDto,
  ShipSignUpListDto, UserRaceInfoDto
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
    return this.http.put<RaceDetailDto>(`${this.apiUrl}/race/${raceId}`, updateRaceDto);
  }

  public getActiveUserRaceInfo(raceId: number): Observable<UserRaceInfoDto> {
    return this.http.get<UserRaceInfoDto>(`${this.apiUrl}/race/${raceId}/active-user`);
  }

  public getShipsForRace(raceId: number): Observable<CrewDetailListDto> {
    return this.http.get<CrewDetailListDto>(`${this.apiUrl}/race/${raceId}/ships`);
  }

  public signUpShipsForRace(raceId: number, dto: ShipSignUpListDto): Observable<CrewDetailListDto> {
    return this.http.post<CrewDetailListDto>(`${this.apiUrl}/race/${raceId}/ships`, dto);
  }

  public getCrew(crewId: number): Observable<CrewDetailDto> {
    return this.http.get<CrewDetailDto>(`${this.apiUrl}/crew/${crewId}`);
  }

  public acceptCrew(crewId: number): Observable<CrewDetailDto> {
    return this.http.post<CrewDetailDto>(`${this.apiUrl}/crew/${crewId}/accept`, null);
  }

  public declineCrew(crewId: number): Observable<any> {
    return this.http.delete<CrewDetailDto>(`${this.apiUrl}/crew/${crewId}/remove`);
  }

  public applyToCrew(crewId: number): Observable<UserRaceInfoDto> {
    return this.http.post<UserRaceInfoDto>(`${this.apiUrl}/crew/${crewId}/apply`, null);
  }

  public leaveCrew(crewId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/crew/${crewId}/leave`);
  }

  public acceptUserToCrew(crewUserId: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/crew-user/${crewUserId}/accept`, null);
  }

  public declineUserFromCrew(crewUserId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/crew-user/${crewUserId}/remove`);
  }
}
