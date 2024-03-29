import {Component, EventEmitter, Input, Output} from "@angular/core";
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {DialogField, DialogFieldType} from "./dialog.model";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {NgFor, NgIf} from "@angular/common";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {provideMomentDateAdapter} from "@angular/material-moment-adapter";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";

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
  public data: any;

  @Input()
  public title: string | undefined;

  public fields: Array<DialogField> = [];

  @Output()
  public submitButtonClick = new EventEmitter();

  protected readonly DialogFieldType = DialogFieldType;

  constructor() {
  }

  public addField(field: DialogField): void {
    this.fields.push(field);
  }

  public onSubmitButtonClick(): void {
    this.submitButtonClick.emit(this.data);
  }
}
