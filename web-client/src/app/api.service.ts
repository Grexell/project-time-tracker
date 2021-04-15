import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Overlay, OverlayRef} from '@angular/cdk/overlay';
import {ComponentPortal} from '@angular/cdk/portal';
import {MatSpinner} from '@angular/material/progress-spinner';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private host = 'http://localhost:8080/';
  private authUrl = this.host + 'auth/';
  private spinnerRef: OverlayRef = this.cdkSpinnerCreate();
  private _token: string;

  constructor(private http: HttpClient, private overlay: Overlay) { }

  get token(): string {
    return this._token;
  }

  set token(value: string) {
    this._token = value;
  }

  login(tokenRequest) {
    return this.http.post(this.authUrl + 'token', tokenRequest);
  }

  private cdkSpinnerCreate() {
    return this.overlay.create({
      hasBackdrop: true,
      backdropClass: 'dark-backdrop',
      positionStrategy: this.overlay.position()
          .global()
          .centerHorizontally()
          .centerVertically()
    });
  }
  //TODO: add stopSpinner inside finalize<T>(callback: () => void): MonoTypeOperatorFunction<T> to all http calls
  showSpinner() {
    this.spinnerRef.attach(new ComponentPortal(MatSpinner));
  }
  stopSpinner() {
    this.spinnerRef.detach();
  }
}
