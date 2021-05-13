import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../api.service";

@Component({
  selector: 'app-vacations-tab',
  templateUrl: './vacations-tab.component.html',
  styleUrls: ['./vacations-tab.component.scss']
})
export class VacationsTabComponent implements OnInit {
  vacationsByUser = [];
  vacationsByProject = [];
  vacationByUserColumns = ['user', 'startDate', 'endDate', 'status', 'actions'];

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadVacations();
  }

  private loadVacations() {
    this.api.loadManagedVacations().subscribe(vacations => this.vacationsByUser = vacations);
  }

  accept(vacation) {
    this.api.acceptVacation(vacation.id).subscribe(() => this.loadVacations());
  }

  reject(vacation) {
    this.api.rejectVacation(vacation.id).subscribe(() => this.loadVacations());
  }
}
