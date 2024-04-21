import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class VarsGlobalService {

  baseUrl = 'http://ec2-3-12-196-39.us-east-2.compute.amazonaws.com:8080/api/public';
  domain = 'ec2-3-12-196-39.us-east-2.compute.amazonaws.com:8080';  
  // baseUrl = 'http://localhost:8080/api/public';
  // domain = 'localhost:8080';

  constructor() { }

  public getIdUser(){

    

  }
}
