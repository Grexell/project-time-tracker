import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {CustomerDialogComponent} from "../customer-dialog/customer-dialog.component";
import {ConfirmDialogComponent} from "../../common/confirm-dialog/confirm-dialog.component";

@Component({
  selector: 'app-project-tab',
  templateUrl: './project-tab.component.html',
  styleUrls: ['./project-tab.component.scss']
})
export class ProjectTabComponent implements OnInit {
  customers = [];
  selectedCustomers = [];

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadProjects();
  }

  unselectCustomer(customer) {
    this.selectedCustomers = this.selectedCustomers.filter( cust => customer !== cust);
  }

  private loadProjects() {
    this.customers = [
      {id: 1, name: 'customer1'},
      {id: 1, name: 'customer2'},
      {id: 1, name: 'customer3'},
      {id: 1, name: 'customer4'},
      {id: 1, name: 'customer5'}
    ];
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
        this.customers.push(result);
      }
    });
  }

  openEditCustomerModal(customer) {
    this.dialog.open(CustomerDialogComponent, {
      data: { customer: Object.assign({}, customer) },
      disableClose: true
    }).beforeClosed().subscribe(result => {
      if (result) {
        Object.assign(customer, result);
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
        this.customers.splice(this.customers.indexOf(customer), 1);
        if (this.selectedCustomers.includes(customer)) {
          this.selectedCustomers.splice(this.customers.indexOf(customer), 1);
        }
      }
    });
  }
}
