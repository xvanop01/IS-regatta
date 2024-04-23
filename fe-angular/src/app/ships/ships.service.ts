import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CreateUpdateShipDto, ShipDetailDto} from "./ships.model";

@Injectable({
  providedIn: 'root'
})
export class ShipsService {

  protected apiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = '/api/ships';
  }

  public getShip(shipId: number): Observable<ShipDetailDto> {
    return this.http.get<ShipDetailDto>(`${this.apiUrl}/ship/${shipId}`);
  }

  public createShip(shipDto: CreateUpdateShipDto): Observable<ShipDetailDto> {
    return this.http.post<ShipDetailDto>(`${this.apiUrl}`, shipDto);
  }

  public updateShip(shipId: number, shipDto: CreateUpdateShipDto): Observable<ShipDetailDto> {
    return this.http.put<ShipDetailDto>(`${this.apiUrl}/ship/${shipId}`, shipDto)
  }
}
