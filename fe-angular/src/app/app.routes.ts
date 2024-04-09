import { Routes } from '@angular/router';
import { IndexComponent } from "./core/index/index.component";
import { UserDetailScreenComponent } from "./users/user-detail-screen/user-detail-screen.component";
import { RegisterScreenComponent } from "./users/register-screen/register-screen.component";
import { LoginScreenComponent } from "./users/login-screen/login-screen.component";
import { UsersManagementScreenComponent } from "./users/users-management-screen/users-management-screen.component";
import { RacesScreenComponent } from "./races/races-screen/races-screen.component";
import {RaceDetailScreenComponent} from "./races/race-detail-screen/race-detail-screen.component";

export const routes: Routes = [
  {path: '', component: IndexComponent},
  {path: 'home', component: UserDetailScreenComponent},
  {path: 'register', component: RegisterScreenComponent},
  {path: 'login', component: LoginScreenComponent},
  {path: 'user/:userId', component: UserDetailScreenComponent},
  {path: 'users', component: UsersManagementScreenComponent},
  {path: 'races', component: RacesScreenComponent},
  {path: 'race/:raceId', component: RaceDetailScreenComponent}
];
