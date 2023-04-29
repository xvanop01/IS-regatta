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

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    UserScreenComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: 'home', component: UserScreenComponent}
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
