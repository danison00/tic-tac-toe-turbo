import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormLoginComponent } from 'src/app/pages/authentication/fragments/form-login/form-login.component';
import { VarsGlobalService } from 'src/app/utils/vars-global.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  constructor(private http: HttpClient, private route: Router, private global:  VarsGlobalService) {}
  onLogout() {
    this.http.get(this.global.baseUrl+'/auth/logout', {withCredentials: true, }).subscribe(() => {
      this.route.navigate([FormLoginComponent])
    });  
    
  }
  

}
