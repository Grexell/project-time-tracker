import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-project-dialog',
  templateUrl: './project-dialog.component.html',
  styleUrls: ['./project-dialog.component.scss']
})
export class ProjectDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ProjectDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

  save() {
    this.dialogRef.close(this.data.project);
  }

  addCustomer(customer) {
    this.data.customers.splice(this.data.customers.indexOf(customer), 1);
    this.data.project.customers.push(customer);
  }

  removeCustomer(customer) {
    this.data.project.customers.splice(this.data.project.customers.indexOf(customer), 1);
    this.data.customers.push(customer);
  }

  addUser(user) {
    this.data.users.splice(this.data.users.indexOf(user), 1);
    this.data.project.team.push(user);
  }

  removeUser(user) {
    this.data.project.team.splice(this.data.project.team.indexOf(user), 1);
    this.data.users.push(user);
  }
}
