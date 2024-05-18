import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GameComponent } from './pages/game/game.component';
import { HeaderComponent } from './shared/header/header.component';
import { MarkComponent } from './pages/game/components/mark/mark.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PlayersOnlineComponent } from './pages/players-online/players-online.component';
import { ModalNewGameRequestComponent } from './pages/host/modal-new-game-request/modal-new-game-request.component';
import { ModalPlayEndComponent } from './pages/game/components/modal-play-end/modal-play-end.component';
import { AuthenticationComponent } from './pages/authentication/authentication.component';
import { PageHostComponent } from './pages/host/page-host/page-host.component';
import { HttpClientModule } from '@angular/common/http';
import { FormLoginComponent } from './pages/authentication/fragments/form-login/form-login.component';
import { FormRegisterComponent } from './pages/authentication/fragments/form-register/form-register.component';
import { ModalAccountRegisterSuccessfulComponent } from './pages/authentication/fragments/modal-account-register-successful/modal-account-register-successful.component';
import { BoardMainComponent } from './pages/game/components/board-main/board-main.component';
import { BoardSecundaryComponent } from './pages/game/components/board-secundary/board-secundary.component';

@NgModule({
  declarations: [
    AppComponent,
    GameComponent,
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
    BoardMainComponent,
    BoardSecundaryComponent,
    
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
