import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { UsersService } from "./users/users.service";
import { RacesService } from "./races/races.service";
import { CoreService } from "./core/core.service";
import { LoggedUserService } from "./users/logged-user.service";
import { provideHttpClient } from "@angular/common/http";
import { provideAnimations } from "@angular/platform-browser/animations";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideAnimations(),
    {provide: LoggedUserService},
    {provide: UsersService},
    {provide: RacesService},
    {provide: CoreService},
  ]
};
