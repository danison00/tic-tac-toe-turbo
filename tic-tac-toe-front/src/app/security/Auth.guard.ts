import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { CookieServiceService } from '../utils/cookie-service.service';

export const authGuard: CanActivateFn = (route, state) => {
  if (inject(CookieServiceService).getValue('user_id') == undefined)
    inject(Router).navigate(['login']);

  return true;
};
