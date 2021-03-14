import {Component, OnInit} from '@angular/core';
import {of} from "rxjs";
import {animate, state, style, transition, trigger} from "@angular/animations";

@Component({
    selector: 'app-user-tab',
    templateUrl: './user-tab.component.html',
    styleUrls: ['./user-tab.component.scss'],
    animations: [
        trigger('detailExpand', [
            state('collapsed', style({height: '0px', minHeight: '0'})),
            state('expanded', style({height: '*'})),
            transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ],
})
export class UserTabComponent implements OnInit {

    users = [];
    columnsToDisplay: string[] = ['User', 'Email', 'Role'];
    expandedElement: any;

    sourceUser;
    editUser = {
        firstName: '',
        secondName: '',
        email: '',
        password: '',
        role: '',
    };
    editAction = 'new';

    constructor() {
    }

    ngOnInit(): void {
        this.loadUsers();
    }

    loadUsers() {
        of([]).subscribe(users => this.users = users);
    }

    saveUser() {
        if (this.editAction === 'new') {
            of(this.editUser)
                .subscribe((user) => {
                    this.users = [...this.users, user];
                    this.clearForm();
                });
        } else {
            of(this.editUser)
                .subscribe((user) => {
                    this.sourceUser.firstName = user.firstName;
                    this.sourceUser.secondName = user.secondName;
                    this.sourceUser.email = user.email;
                    this.sourceUser.password = user.password;
                    this.sourceUser.role = user.role;
                    this.clearForm();
                });
        }
    }

    clearForm() {
        this.editAction = 'new';
        this.editUser = {
            firstName: '',
            secondName: '',
            email: '',
            password: '',
            role: null
        };
    }

    edit(element) {
        this.sourceUser = element;
        this.editAction = 'edit';
        this.editUser = Object.assign({}, element);
    }
}
