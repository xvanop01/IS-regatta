import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { UsersService } from "./users/users.service";
import { RacesService } from "./races/races.service";
import { CoreService } from "./core/core.service";
import { LoggedUserService } from "./users/logged-user.service";
import { provideHttpClient } from "@angular/common/http";
import { provideAnimations } from "@angular/platform-browser/animations";
import { provideNativeDateAdapter } from "@angular/material/core";
import {DatePipe} from "@angular/common";
import {ShipsService} from "./ships/ships.service";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideAnimations(),
    provideNativeDateAdapter(),
    {provide: LoggedUserService},
    {provide: UsersService},
    {provide: RacesService},
    {provide: CoreService},
    {provide: ShipsService},
    {provide: DatePipe}
  ]
};
