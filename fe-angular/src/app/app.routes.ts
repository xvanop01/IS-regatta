import { Routes } from '@angular/router';
import { IndexComponent } from "./core/index/index.component";
import { UserScreenComponent } from "./users/user-screen/user-screen.component";
import { RegisterScreenComponent } from "./users/register-screen/register-screen.component";
import { LoginScreenComponent } from "./users/login-screen/login-screen.component";
import { UserUpdateScreenComponent } from "./users/user-update-screen/user-update-screen.component";
import { RolesUpdateScreenComponent } from "./users/roles-update-screen/roles-update-screen.component";
import { UsersManagementScreenComponent } from "./users/users-management-screen/users-management-screen.component";
import { RacesScreenComponent } from "./races/races-screen/races-screen.component";

export const routes: Routes = [
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
];
