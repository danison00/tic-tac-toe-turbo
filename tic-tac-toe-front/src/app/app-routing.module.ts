import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TicTacToeComponent } from './pages/tic-tac-toe/tic-tac-toe.component';
import { PlayersOnlineComponent } from './pages/players-online/players-online.component';
import { AuthenticationComponent } from './pages/authentication/authentication.component';
import { PageHostComponent } from './shared/page-host/page-host.component';
import { AppComponent } from './app.component';
import { authGuard } from './security/Auth.guard';
import { FormLoginComponent } from './pages/authentication/fragments/form-login/form-login.component';
import { FormRegisterComponent } from './pages/authentication/fragments/form-register/form-register.component';

const routes: Routes = [
  {
    path: 'tic-tac-toe',
    component: PageHostComponent,
    children: [
      {
        path: 'home',
        component: PlayersOnlineComponent,
        canActivate: [authGuard],
      },
      {
        path: 'game',
        component: TicTacToeComponent,
        canActivate: [authGuard],
      },

      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: 'tic-tac-toe',
    component: AuthenticationComponent,
    children: [
      {
        path: 'login',
        component: FormLoginComponent,
      },
      {
        path: 'register',
        component: FormRegisterComponent,
      },
    ],
  },
  {
    path: '**',
    redirectTo: 'tic-tac-toe/login',
  },

  //   children: [
  //     {
  //       path: 'login',
  //       component: AuthenticationComponent,
  //     },

  //     {
  //       path: 'home',
  //       component: PlayersOnlineComponent,
  //     },
  //     {
  //       path: 'game',
  //       component: TicTacToeComponent,
  //     },
  //     // {
  //     //   path: '**',
  //     //   redirectTo: 'login',
  //     // },
  //   ],
  // },

  // {
  //   path: '**',
  //   redirectTo: 'tic-tac-toe/login',
  // },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
