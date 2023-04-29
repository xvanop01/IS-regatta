import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ToolbarComponent } from './shared/toolbar/toolbar.component';
import { UserScreenComponent } from './users/user-screen/user-screen.component';
import { LoggedUserService } from './users/logged-user.service';
import { UsersService } from './users/users.service';
import { RegisterScreenComponent } from './users/register-screen/register-screen.component';
import { LoginScreenComponent } from './users/login-screen/login-screen.component';
import { UserUpdateScreenComponent } from './users/user-update-screen/user-update-screen.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    UserScreenComponent,
    RegisterScreenComponent,
    LoginScreenComponent,
    UserUpdateScreenComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot([
      {path: 'home', component: UserScreenComponent},
      {path: 'register', component: RegisterScreenComponent},
      {path: 'login', component: LoginScreenComponent},
      {path: 'user/:userId/update', component: UserUpdateScreenComponent}
    ]),
  ],
  providers: [
    UsersService,
    LoggedUserService,
    HttpClient
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
