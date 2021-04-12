import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-vacations-tab',
  templateUrl: './vacations-tab.component.html',
  styleUrls: ['./vacations-tab.component.scss']
})
export class VacationsTabComponent implements OnInit {
  vacationsByUser = [];
  vacationsByProject = [];

  constructor() { }

  ngOnInit(): void {
  }

}
