import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivateChild, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { pipe } from 'rxjs';
import { map } from 'rxjs/operators';
import { Route } from '@angular/compiler/src/core';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private auth: AuthService, private router: Router) {}

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    let isAuthorized = false;
    while (childRoute) {
      isAuthorized = isAuthorized || childRoute.data['isAuthorized'];
      childRoute = childRoute.parent;
    }
    if (this.auth.isAuthorized() === isAuthorized) return true;
    else if (!this.auth.isAuthorized()) return this.router.navigate(['/']);
    return this.router.navigate(['/home']);
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.auth.isAuthorized() === route.data['isAuthorized']) return true;
    else if (!this.auth.isAuthorized()) return this.router.navigate(['/']);
    return this.router.navigate(['/home']);
  }
}
