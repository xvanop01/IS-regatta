import {FormControl} from "@angular/forms";

export interface DialogField {
  title: string,
  type: DialogFieldType,
  enum: Array<DialogEnum>,
  required: boolean,
  error: string | null,
  fc: FormControl
}

export enum DialogFieldType {
  STRING = 'text',
  NUMBER = 'number',
  ENUM = 'enum',
  DATE = 'date'
}

export interface DialogEnum {
  title: string,
  value: string
}
