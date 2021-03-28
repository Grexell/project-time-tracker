import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { EmployeeComponent } from './employee/employee.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTabsModule} from '@angular/material/tabs';
import {MatMenuModule} from '@angular/material/menu';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProfileComponent } from './profile/profile.component';
import {HttpClientModule} from "@angular/common/http";
import { UserTabComponent } from './admin/user-tab/user-tab.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatTableModule} from "@angular/material/table";
import {MatSelectModule} from "@angular/material/select";
import {MatChipsModule} from "@angular/material/chips";
import { SettingsComponent } from './admin/settings/settings.component';
import {MatDividerModule} from '@angular/material/divider';
import {MatListModule} from '@angular/material/list';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import { ProjectTabComponent } from './manager/project-tab/project-tab.component';
import { PortalSelectComponent } from './portal-select/portal-select.component';
import { CustomerDialogComponent } from './manager/customer-dialog/customer-dialog.component';
import { ConfirmDialogComponent } from './common/confirm-dialog/confirm-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    EmployeeComponent,
    DashboardComponent,
    ProfileComponent,
    UserTabComponent,
    SettingsComponent,
    ProjectTabComponent,
    PortalSelectComponent,
    CustomerDialogComponent,
    ConfirmDialogComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        AppRoutingModule,
        MatDialogModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        MatFormFieldModule,
        MatToolbarModule,
        MatMenuModule,
        MatTabsModule,
        BrowserAnimationsModule,
        MatGridListModule,
        MatTableModule,
        MatSelectModule,
        MatChipsModule,
        MatDividerModule,
        MatListModule,
        MatDatepickerModule,
        MatNativeDateModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
