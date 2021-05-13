import {Component, OnInit} from '@angular/core';
import {of} from "rxjs";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ApiService} from "../../api.service";

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

    roleComparator = (r1, r2) => r1.name.toLowerCase() === r2.name.toLowerCase();
    calendarComparator = (c1, c2) => c1.id === c2.id;
    roles = [
        { id: 1, name: 'admin'},
        { id: 2, name: 'manager'},
        { id: 3, name: 'user'}
    ];
    calendars: any[];

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
        calendar: null
    };
    editAction = 'new';

    constructor(private api: ApiService) {
    }

    ngOnInit(): void {
        this.loadUsers();
        this.loadCalendars();
    }

    loadUsers() {
        this.api.loadUsers().subscribe(users => this.users = users);
    }

    loadCalendars() {
        this.api.loadCalendars().subscribe(calendars => this.calendars = calendars);
    }

    saveUser() {
        if (this.editAction === 'new') {
            this.api.createUser(this.editUser)
                .subscribe((user) => {
                    this.users = [...this.users, user];
                    this.clearForm();
                });
        } else {
            this.api.updateUser(this.editUser)
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

    deleteUser(user) {
        this.api.deleteUser(user).subscribe(() => this.loadUsers());
    }

    clearForm() {
        this.editAction = 'new';
        this.editUser = {
            firstName: '',
            secondName: '',
            email: '',
            password: '',
            role: null,
            calendar: null
        };
    }

    edit(element) {
        this.sourceUser = element;
        this.editAction = 'edit';
        this.editUser = Object.assign({}, element);
    }
}
