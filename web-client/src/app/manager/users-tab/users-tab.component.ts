import {Component, OnInit, TemplateRef} from '@angular/core';
import {ApiService} from '../../api.service';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {MatDialog} from "@angular/material/dialog";
import {FormControl} from "@angular/forms";
import {map, startWith} from "rxjs/operators";
import {Observable} from "rxjs";

@Component({
  selector: 'app-users-tab',
  templateUrl: './users-tab.component.html',
  styleUrls: ['./users-tab.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class UsersTabComponent implements OnInit {

  userTableColumns = ['name', 'email', 'position', 'salary'];
  userFilter = '';
  selectedUsers = [];
  users = [];
  filteredUsers = [];

  positions: any[];
  filteredPositions: Observable<any>;

  positionControl = new FormControl();
  positionFn = (position) => position && position.name? position.name : '';

  constructor(private api: ApiService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadUsers();
    this.filteredPositions = this.positionControl.valueChanges.pipe(
        startWith(''),
        map(value => typeof value === 'string' ? value : value.name),
        map(name => name ? this.positions.filter(pos => pos.name.includes(name)) : this.positions.slice())
    )
  }

  loadUsers() {
    this.api.loadPositions().subscribe(positions => {
      this.positions = positions;
      this.api.loadEmployees().subscribe(users => {
        this.users = users;
        this.users.forEach(user => {
          let position = this.positions.find(position => position.id === user.position);
          user.positionName = position ? position.name : '';
        });
        this.filterUsers();
      });
    });
  }

  filterUsers() {
    this.filteredUsers = this.users.filter(user => !this.userFilter || user.email.toLowerCase().includes(this.userFilter.toLowerCase()) ||
        (user.firstName + user.secondName).toLowerCase().includes(this.userFilter.toLowerCase()));
  }

  openPositionModal(user, modal: TemplateRef<any>) {
    this.dialog.open(modal, {
      data: { name: user.firstName, position: user.position },
      disableClose: true
    }).beforeClosed().subscribe(result => {
      result = typeof result === 'string' ? { name : result } : result;
      if (result && user.position !== result.id) {
        this.api.changePositions(user.id, result).subscribe(() => this.loadUsers());
      }
    });
  }
}
