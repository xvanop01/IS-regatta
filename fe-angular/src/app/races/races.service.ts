import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import {
  CreateUdateRaceDto,
  CrewDetailDto, CrewDetailListDto,
  RaceDetailDto,
  ShipSignUpListDto
} from "./races.model";

@Injectable({
  providedIn: 'root'
})
export class RacesService {

  protected apiUrl;
  protected shipApiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api/races';
    this.shipApiUrl = 'http://localhost:8080/api/ships';
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

  public getShipsForRace(raceId: number): Observable<CrewDetailListDto> {
    return this.http.get<CrewDetailListDto>(`${this.apiUrl}/race/${raceId}/ships`);
  }

  public signUpShipsForRace(raceId: number, dto: ShipSignUpListDto): Observable<CrewDetailListDto> {
    return this.http.post<CrewDetailListDto>(`${this.apiUrl}/race/${raceId}/ships`, dto);
  }

  public acceptCrew(crewId: number): Observable<CrewDetailDto> {
    return this.http.post<CrewDetailDto>(`${this.apiUrl}/crew/${crewId}/accept`, null);
  }

  public declineCrew(crewId: number): Observable<any> {
    return this.http.post<CrewDetailDto>(`${this.apiUrl}/crew/${crewId}/remove`, null);
  }
}
