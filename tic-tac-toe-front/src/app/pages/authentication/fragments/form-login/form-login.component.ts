import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Login } from 'src/app/dto/login.interface';
import { UrlDto } from 'src/app/dto/auth/UrlDto.interface';
import { Subject, of, switchMap, takeUntil } from 'rxjs';
import { environment } from 'src/environments/environment.dev';

@Component({
  selector: 'app-form-login',
  templateUrl: './form-login.component.html',
  styleUrls: ['./form-login.component.scss'],
})
export class FormLoginComponent {
  private unsubscribeTrigger = new Subject<void>();
  formLogin: FormGroup;
  protected loginInvalidControl = false;

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.formLogin = this.fb.group({
      username: [null, [Validators.required, Validators.minLength(4)]],
      password: [null, [Validators.required, Validators.minLength(3)]],
    });
  }

  onLogin() {
    if ((this, this.formLogin.invalid)) return;

    const login = this.formLogin.value as Login;
    console.log(login);

    this.http
      .post(
        environment.baseUrl+'/login/basicAuth',
        { username: login.username, password: login.password },
        { withCredentials: true }
      )
      .subscribe({
        next: () => {
             document.location.reload();
        },
        error: (error) => {
          if (error.status === 401) {
            this.loginInvalidControl = true;
          }
        },
      });
  }
  onLoginOauth2() {
    this.http
      .get<UrlDto>(environment.baseUrl+'/login/OAuth2/url')
      .pipe(
        switchMap((urlDto: UrlDto) => {
          return of(urlDto);
        })
      )
      .pipe(takeUntil(this.unsubscribeTrigger))
      .subscribe((urlDto: UrlDto) => {
         window.location.href = urlDto.authUrl;
      });
  }
}
