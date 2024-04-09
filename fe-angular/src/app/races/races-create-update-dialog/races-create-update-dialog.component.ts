import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {DialogComponent} from "../../core/support/dialog/dialog.component";
import {DialogFieldDirective} from "../../core/support/dialog/dialog-field.directive";
import {DialogFieldType} from "../../core/support/dialog/dialog.model";
import {RacesService} from "../races.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-races-create-update-dialog',
  standalone: true,
  templateUrl: './races-create-update-dialog.component.html',
  styleUrls: ['./races-create-update-dialog.component.css'],
  imports: [
    MatDialogModule,
    MatButtonModule,
    DialogComponent,
    DialogFieldDirective
  ]
})
export class RacesCreateUpdateDialogComponent {

  public raceId: number | undefined;

  protected readonly DialogFieldType = DialogFieldType;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<RacesCreateUpdateDialogComponent>,
              protected racesService: RacesService,
              private snackBar: MatSnackBar) {
    this.raceId = data?.id;
    console.log(this.raceId);
  }

  public onSubmitButtonClick(data: any): void {
    const dto = {
      name: data?.name,
      location: data?.location,
      date: data?.date,
      signUpUntil: data?.signUpUntil,
      description: data?.description,
      isPublic: data?.isPublic
    }
    if (this.raceId === undefined) {
      this.racesService.createRace(dto).subscribe(result => {
        this.dialogRef.close(result);
      }, error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    } else {
      this.racesService.updateRace(this.raceId, dto).subscribe(result => {
        this.dialogRef.close(result);
      }, error => {
        let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
      });
    }
  }
}
