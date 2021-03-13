import { Component, ViewChild, TemplateRef, AfterViewInit, OnDestroy } from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements AfterViewInit, OnDestroy {

  @ViewChild('loginDialog')
  private loginDialog: TemplateRef<any>;
  private form: MatDialogRef<any, any>;

  username: string = '';
  password: string = '';
  showPassword = false;

  constructor(private auth: AuthService, private router: Router, private dialog: MatDialog) { }

  ngAfterViewInit(): void {
    this.form = this.dialog.open(this.loginDialog, { disableClose: true });
  }

  ngOnDestroy(): void {
    this.form.close();
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  login() {
    this.auth.login(this.username, this.password).subscribe(() => {
      this.router.navigate(['home']);
    });
  }
}
