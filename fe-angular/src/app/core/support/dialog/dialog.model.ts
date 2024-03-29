export interface DialogField {
  title: string,
  type: DialogFieldType,
  value: any,
  enum: Array<DialogEnum>
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
