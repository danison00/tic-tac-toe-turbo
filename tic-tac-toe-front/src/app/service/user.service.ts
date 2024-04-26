import { UsernameExists } from 'src/app/dto/UsernameExists.interface';
import { Injectable } from '@angular/core';
import { UserRegisterDto } from '../dto/UserRegisterDto.interface';
import { HttpStatusCode } from '@angular/common/http';
import { of, switchMap, throwError } from 'rxjs';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private httpService: HttpService) {}

  register(user: UserRegisterDto) {
    return this.verifyUsernameAvailable(user.username).pipe(
      switchMap((exist: boolean)=>{
        if (exist) return throwError(() => null);
        return this.httpService.post<HttpStatusCode>(
          '/public/user/register',
          user
        );
      })
    )
  }

  verifyUsernameAvailable(username: string) {
    return this.httpService
      .get<UsernameExists>( '/public/user/username-exists', { username: username },
      )
      .pipe(
        switchMap((exist: UsernameExists) => {
          return of(exist.exists);
        })
      );
  }
}
