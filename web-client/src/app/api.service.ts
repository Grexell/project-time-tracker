import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Overlay, OverlayRef} from '@angular/cdk/overlay';
import {ComponentPortal} from '@angular/cdk/portal';
import {MatSpinner} from '@angular/material/progress-spinner';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private host = 'http://localhost:8080/';
  private authUrl = this.host + 'auth/';
  private calendarUrl = this.host + 'calendar';
  private customerUrl = this.host + 'customer';
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

  loadCustomers(): Observable<any[]> {
    return this.http.get<any[]>(this.customerUrl, this.getAuthHeaders());
  }

  createCustomer(customer) {
    return this.http.post<any>(this.customerUrl, customer, this.getAuthHeaders());
  }

  updateCustomer(customer) {
    return this.http.put<any>(`${this.customerUrl}/${customer.id}`, customer, this.getAuthHeaders());
  }

  deleteCustomer(customer) {
    return this.http.delete<any>(`${this.customerUrl}/${customer.id}`, this.getAuthHeaders());
  }

  loadCalendars(): Observable<any[]> {
    return this.http.get<any[]>(this.calendarUrl, this.getAuthHeaders());
  }

  addCalendar(calendar): Observable<any> {
    return this.http.post<any>(this.calendarUrl, calendar, this.getAuthHeaders());
  }

  deleteCalendar(calendar): Observable<any> {
    return this.http.delete<any>(`${this.calendarUrl}/${calendar.id}`, this.getAuthHeaders());
  }

  loadHolidays(calendar) {
    return this.http.get<any[]>(`${this.calendarUrl}/${calendar.id}/holiday`, this.getAuthHeaders());
  }

  createHoliday(calendar, holiday) {
    return this.http.post<any>(`${this.calendarUrl}/${calendar.id}/holiday`, holiday, this.getAuthHeaders());
  }

  deleteHoliday(calendar, holiday) {
    return this.http.delete<any>(`${this.calendarUrl}/${calendar.id}/holiday/${holiday.id}`, this.getAuthHeaders());
  }

  loadUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.authUrl + 'user', this.getAuthHeaders());
  }

  createUser(user) {
    return this.http.post<any>(this.authUrl + 'user', user, this.getAuthHeaders());
  }

  updateUser(user) {
    return this.http.put<any>(this.authUrl + 'user', user, this.getAuthHeaders());
  }

  private getAuthHeaders() {
    return {headers: {'Authorization': this.token}};
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
  private showSpinner() {
    this.spinnerRef.attach(new ComponentPortal(MatSpinner));
  }

  private stopSpinner() {
    this.spinnerRef.detach();
  }
}
