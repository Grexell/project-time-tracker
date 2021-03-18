import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { EmployeeComponent } from './employee/employee.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProfileComponent } from './profile/profile.component';
import {PortalSelectComponent} from "./portal-select/portal-select.component";

const routes: Routes = [
  {
    component: EmployeeComponent, path: 'home', canActivate: [AuthGuard], canActivateChild: [AuthGuard],
    data: { isAuthorized: true, isSelectedPortal: true },
    children: [
      {
        component: ProfileComponent, path: 'profile'
      },
      {
        component: DashboardComponent, path: ''
      }
    ]
  },
  {
    component: PortalSelectComponent, path: 'portal', canActivate: [AuthGuard], canActivateChild: [AuthGuard],
    data: { isAuthorized: true, isSelectedPortal: false }
  },
  {
    component: LoginComponent, path: '', canActivate: [AuthGuard], canActivateChild: [AuthGuard],
    data: { isAuthorized: false, isSelectedPortal: false }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
