import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
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
import { RacesService } from './races/races.service';
import { RacesScreenComponent } from './races/races-screen/races-screen.component';
import { TableComponent } from './core/support/table/table.component';
import { TableColumnDirective } from './core/support/table/table-column.directive';
import { MatIconModule } from '@angular/material/icon'
import { MatButtonModule } from '@angular/material/button'

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
    UsersManagementScreenComponent,
    RacesScreenComponent,
    TableColumnDirective,
    TableComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    MatButtonModule,
    MatIconModule,
    BrowserAnimationsModule,
    RouterModule.forRoot([
      {path: '', component: IndexComponent},
      {path: 'home', component: UserScreenComponent},
      {path: 'register', component: RegisterScreenComponent},
      {path: 'login', component: LoginScreenComponent},
      {path: 'user/:userId', component: UserScreenComponent},
      {path: 'user/:userId/update', component: UserUpdateScreenComponent},
      {path: 'user/:userId/roles/update', component: RolesUpdateScreenComponent},
      {path: 'users', component: UsersManagementScreenComponent},
      {path: 'races', component: RacesScreenComponent},
      {path: 'races/create', component: RacesScreenComponent}
    ]),
  ],
  providers: [
    UsersService,
    RacesService,
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
