import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TicTacToeComponent } from './pages/tic-tac-toe/tic-tac-toe.component';
import { HeaderComponent } from './shared/header/header.component';
import { MarkComponent } from './pages/tic-tac-toe/components/mark/mark.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PlayersOnlineComponent } from './pages/players-online/players-online.component';
import { ModalNewGameRequestComponent } from './shared/modal-new-game-request/modal-new-game-request.component';
import { ModalPlayEndComponent } from './pages/tic-tac-toe/components/modal-play-end/modal-play-end.component';
import { AuthenticationComponent } from './pages/authentication/authentication.component';
import { PageHostComponent } from './shared/page-host/page-host.component';
import { HttpClientModule } from '@angular/common/http';
import { FormLoginComponent } from './pages/authentication/fragments/form-login/form-login.component';
import { FormRegisterComponent } from './pages/authentication/fragments/form-register/form-register.component';
import { ModalAccountRegisterSuccessfulComponent } from './pages/authentication/fragments/modal-account-register-successful/modal-account-register-successful.component';

@NgModule({
  declarations: [
    AppComponent,
    TicTacToeComponent,
    HeaderComponent,
    MarkComponent,
    PlayersOnlineComponent,
    ModalNewGameRequestComponent,
    ModalPlayEndComponent,
    AuthenticationComponent,
    PageHostComponent,
    FormLoginComponent,
    FormRegisterComponent,
    ModalAccountRegisterSuccessfulComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    
    
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
