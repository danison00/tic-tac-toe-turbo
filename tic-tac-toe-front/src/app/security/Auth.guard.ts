import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './Auth.service';

export const authGuard: CanActivateFn = (route, state) => {  
  if (!inject(AuthService).isAuth()) inject(Router).navigate(['login']);
  return true;
};
