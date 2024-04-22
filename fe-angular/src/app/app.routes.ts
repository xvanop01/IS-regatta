import { Routes } from '@angular/router';
import { UserDetailScreenComponent } from "./users/user-detail-screen/user-detail-screen.component";
import { RegisterScreenComponent } from "./users/register-screen/register-screen.component";
import { LoginScreenComponent } from "./users/login-screen/login-screen.component";
import { UsersScreenComponent } from "./users/users-screen/users-screen.component";
import { RacesScreenComponent } from "./races/races-screen/races-screen.component";
import {RaceDetailScreenComponent} from "./races/race-detail-screen/race-detail-screen.component";
import {ShipsScreenComponent} from "./ships/ships-screen/ships-screen.component";
import {CrewDetailScreenComponent} from "./races/crew-detail-screen/crew-detail-screen.component";

export const routes: Routes = [
  {path: '', component: UserDetailScreenComponent},
  {path: 'home', component: UserDetailScreenComponent},
  {path: 'register', component: RegisterScreenComponent},
  {path: 'login', component: LoginScreenComponent},
  {path: 'user/:userId', component: UserDetailScreenComponent},
  {path: 'users', component: UsersScreenComponent},
  {path: 'races', component: RacesScreenComponent},
  {path: 'race/:raceId', component: RaceDetailScreenComponent},
  {path: 'ships', component: ShipsScreenComponent},
  {path: 'crew/:crewId', component: CrewDetailScreenComponent}
];
