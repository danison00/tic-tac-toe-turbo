import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subject, of, switchMap, takeUntil } from 'rxjs';
import { UtilService } from 'src/app/utils/utils.service';
import { VarsGlobalService } from 'src/app/utils/vars-global.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.scss'],
})
export class AuthenticationComponent implements OnInit, OnDestroy {
  private unsubscribeTrigger = new Subject<void>();
  constructor(
    private global: VarsGlobalService,
    private http: HttpClient,
    private util: UtilService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
   
  }

  ngOnInit() {
    const isAuth = this.util.getCookie('is_auth');
    if (isAuth) {
      console.log('autenticado');
      this.router.navigate(['tic-tac-toe', 'home']);
    }

    this.activatedRoute.queryParams
      .pipe(
        switchMap((param: Params) => {
          const code = param['code'];
          return of(code);
        })
      )
      .pipe(takeUntil(this.unsubscribeTrigger))
      .subscribe((code: string) => {
        if (code) {
          this.getTokenAuthenticatonInCookie(code);
        }
      });
  }

  ngOnDestroy(): void {
    this.unsubscribeTrigger.next();
    this.unsubscribeTrigger.complete();
  }
  private getTokenAuthenticatonInCookie(code: string) {
    const param = new HttpParams().set('code', code);
    this.http
      .get(this.global.baseUrl+'/auth/callback', {
        params: param,
        withCredentials: true,
      })
      .pipe(takeUntil(this.unsubscribeTrigger))
      .subscribe((resp: any) => {
        console.log('reload');

        document.location.reload();
      });
  }
 

}
