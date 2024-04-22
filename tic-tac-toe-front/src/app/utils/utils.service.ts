import { ActivatedRoute, ParamMap } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable, of, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UtilService {
  constructor(private activatedRoute: ActivatedRoute) {}
  
  
  getIdByUrl(): Observable<string> {    
    return this.activatedRoute.queryParamMap.pipe(
      switchMap((params: ParamMap) => {
        const id = params.get('id');
        if(id == null) return of()
         
        return of(id);
      }), 
    );
  }
  getIdGameByUrl(): Observable<string | null> {

    return this.activatedRoute.queryParamMap.pipe(
      switchMap((params: ParamMap) => {
        const id = params.get('idGame');
        return of(id);
      })
    );
  }
  getCookie(name: string): string | null {
    const cookies = document.cookie.split('; '); // Separar a string de cookies em um array

    // Iterar sobre os cookies e encontrar o cookie com o nome especificado
    for (const cookie of cookies) {
      const [cookieName, cookieValue] = cookie.split('='); // Separar o nome e o valor do cookie

      if (cookieName === name) {
        return cookieValue; // Decodificar o valor do cookie e retorná-lo
      }
    }
    return null;
  }
}
