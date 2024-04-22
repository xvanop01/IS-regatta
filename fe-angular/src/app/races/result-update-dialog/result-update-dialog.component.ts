import {Component, Inject} from "@angular/core";
import {DialogComponent} from "../../core/support/dialog/dialog.component";
import {DialogFieldDirective} from "../../core/support/dialog/dialog-field.directive";
import {DialogFieldType} from "../../core/support/dialog/dialog.model";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {RacesService} from "../races.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Validators} from "@angular/forms";
import {CrewResultsUpdateDto} from "../races.model";

@Component({
  selector: 'app-result-update-dialog',
  standalone: true,
  templateUrl: 'result-update-dialog.component.html',
  styleUrls: ['result-update-dialog.component.css'],
  imports: [
    DialogComponent,
    DialogFieldDirective
  ]
})
export class ResultUpdateDialogComponent {

  public crewName: string;

  public crewId: number;

  protected readonly DialogFieldType = DialogFieldType;

  protected readonly Validators = Validators;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<ResultUpdateDialogComponent>,
              private racesService: RacesService,
              private snackBar: MatSnackBar) {
    this.crewId = data?.id;
    this.crewName = data?.crewName;
  }

  public onSubmitButtonClick(data: any): void {
    const dto: CrewResultsUpdateDto = {
      position: data?.position,
      time: data?.time
    }
    this.racesService.updateCrewResults(this.crewId, dto).subscribe(result => {
      this.dialogRef.close(result);
    }, error => {
      this.snackBar.open(error.status + ': ' + error.error, 'X');
    })
  }
}
