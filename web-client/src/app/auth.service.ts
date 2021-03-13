import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import {ApiService} from "./api.service";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private clientSecret = 'asdf';

  private isLogined = false;
  private token: any;
  constructor(private api: ApiService) {}

  isAuthorized() {
    return this.isLogined;
  }

  login(username, password) {
    return this.api.login({
      username, password, clientSecret: this.clientSecret
    }).pipe(
        map(value => {
          this.token = value;
          this.isLogined = true;
        })
    )
  }

  logout() {
    this.isLogined = false;
    return of(true);
  }
}
