import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../api.service";

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
  filteredReports = [];

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadReports();
  }

  private loadReports() {
    this.api.loadReports().subscribe(reports => this.reports = reports);
  }

  private filterReports() {
      // todo add filtering
    this.filteredReports = this.reports;
    // this.filteredReports = this.reports.filter();
  }
}
