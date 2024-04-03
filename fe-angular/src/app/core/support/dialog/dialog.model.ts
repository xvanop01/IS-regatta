import {FormControl} from "@angular/forms";

export interface DialogField {
  title: string,
  field: string,
  type: DialogFieldType,
  enum: Array<DialogEnum>,
  pswdVisible: boolean,
  required: boolean,
  error: string | null,
  fc: FormControl
}

export enum DialogFieldType {
  STRING = 'text',
  NUMBER = 'number',
  ENUM = 'enum',
  ENUM_MULTIPLE = 'enum_multiple',
  DATE = 'date',
  PASSWORD = 'password'
}

export interface DialogEnum {
  title: string,
  value: string
}
