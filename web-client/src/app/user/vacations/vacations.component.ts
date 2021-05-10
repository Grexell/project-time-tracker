import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../api.service";

@Component({
  selector: 'app-vacations',
  templateUrl: './vacations.component.html',
  styleUrls: ['./vacations.component.scss']
})
export class VacationsComponent implements OnInit {
  vacationColumns = [];
  teamVacationColumns: any[];
  vacations: any[];
  teamVacations: any[];

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadVacations();
  }

  private loadVacations() {
    this.api.loadVacations().subscribe(vacations => this.vacations = vacations);
    this.api.loadTeamVacations().subscribe(vacations => this.teamVacations = vacations);
  }

}
