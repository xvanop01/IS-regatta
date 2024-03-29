import {Directive, Host, Input, OnInit} from "@angular/core";
import {DialogEnum, DialogFieldType} from "./dialog.model";
import {DialogComponent} from "./dialog.component";

@Directive({
  selector: 'app-dialog-field',
  standalone: true
})
export class DialogFieldDirective implements OnInit {

  @Input()
  public title: string | undefined;

  @Input()
  public value: any = null;

  @Input()
  public type: DialogFieldType = DialogFieldType.STRING;

  @Input()
  public enum: Array<DialogEnum> = [];

  protected dialog: DialogComponent;

  constructor(@Host() parent: DialogComponent) {
    this.dialog = parent;
  }

  ngOnInit(): void {
    this.dialog.addField({
      title: this.title === undefined ? '' : this.title,
      value: this.value,
      type: this.type,
      enum: this.enum
    })
  }

}
