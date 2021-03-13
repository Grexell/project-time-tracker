import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private host = 'http://localhost:8080/';
  private authUrl = this.host + 'auth/';

  constructor(private http: HttpClient) { }

  login(tokenRequest) {
    return this.http.post(this.authUrl + 'token', tokenRequest);
  }
}
