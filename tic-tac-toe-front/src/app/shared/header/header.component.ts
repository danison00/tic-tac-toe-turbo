import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormLoginComponent } from 'src/app/pages/authentication/fragments/form-login/form-login.component';
import { environment } from 'src/environments/environment.dev';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  constructor(private http: HttpClient, private route: Router) {}
  onLogout() {
    this.http.get(environment.baseUrl+'/logout', {withCredentials: true, }).subscribe(() => {
      this.route.navigate([FormLoginComponent])
    });  
    
  }
  

}
