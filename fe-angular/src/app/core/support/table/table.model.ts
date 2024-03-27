export interface TableColumn {
  title: string;
  field: string;
  width: number | string;
  icon: string;
  isButton: boolean;
}

export interface TableSearch {
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
