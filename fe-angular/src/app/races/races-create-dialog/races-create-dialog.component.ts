import {Component} from "@angular/core";
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {DialogComponent} from "../../core/support/dialog/dialog.component";
import {DialogFieldDirective} from "../../core/support/dialog/dialog-field.directive";
import {DialogFieldType} from "../../core/support/dialog/dialog.model";
import {RacesService} from "../races.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-races-create-dialog',
  standalone: true,
  templateUrl: './races-create-dialog.component.html',
  styleUrls: ['./races-create-dialog.component.css'],
  imports: [
    MatDialogModule,
    MatButtonModule,
    DialogComponent,
    DialogFieldDirective
  ]
})
export class RacesCreateDialogComponent {

  protected readonly DialogFieldType = DialogFieldType;

  constructor(public dialogRef: MatDialogRef<RacesCreateDialogComponent>,
              protected racesService: RacesService,
              private snackBar: MatSnackBar) {
  }

  public onSubmitButtonClick(data: any): void {
    this.racesService.createRace({
      name: data?.name,
      location: data?.location,
      date: data?.date,
      signUpUntil: data?.signUpUntil,
      description: data?.description,
      isPublic: data?.isPublic
    }).subscribe(result => {
      this.dialogRef.close(result);
    }, error => {
      let snackBarRef = this.snackBar.open(error.status + ': ' + error.error, 'X');
    });
  }
}
