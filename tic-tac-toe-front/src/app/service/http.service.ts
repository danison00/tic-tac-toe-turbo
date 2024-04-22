import {
  HttpClient,
  HttpErrorResponse,
  HttpStatusCode,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable, catchError, of, switchMap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.dev';

@Injectable({
  providedIn: 'root',
})
export class HttpService {
  constructor(private http: HttpClient) {}

  get<T>(url: string, params?: any ): Observable<T> {
    return this.http.get<T>(environment.baseUrl + url, {withCredentials: true, params: params}, ).pipe(
      catchError((err: HttpErrorResponse) => {
        alert(
          'Aconteceu um erro interno nos nossos servidores. :(\nJá estamos trabalhando nisso!'
        );
        return EMPTY;
      }),
      switchMap((resp) => {
        return of(resp);
      })
    );
  }

  post<T>(url: string, body: any): Observable<T> {
    return this.http
      .post<T>(environment.baseUrl + url, body, {
        withCredentials: true,
      })
      .pipe(
        switchMap((value: T) => {
          return of(value);
        }),
        catchError((err: HttpErrorResponse) => {
          switch (err.status) {
            case HttpStatusCode.Unauthorized: {
              return throwError(() => err);
            }
          }
          alert(
            'Aconteceu um erro interno nos nossos servidores. :(\nJá estamos trabalhando nisso!'
          );

          return EMPTY;
        })
      );
  }
}
