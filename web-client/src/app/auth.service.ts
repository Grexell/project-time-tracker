import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import {ApiService} from "./api.service";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private clientSecret = 'asdf';

  private isLogined = true;
  private portalType: string = 'user';
  private token: any;
  constructor(private api: ApiService) {}

  isAuthorized() {
    return this.isLogined;
  }

  isSelectedPortal() {
    return !!this.portalType;
  }

  getPortalType() {
    return this.portalType;
  }

  selectPortal(portalType: string) {
    return of(portalType).pipe(
        map(value => {
          this.portalType = value;
        })
    );
  }

  login(username, password) {
    return of({
    // return this.api.login({
    //   username, password, clientSecret: this.clientSecret
    }).pipe(
        map(value => {
          this.token = value;
          this.isLogined = true;
        })
    );
  }

  logout() {
    this.isLogined = false;
    this.portalType = null;
    return of(true);
  }
}
