import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {CustomerDialogComponent} from '../customer-dialog/customer-dialog.component';
import {ConfirmDialogComponent} from '../../common/confirm-dialog/confirm-dialog.component';
import {ProjectDialogComponent} from '../project-dialog/project-dialog.component';
import {ApiService} from "../../api.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {formatISO} from "date-fns";
import {Observable} from "rxjs";

@Component({
  selector: 'app-project-tab',
  templateUrl: './project-tab.component.html',
  styleUrls: ['./project-tab.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ProjectTabComponent implements OnInit {
  customers = [];
  selectedCustomers = [];

  projectTableColumns = ['project', 'startDate', 'endDate', 'budget', 'actions'];

  projects = [];
  projectFilter: string;
  filteredProjects = [];
  selectedProjects = [];
  users = [];
  positions: any[];

  constructor(private api: ApiService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadProjects();
  }

  unselectCustomer(customer) {
    this.selectedCustomers = this.selectedCustomers.filter( cust => customer !== cust);
  }

  private loadProjects() {
    this.loadPositions();
    this.api.loadCustomers().subscribe(customers => this.customers = customers);
    this.api.loadManagedProjects().subscribe(projects => {
      this.projects = projects;
      this.filterProjects();
    });
  }

  private loadPositions() {
    this.api.loadPositions().subscribe(positions => {
      this.positions = positions;
      this.api.loadEmployees().subscribe(users => {
        this.users = users;
        this.users.forEach(user => {
          let position = this.positions.find(position => position.id === user.position);
          user.positionName = position ? position.name : '';
        });
        // this.filterUsers();
      });
    });
  }

  openNewCustomerModal() {
    this.dialog.open(CustomerDialogComponent, {
      data: {
        customer: {
          name: '',
          contact: ''
        }
      },
      disableClose: true
    }).beforeClosed().subscribe(result => {
      if (result) {
        this.api.createCustomer(result).subscribe( customer => this.customers.push(customer));
      }
    });
  }

  openEditCustomerModal(customer) {
    this.dialog.open(CustomerDialogComponent, {
      data: { customer: Object.assign({}, customer) },
      disableClose: true
    }).beforeClosed().subscribe(result => {
      if (result) {
        this.api.updateCustomer(result).subscribe( response => Object.assign(customer, response));
      }
    });
  }

  openRemoveCustomerModal(customer) {
    this.dialog.open(ConfirmDialogComponent, {
      data: {
        action: 'Delete',
        subject: customer.name
      },
      disableClose: true
    }).beforeClosed().subscribe(result => {
      if (result) {
        this.api.deleteCustomer(customer).subscribe( () => {
          this.customers.splice(this.customers.indexOf(customer), 1);
          if (this.selectedCustomers.includes(customer)) {
            this.selectedCustomers.splice(this.customers.indexOf(customer), 1);
          }
        });
      }
    });
  }

  openNewProjectModal() {
    this.openProjectModal({
      customers: [],
      team: []
    });
  }

  openEditProjectModal(project) {
    project = { ...project};
    project.customers = this.customers.filter(customer => project.customers
        .map(cust => cust.customerId)
        .includes(customer.id));
    project.team = project.team.map(user => {
      let find = this.users.find(u => user.userId === u.id);
      return {...find, budget: user.budget, monthly: !!user.month, teamId: user.id};
    });
    this.openProjectModal(project);
  }

  private openProjectModal(project) {
    this.dialog.open(ProjectDialogComponent, {
      data: {
        project,
        users: this.users.filter(cust => !project.team.map(user => user.id).includes(cust.id)),
        customers: this.customers.filter(cust => !project.customers.includes(cust))
      },
      disableClose: true,
      width: '90%'
    }).beforeClosed().subscribe(result => {
      if (result) {
        result.customers = result.customers.map(customer => {
          return {customerId: customer.id}
        })
        result.team = result.team.map(user => {
          let teamMember: any = {userId: user.id, salary: user.budget, monthly: user.monthly};
          if (user.teamId) {
            teamMember.id = user.teamId;
          }
          return teamMember
        })
        if (!result.id) {
          this.api.createProject(result).subscribe(project => {
            this.projects.push(project);
            this.filterProjects();
          });
        } else {
          this.api.updateProject(result).subscribe(() => this.loadProjects());
        }
      }
    });
  }

  attachProject(project) {
    this.api.attachProject(project).subscribe(() => project.attached = true);
  }

  openEndProjectModal(project, content) {
    this.dialog.open(content, {
      data: { subject: project.name, startDate: new Date() },
      disableClose: true
    }).beforeClosed().subscribe(result => {
      if (result) {
        let date = formatISO(new Date(result), { representation: 'date' });
        this.api.endProject(project, date).subscribe(() => this.loadProjects());
      }
    });
  }

  filterProjects() {
    this.filteredProjects = this.projects
        .filter(project => !this.projectFilter || project.name.includes(this.projectFilter))
        .filter(project => this.selectedCustomers.every(customer => project.customers
            .map(cust => cust.customerId)
            .includes(customer.id)));
  }
}
