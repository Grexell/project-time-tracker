import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {CustomerDialogComponent} from '../customer-dialog/customer-dialog.component';
import {ConfirmDialogComponent} from '../../common/confirm-dialog/confirm-dialog.component';
import {ProjectDialogComponent} from '../project-dialog/project-dialog.component';
import {ApiService} from "../../api.service";

@Component({
  selector: 'app-project-tab',
  templateUrl: './project-tab.component.html',
  styleUrls: ['./project-tab.component.scss']
})
export class ProjectTabComponent implements OnInit {
  customers = [];
  selectedCustomers = [];

  projectTableColumns = ['project', 'budget', 'startDate', 'endDate', 'actions'];

  projects = [];
  projectFilter: string;
  filteredProjects = [];
  selectedProjects = [];
  users = [];

  constructor(private api: ApiService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadProjects();
  }

  unselectCustomer(customer) {
    this.selectedCustomers = this.selectedCustomers.filter( cust => customer !== cust);
  }

  private loadProjects() {
    this.api.loadCustomers().subscribe(customers => this.customers = customers);
    this.api.loadEmployees().subscribe(users => this.users = users);
    this.api.loadManagedProjects().subscribe(projects => {
      this.projects = projects;
      this.filterProjects();
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
    this.dialog.open(ProjectDialogComponent, {
      data: {
        project: {
          customers: [],
          team: []
        },
        users: [...this.users],
        customers: [...this.customers]
      },
      disableClose: true,
      width: '90%'
    }).beforeClosed().subscribe(result => {
      if (result) {
        result.customers = result.customers.map(customer => {
          return {customerId: customer.id}
        })
        result.team = result.team.map(user => {
          return {userId: user.id, salary: user.budget, monthly: user.monthly}
        })
        this.api.createProject(result).subscribe(project => {
          this.projects.push(project);
          this.filterProjects();
        })
      }
    });
  }

  filterProjects() {
    this.filteredProjects = this.projects
        .filter(project => !this.projectFilter || project.name.includes(this.projectFilter))
        .filter(project => this.selectedCustomers.every(customer => project.customers
            .map(cust => cust.id)
            .includes(customer.id)));
  }
}
