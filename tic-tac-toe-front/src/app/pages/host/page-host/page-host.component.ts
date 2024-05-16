import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, first, takeUntil } from 'rxjs';
import { User } from 'src/app/model/interfaces/user.interface';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';
import { PageHostService } from './page-host.service';
import { UserView } from 'src/app/model/user-view.interface';

@Component({
  selector: 'app-page-host',
  templateUrl: './page-host.component.html',
  styleUrls: ['./page-host.component.scss'],
})
export class PageHostComponent {
  user!: User;
  private $unsubscribeTrigger = new Subject<void>();
  private newGameEvent!: Event;
  protected userChallenger!: UserView;
  protected modalNewChallenge = false;

  constructor(
    private cookieService: CookieServiceService,
    private router: Router,
    private hostService: PageHostService
  ) {}

  ngOnInit(): void {
    this._listener();
  }
  private _listener() {
    this.hostService
      .getNewChallenge()
      .pipe(takeUntil(this.$unsubscribeTrigger))
      .subscribe((userView: UserView) => {
        this.userChallenger = userView;
        this.modalNewChallenge = true;
      });
    this.hostService
      .listenNewGame()
      .pipe(takeUntil(this.$unsubscribeTrigger))
      .subscribe((gameId: string) => {
        this.router.navigate(['tic-tac-toe', 'game'], {
          queryParams: {
            gameId: gameId,
          },
        });
      });
  }
  ngOnDestroy(): void {
    this.$unsubscribeTrigger.next();
    this.$unsubscribeTrigger.complete();
  }

  onChallengeAccept() {
    this.modalNewChallenge = false;
    this.hostService.newGame(this.userChallenger.id);
  }

  

}
