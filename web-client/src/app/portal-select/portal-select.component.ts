import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-portal-select',
    templateUrl: './portal-select.component.html',
    styleUrls: ['./portal-select.component.scss']
})
export class PortalSelectComponent implements OnInit {
    userType: string;

    constructor(private auth: AuthService, private router: Router) {
        this.userType = auth.getUserRole();
    }

    ngOnInit(): void {
    }

    selectPortal(type: string) {
        this.auth.selectPortal(type)
            .subscribe(() => {
                this.router.navigate(['/home'])
            });
    }
}
