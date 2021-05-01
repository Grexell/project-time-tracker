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
import {HttpClientModule} from '@angular/common/http';
import { UserTabComponent } from './admin/user-tab/user-tab.component';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatTableModule} from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import {MatChipsModule} from '@angular/material/chips';
import { SettingsComponent } from './admin/settings/settings.component';
import {MatDividerModule} from '@angular/material/divider';
import {MatListModule} from '@angular/material/list';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { ProjectTabComponent } from './manager/project-tab/project-tab.component';
import { PortalSelectComponent } from './portal-select/portal-select.component';
import { CustomerDialogComponent } from './manager/customer-dialog/customer-dialog.component';
import { ConfirmDialogComponent } from './common/confirm-dialog/confirm-dialog.component';
import { ProjectDialogComponent } from './manager/project-dialog/project-dialog.component';
import { ProjectsComponent } from './user/projects/projects.component';
import { VacationsComponent } from './user/vacations/vacations.component';
import { ReportComponent } from './user/report/report.component';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { VacationsTabComponent } from './manager/vacations-tab/vacations-tab.component';
import {UsersTabComponent} from './manager/users-tab/users-tab.component';
import {MatExpansionModule} from "@angular/material/expansion";
import {MatCheckboxModule} from "@angular/material/checkbox";

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
    ConfirmDialogComponent,
    ProjectDialogComponent,
      ProjectsComponent,
      VacationsComponent,
      ReportComponent,
      VacationsTabComponent,
      UsersTabComponent
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
        MatButtonToggleModule,
        MatExpansionModule,
        MatCheckboxModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
