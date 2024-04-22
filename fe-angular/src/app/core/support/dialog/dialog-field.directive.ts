import {Directive, Host, Input, OnInit} from "@angular/core";
import {DialogEnum, DialogFieldType} from "./dialog.model";
import {DialogComponent} from "./dialog.component";
import {FormControl} from "@angular/forms";

@Directive({
  selector: 'app-dialog-field',
  standalone: true
})
export class DialogFieldDirective implements OnInit {

  @Input()
  public title: string | undefined;

  @Input()
  public field: string = '';

  @Input()
  public value: any = null;

  @Input()
  public type: DialogFieldType = DialogFieldType.STRING;

  @Input()
  public enum: Array<DialogEnum> = [];

  @Input()
  public required: boolean = false;

  @Input()
  public validators: any;

  @Input()
  public info: any;

  @Input()
  public errorMessage: any;

  private dialog: DialogComponent;

  constructor(@Host() parent: DialogComponent) {
    this.dialog = parent;
  }

  ngOnInit(): void {
    let fc;
    if (this.validators) {
      fc = new FormControl(this.value, this.validators);
    } else {
      fc = new FormControl(this.value);
    }
    this.dialog.addField({
      title: this.title === undefined ? '' : this.title,
      field: this.field,
      type: this.type,
      enum: this.enum,
      pswdVisible: false,
      required: this.required,
      error: null,
      fc: fc,
      info: this.info === undefined ? '' : this.info,
      errorMassage: this.errorMessage === undefined ? '' : this.errorMessage
    })
  }

}
