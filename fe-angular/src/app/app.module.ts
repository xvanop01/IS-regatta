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

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    UserScreenComponent,
    RegisterScreenComponent,
    LoginScreenComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: 'home', component: UserScreenComponent},
      {path: 'register', component: RegisterScreenComponent},
      {path: 'login', component: LoginScreenComponent}
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
