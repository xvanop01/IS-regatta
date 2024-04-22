import {FormControl} from "@angular/forms";

export interface DialogField {
  title: string,
  field: string,
  type: DialogFieldType,
  enum: Array<DialogEnum>,
  pswdVisible: boolean,
  required: boolean,
  error: string | null,
  fc: FormControl,
  info: string,
  errorMassage: string
}

export enum DialogFieldType {
  STRING = 'text',
  NUMBER = 'number',
  BOOLEAN = 'boolean',
  ENUM = 'enum',
  ENUM_MULTIPLE = 'enum_multiple',
  DATE = 'date',
  PASSWORD = 'password'
}

export interface DialogEnum {
  title: string,
  value: string
}
