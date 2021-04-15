import { Component, OnInit } from '@angular/core';

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

  constructor() { }

  ngOnInit(): void {
  }

}
