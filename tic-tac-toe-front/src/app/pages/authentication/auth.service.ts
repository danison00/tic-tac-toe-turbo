import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { LoginDto } from '../../dto/login.interface';
import { UrlDto } from '../../dto/auth/UrlDto.interface';
import { FormLoginComponent } from './fragments/form-login/form-login.component';
import { HttpService } from '../../service/http.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private router: Router,
    private httpService: HttpService
  ) {}

  login(login: LoginDto) {
    return this.httpService.post('/login/basicAuth', login);
  }

  loginOAuth2() {
    this.httpService
      .get<UrlDto>('/login/OAuth2/url')
      .subscribe((urlDto: UrlDto) => {
        window.location.href = urlDto.authUrl;
      });
  }
  logout() {
    this.httpService.get('/logout').subscribe(() => {
      this.router.navigate([FormLoginComponent]);
    });
  }
  getCookieAuthenticaton(code: string) {
    return this.httpService.get('/login/OAuth2/callback', {
      code: code,
    });
  }
}
