import { Component, OnInit } from '@angular/core';

class Report {
  public id: number;
  constructor(public taskId?: number, public projectId?: number, public text?: string) {
  }
}

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {
  reportTableColumns = ['project', 'budget', 'startDate', 'endDate', 'actions'];
  selectedPeriod = 'day';

  report = new Report();
  projects = [];
  tasks = [];

  reports = [];

  constructor() { }

  ngOnInit(): void {
  }

}
