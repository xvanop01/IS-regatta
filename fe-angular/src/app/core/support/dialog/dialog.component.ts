import {Component, EventEmitter, Input, Output} from "@angular/core";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {DialogField, DialogFieldType} from "./dialog.model";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {DatePipe, NgFor, NgIf} from "@angular/common";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {provideMomentDateAdapter} from "@angular/material-moment-adapter";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatIconModule} from "@angular/material/icon";

export const MONTH_FORMAT = {
  parse: {
    dateInput: 'DD.MM.YYYY',
  },
  display: {
    dateInput: 'DD.MM.YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMM YYYY',
  },
};

@Component({
  selector: 'app-dialog',
  standalone: true,
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css'],
  providers: [
    provideMomentDateAdapter(MONTH_FORMAT)
  ],
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    NgFor
  ]
})
export class DialogComponent {

  @Input()
  public data: {[key: string]: any} = {};

  @Input()
  public title: string | undefined;

  @Output()
  public submitButtonClick = new EventEmitter();

  public fields: Array<DialogField> = [];

  protected readonly DialogFieldType = DialogFieldType;

  constructor(private datePipe: DatePipe) {

  }

  public addField(field: DialogField): void {
    switch (field.type) {
      case DialogFieldType.ENUM:
        field.fc.setValue(this.data[field.field]?.value);
        break;
      case DialogFieldType.ENUM_MULTIPLE:
        let enums = [];
        for (const e of this.data[field.field]) {
          enums.push(e.value);
        }
        field.fc.setValue(enums);
        break;
      case DialogFieldType.STRING:
      case DialogFieldType.NUMBER:
      case DialogFieldType.PASSWORD:
      case DialogFieldType.DATE:
        field.fc.setValue(this.data[field.field]);
        break;
    }
    this.fields.push(field);
  }

  public onSubmitButtonClick(): void {
    for (const field of this.fields) {
      switch (field.type) {
        case DialogFieldType.STRING:
        case DialogFieldType.NUMBER:
        case DialogFieldType.ENUM:
        case DialogFieldType.ENUM_MULTIPLE:
        case DialogFieldType.PASSWORD:
          this.data[field.field] = field.fc.value;
          break;
        case DialogFieldType.DATE:
          this.data[field.field] = this.datePipe.transform(field.fc.value, 'YYYY-MM-dd');
          break;
      }
    }
    this.submitButtonClick.emit(this.data);
  }

  public validateField(field: DialogField): void {
    if (field.required && field.fc.value === null) {
      field.error = 'Field is required';
    } else {
      field.error = null;
    }
  }

  public isFieldInvalid(): boolean {
    for (const field of this.fields) {
      if (field.required && field.fc.value === null) {
        return true;
      }
    }
    return false;
  }

  public onIconClicked(field: DialogField): void {
    field.pswdVisible = !field.pswdVisible;
  }
}
