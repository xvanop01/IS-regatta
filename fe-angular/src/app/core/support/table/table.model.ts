export interface TableColumn {
  title: string;
  field: string;
  width: number | string;
  icon: string;
  isButton: boolean;
}

export interface PageSpecs {
  pageSize: number;
  pageNumber: number;
}
