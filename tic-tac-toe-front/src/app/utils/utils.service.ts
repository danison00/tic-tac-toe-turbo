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

}
