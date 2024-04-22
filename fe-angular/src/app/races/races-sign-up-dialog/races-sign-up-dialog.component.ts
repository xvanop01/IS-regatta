import {Component, Inject} from "@angular/core";
import {DialogComponent} from "../../core/support/dialog/dialog.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DialogFieldDirective} from "../../core/support/dialog/dialog-field.directive";
import {DialogEnum, DialogFieldType} from "../../core/support/dialog/dialog.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {RacesService} from "../races.service";
import {ShipSignUpListDto} from "../races.model";

@Component({
  selector: 'app-races-sign-up-dialog',
  standalone: true,
  templateUrl: './races-sign-up-dialog.component.html',
  styleUrls: ['./races-sign-up-dialog.component.css'],
  imports: [
    DialogComponent,
    DialogFieldDirective
  ]
})
export class RacesSignUpDialogComponent {

  public userShips: Array<DialogEnum> = [];

  protected readonly DialogFieldType = DialogFieldType;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<RacesSignUpDialogComponent>,
              private racesService: RacesService,
              private snackBar: MatSnackBar) {
    for (const ship of data?.ships) {
      this.userShips.push({
        title: (ship.shipRegistration ? ship.shipRegistration : '') + ship.shipName,
        value: ship.id?.toString()
      });
    }
  }

  public onSubmitButtonClick(data: any): void {
    let shipsDto: ShipSignUpListDto = {ships: []};
    if (data && this.data.raceId && data.ship) {
      for (const ship of data.ship) {
        shipsDto.ships.push(Number(ship));
      }
      this.racesService.signUpShipsForRace(this.data.raceId, shipsDto).subscribe(result => {
        this.dialogRef.close(result);
      }, error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    } else {
      let snackBarRef = this.snackBar.open('Missing raceId or no ships were selected.', 'X');

    }
  }
}
