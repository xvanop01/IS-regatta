import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { CreateUserDto, UserDetailDto } from "../users.model";
import { UsersService } from "../users.service";

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update-screen.component.html',
  styleUrls: ['./user-update-screen.component.css']
})
export class UserUpdateScreenComponent implements OnInit {

  protected user: any;

  updateForm = this.formBuilder.group({
    username: '',
    password: ''
  });

  constructor(private route: ActivatedRoute,
              private usersService: UsersService,
              private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const userId = Number(routeParams.get('userId'));
    this.usersService.getUser(userId).subscribe(
      result => {
        this.user = result;
        this.updateForm.patchValue({
          username: this.user.username
        })
      });
  }

  onSubmit(): void {
    var username: string = '';
    var password: string = '';
    if (this.updateForm.value.username !== undefined && this.updateForm.value.username !== null) {
      username = this.updateForm.value.username;
    }
    if (this.updateForm.value.password !== undefined && this.updateForm.value.password !== null) {
      password = this.updateForm.value.password;
    }
    const updateUser: CreateUserDto = {
      username: username,
      password: password
    };
    this.usersService.updateUser(this.user.id, updateUser).subscribe(
      result => {
        console.log("success");
      })
  }
}
