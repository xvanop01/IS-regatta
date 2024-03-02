import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PageSpecs } from "./support/table/table.model";

@Injectable()
export class CoreService {

  protected apiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api/'
  }

  public getTableData(serviceName: string, pageSpecs: PageSpecs): Observable<any> {
    let params = new HttpParams()
      .set('pageSize', pageSpecs.pageSize)
      .set('pageNumber', pageSpecs.pageNumber);
    return this.http.get<any>(`${this.apiUrl}table-data-service/${serviceName}`,
      {withCredentials: true, params: params});
  }
}
