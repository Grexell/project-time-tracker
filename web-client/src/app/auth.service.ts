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
  private portalType: string;
  private token: any;
  private user: any;
  constructor(private api: ApiService) {
    let token = localStorage.getItem('token');
    if (token) {
      this.setToken(JSON.parse(token));
      let portal = localStorage.getItem('portal');
      if (portal) {
        this.portalType = portal;
      }
    }
  }

  isAuthorized() {
    return this.isLogined;
  }

  isSelectedPortal() {
    return !!this.portalType;
  }

  getPortalType() {
    return this.portalType;
  }

  getUserRole() {
    return this.user.role;
  }

  selectPortal(portalType: string) {
    return of(portalType).pipe(
        map(value => {
          this.portalType = value;
          localStorage.setItem('portal', this.portalType);
        })
    );
  }

  login(username, password) {
    // return of({
    return this.api.login({
      username, password, clientSecret: this.clientSecret
    }).pipe(
        map(value => {
          localStorage.setItem('token', JSON.stringify(value));
          this.setToken(value);
        })
    );
  }

  private setToken(value) {
    this.token = value;
    this.user = JSON.parse(atob(value.access_token.split('.')[1]));
    this.isLogined = true;
    this.api.token = value.access_token;
  }

  logout() {
    this.isLogined = false;
    this.portalType = null;
    this.user = null;
    this.token = null;
    this.api.token = null;
    localStorage.removeItem('token');
    localStorage.removeItem('portal');
    return of(true);
  }
}
