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
  public value: any = null;

  @Input()
  public type: DialogFieldType = DialogFieldType.STRING;

  @Input()
  public enum: Array<DialogEnum> = [];

  @Input()
  public required: boolean = false;

  protected dialog: DialogComponent;

  constructor(@Host() parent: DialogComponent) {
    this.dialog = parent;
  }

  ngOnInit(): void {
    this.dialog.addField({
      title: this.title === undefined ? '' : this.title,
      type: this.type,
      enum: this.enum,
      required: this.required,
      error: null,
      fc: new FormControl(this.value)
    })
  }

}
