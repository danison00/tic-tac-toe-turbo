import { Injectable } from '@angular/core';
import { CookieServiceService } from '../utils/cookie-service.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private cookieService: CookieServiceService) {}

  isAuth(): boolean {
    if (this.cookieService.getValue('user_id')) return true;
    return false;
  }
}
