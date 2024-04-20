import {FormControl} from "@angular/forms";

export interface TableColumn {
  title: string;
  field: string;
  width: number | string;
  icon: string;
  isButton: boolean;
  ifField: any;
  ifValue: any;
}

export interface TableSearch {
  title: string;
  column: string;
  type: SearchType;
  fc: FormControl
}

export interface Filter {
  title: string;
  column: string;
  value: string;
  type: SearchType;
}

export enum SearchType {
  STRING,
  MONTH
}

export interface PageSpecs {
  pageSize: number;
  pageNumber: number;
}

export enum dir {
  ASC = 'ASC',
  DESC = 'DESC'
}

export interface SortSpecs {
  column: string;
  direction: dir;
}
