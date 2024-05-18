import { Game } from 'src/app/model/entities/Game.entity';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameComponent } from './pages/game/game.component';
import { PlayersOnlineComponent } from './pages/players-online/players-online.component';
import { AuthenticationComponent } from './pages/authentication/authentication.component';
import { PageHostComponent } from './pages/host/page-host/page-host.component';
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
        component: GameComponent,
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

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
