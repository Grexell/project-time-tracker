import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivateChild, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private auth: AuthService, private router: Router) {}

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    let isAuthorized = false;
    let isSelectedPortal = false;
    while (childRoute) {
      isAuthorized = isAuthorized || childRoute.data['isAuthorized'];
      isSelectedPortal = isSelectedPortal || childRoute.data['isSelectedPortal'];
      childRoute = childRoute.parent;
    }
    if (this.auth.isAuthorized() === isAuthorized) {
      if (this.auth.isSelectedPortal() === isSelectedPortal) return true;
      if (isSelectedPortal) this.router.navigate(['/portal'])
      else this.router.navigate(['/home']);
    } else if (!this.auth.isAuthorized()) return this.router.navigate(['/']);
    if (this.auth.isSelectedPortal() === isSelectedPortal) return this.router.navigate(['/home']);
    if (isSelectedPortal) this.router.navigate(['/portal'])
    else this.router.navigate(['/home']);
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.auth.isAuthorized() === route.data['isAuthorized']) {
      if (this.auth.isSelectedPortal() === route.data['isSelectedPortal']) return true;
      if (route.data['isSelectedPortal']) this.router.navigate(['/portal'])
      else this.router.navigate(['/home']);
    } else if (!this.auth.isAuthorized()) return this.router.navigate(['/']);
    if (this.auth.isSelectedPortal() === route.data['isSelectedPortal']) return this.router.navigate(['/home']);
    if (route.data['isSelectedPortal']) this.router.navigate(['/portal'])
    else this.router.navigate(['/home']);
  }
}
