import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CreateRaceDto, RaceDetailDto, RaceDetailListDto, UpdateRaceDatesDto, UpdateRaceDto } from "./races.model";

@Injectable({
  providedIn: 'root'
})
export class RacesService {

  protected apiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api/races'
  }

  public getAllRaces(): Observable<RaceDetailListDto> {
    return this.http.get<RaceDetailListDto>(`${this.apiUrl}`, {withCredentials: true});
  }

  public createRace(createRaceDto: CreateRaceDto): Observable<RaceDetailDto> {
    return this.http.post<RaceDetailDto>(`${this.apiUrl}`, createRaceDto, {withCredentials: true});
  }

  public getRace(raceId: number): Observable<RaceDetailDto> {
    return this.http.get<RaceDetailDto>(`${this.apiUrl}/race/${raceId}`, {withCredentials: true});
  }

  public updateRace(raceId: number, updateRaceDto: UpdateRaceDto): Observable<RaceDetailDto> {
    return this.http.patch<RaceDetailDto>(`${this.apiUrl}/race/${raceId}`, updateRaceDto, {withCredentials: true});
  }

  public activateRace(raceId: number): Observable<RaceDetailDto> {
    return this.http.post<RaceDetailDto>(`${this.apiUrl}/race/${raceId}/open`, null, {withCredentials: true});
  }

  public updateRaceDates(raceId: number, updateRaceDatesDto: UpdateRaceDatesDto): Observable<RaceDetailDto> {
    return this.http.patch<RaceDetailDto>(
      `${this.apiUrl}/race/${raceId}/dates`, updateRaceDatesDto, {withCredentials: true}
    );
  }
}
