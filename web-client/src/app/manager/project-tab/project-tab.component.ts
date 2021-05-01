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

  users = [
    {
      id: 1,
      firstName: 'user',
      secondName: '1',
      position: 'Junior Software Engineer',
      salary: 800
    },
    {
      id: 2,
      firstName: 'user',
      secondName: '2',
      position: 'Senior Software Engineer',
      salary: 2800
    },
    {
      id: 3,
      firstName: 'user',
      secondName: '3',
      position: 'Middle Software Engineer',
      salary: 1500
    }
  ];

  constructor(private api: ApiService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadProjects();
  }

  unselectCustomer(customer) {
    this.selectedCustomers = this.selectedCustomers.filter( cust => customer !== cust);
  }

  private loadProjects() {
    this.api.loadCustomers().subscribe(customers => this.customers = customers);
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
        console.log(result);
        this.projects.push(result);
      }
    });
  }

  filterProjects() {
    this.filteredProjects = this.projects
        .filter(project => project.name.includes(this.projectFilter))
        .filter(project => this.selectedCustomers.every(customer => project.customers
            .map(cust => cust.id)
            .includes(customer.id)));
  }
}
