import { Component, OnInit } from '@angular/core';
import {ApiService} from '../../api.service';

@Component({
  selector: 'app-users-tab',
  templateUrl: './users-tab.component.html',
  styleUrls: ['./users-tab.component.scss']
})
export class UsersTabComponent implements OnInit {

  userTableColumns = ['name', 'email', 'position', 'salary'];
  userFilter = '';
  selectedUsers = [];
  users = [];
  filteredUsers = [];

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.api.loadEmployees().subscribe(users => {
      this.users = users;
      this.filterUsers();
    });
  }

  filterUsers() {
    this.filteredUsers = this.users.filter(user => !this.userFilter || user.email.toLowerCase().includes(this.userFilter.toLowerCase()) ||
        (user.firstName + user.secondName).toLowerCase().includes(this.userFilter.toLowerCase()));
  }
}
