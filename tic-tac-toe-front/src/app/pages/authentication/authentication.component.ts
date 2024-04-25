import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subject, of, switchMap, takeUntil } from 'rxjs';
import { AuthService } from 'src/app/pages/authentication/auth.service';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';
import { UtilService } from 'src/app/utils/utils.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.scss'],
})
export class AuthenticationComponent implements OnInit, OnDestroy {
  private unsubscribeTrigger = new Subject<void>();
  constructor(
    private authService: AuthService,
    private cookieService: CookieServiceService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    const isAuth = this.cookieService.getValue('is_auth');
    if (isAuth != undefined) {
      console.log('autenticado');
      this.router.navigate(['tic-tac-toe', 'home']);
      return;
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
          this.authService
            .getCookieAuthenticaton(code)
            .pipe(takeUntil(this.unsubscribeTrigger))
            .subscribe(() => {
              this.router.navigate(['tic-tac-toe', 'home']);
            });
        }
      });
  }

  ngOnDestroy(): void {
    this.unsubscribeTrigger.next();
    this.unsubscribeTrigger.complete();
  }
}
