import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ToolbarComponent } from './core/toolbar/toolbar.component';
import { UserScreenComponent } from './users/user-screen/user-screen.component';
import { LoggedUserService } from './users/logged-user.service';
import { UsersService } from './users/users.service';
import { RegisterScreenComponent } from './users/register-screen/register-screen.component';
import { LoginScreenComponent } from './users/login-screen/login-screen.component';
import { UserUpdateScreenComponent } from './users/user-update-screen/user-update-screen.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RolesUpdateScreenComponent } from './users/roles-update-screen/roles-update-screen.component';
import { IndexComponent } from './core/index/index.component';
import { UsersManagementScreenComponent } from './users/users-management-screen/users-management-screen.component';
import { MatSnackBarModule, MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    IndexComponent,
    UserScreenComponent,
    RegisterScreenComponent,
    LoginScreenComponent,
    UserUpdateScreenComponent,
    RolesUpdateScreenComponent,
    UsersManagementScreenComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    BrowserAnimationsModule,
    RouterModule.forRoot([
      {path: '', component: IndexComponent},
      {path: 'home', component: UserScreenComponent},
      {path: 'register', component: RegisterScreenComponent},
      {path: 'login', component: LoginScreenComponent},
      {path: 'user/:userId', component: UserScreenComponent},
      {path: 'user/:userId/update', component: UserUpdateScreenComponent},
      {path: 'user/:userId/roles/update', component: RolesUpdateScreenComponent},
      {path: 'users', component: UsersManagementScreenComponent}
    ]),
  ],
  providers: [
    UsersService,
    LoggedUserService,
    HttpClient,
    { provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: {
      panelClass: ['background-red'],
      verticalPosition: 'top',
      horizontalPosition: 'right',
      duration: 50000
    }}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
