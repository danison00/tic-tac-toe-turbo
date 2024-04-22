import { HttpClient, HttpStatusCode } from '@angular/common/http';
import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError, of, switchMap, throwError } from 'rxjs';
import { UserRegister } from 'src/app/dto/UserRegister.interface';
import { UsernameExists } from 'src/app/dto/UsernameExists.interface';
import { environment } from 'src/environments/environment.dev';

@Component({
  selector: 'app-form-register',
  templateUrl: './form-register.component.html',
  styleUrls: ['./form-register.component.scss'],
})
export class FormRegisterComponent implements OnDestroy{
  protected formRegister: FormGroup;
  protected usernameUnavailableControl = false;
  modalRegisterSuccessfulControl = false;
  constructor(fb: FormBuilder, private http: HttpClient) {
    this.formRegister = fb.group({
      name: [null, [Validators.minLength(5), Validators.required]],
      username: [null, [Validators.minLength(4), Validators.required]],
      password: [null, Validators.required],
    });
  }
  ngOnDestroy(): void {
   this.modalRegisterSuccessfulControl = false;
   this.formRegister.reset()
  }

  onRegisterOauth2() {}
  onRegister() {
    if (this.formRegister.invalid) return;

    const newUser = this.formRegister.value as UserRegister;

    console.log(newUser);

    this.verifyUsernameAvailable()
      .pipe(
        catchError(() => {
          this.usernameUnavailableControl = true;
          return of();
        }),
        switchMap(() => {
          return this.http.post<HttpStatusCode>(
            environment.baseUrl+'/user/register',
            {
              username: newUser.username,
              password: newUser.password,
              name: newUser.name,
            }
          );
        })
      )
      .subscribe({
        next: () => {
          this.modalRegisterSuccessfulControl = true;
        },
        error: (error) => {
          console.log(error);
        },
      });
  }

  verifyUsernameAvailable() {
    const username = this.formRegister.get('username')?.value;
    return this.http
      .get<UsernameExists>(environment.baseUrl+'/user/username-exists', {
        params: { username: username },
      })
      .pipe(
        switchMap((exists: UsernameExists) => {
          console.log(exists);
          if (!exists.exists) {
            return of(true);
          }
          return throwError(() => true);
        })
      );
  }
}
