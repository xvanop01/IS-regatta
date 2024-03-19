import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import {PageSpecs, SortSpecs} from "./support/table/table.model";

@Injectable({
  providedIn: 'root'
})
export class CoreService {

  protected apiUrl;

  constructor(protected http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api/'
  }

  public getTableData(serviceName: string, pageSpecs: PageSpecs, sortSpecs: SortSpecs | null): Observable<any> {
    if (sortSpecs?.column === '') {
      sortSpecs = null;
    }
    let paramObject = {
      pageSize: pageSpecs.pageSize,
      pageNumber: pageSpecs.pageNumber,
      sortCriteria: sortSpecs
    }
    let params = new HttpParams()
      .set('tableDataRequestDto', JSON.stringify(paramObject));
    return this.http.get<any>(`${this.apiUrl}table-data-service/${serviceName}`,
      {withCredentials: true, params: params});
  }
}
