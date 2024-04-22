import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component } from '@angular/core';
import { LoginDto } from 'src/app/dto/login.interface';
import { Subject, takeUntil } from 'rxjs';
import { AuthService } from 'src/app/service/auth.service';
import { HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-form-login',
  templateUrl: './form-login.component.html',
  styleUrls: ['./form-login.component.scss'],
})
export class FormLoginComponent {
  private unsubscribeTrigger = new Subject<void>();
  formLogin: FormGroup;
  protected loginInvalidControl = false;

  constructor(private authService: AuthService, private fb: FormBuilder) {
    this.formLogin = this.fb.group({
      username: [null, [Validators.required, Validators.minLength(4)]],
      password: [null, [Validators.required, Validators.minLength(3)]],
    });
  }

  onLogin() {
    if (this.formLogin.invalid) return;

    const login: LoginDto = {
      username: this.formLogin.get('username')?.value,
      password: this.formLogin.get('password')?.value,
    };
    this.authService
      .login(login)
      .pipe(takeUntil(this.unsubscribeTrigger))
      .subscribe({
        next: () => {
          document.location.reload();
        },
        error: () => {
          this.loginInvalidControl = true;
        },
      });
  }
  onLoginOauth2() {
    this.authService.loginOAuth2();
  }
}
