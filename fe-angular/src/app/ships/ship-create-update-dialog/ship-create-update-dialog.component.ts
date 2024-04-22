import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {DialogComponent} from "../../core/support/dialog/dialog.component";
import {DialogFieldDirective} from "../../core/support/dialog/dialog-field.directive";
import {DialogFieldType} from "../../core/support/dialog/dialog.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ShipsService} from "../ships.service";

@Component({
  selector: 'app-ships-create-update-dialog',
  standalone: true,
  templateUrl: './ship-create-update-dialog.component.html',
  styleUrls: ['./ship-create-update-dialog.component.css'],
  imports: [
    MatDialogModule,
    MatButtonModule,
    DialogComponent,
    DialogFieldDirective
  ]
})
export class ShipCreateUpdateDialogComponent {

  public shipId: number | undefined;

  protected readonly DialogFieldType = DialogFieldType;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<ShipCreateUpdateDialogComponent>,
              protected shipsService: ShipsService,
              private snackBar: MatSnackBar) {
    this.shipId = data?.id;
  }

  public onSubmitButtonClick(data: any): void {
    const dto = {
      name: data?.name,
      registration: data?.registration
    }
    if (this.shipId === undefined) {
      this.shipsService.createShip(dto).subscribe(result => {
        this.dialogRef.close(result);
      }, error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    } else {
      this.shipsService.updateShip(this.shipId, dto).subscribe(result => {
        this.dialogRef.close(result);
      }, error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    }
  }
}
