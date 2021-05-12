import {Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from "../../api.service";
import {MatCalendar} from "@angular/material/datepicker";
import {formatISO, isSameDay, isSameMonth, isSameWeek, parseISO} from 'date-fns';
import {Observable} from "rxjs";

class Report {
  constructor(public id?: number, public taskId?: number, public projectId?: number, public text?: string,
              public hours?: number, public minutes?: number) {
  }
}

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {
  @ViewChild('calendar') calendar: MatCalendar<Date>;

  reportTableColumns = ['project', 'task', 'date', 'time', 'edit'];
  selectedPeriod = 'day';

  report = new Report();
  projects = [];
  tasks = [];

  reports = [];
  filteredReports = [];
  selectedDate = new Date();

  dates = new Set();
  transfers = new Set();

  projMap = new Map();
  taskMap = new Map();

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadReports();
    this.loadHolidays();
  }

  private loadReports() {
    this.api.loadReports().subscribe(reports => {
      this.projMap = new Map<any, any>();
      this.taskMap = new Map<any, any>();
      this.reports = reports;

      this.loadProjects();
      this.filterReports();
    });
  }

  dateClass() {
    return (date: Date) => {
      const dateStr = formatISO(date, { representation: 'date' });
      if (this.dates && this.dates.has(dateStr)) {
        return 'start-date';
      } else if (this.transfers && this.transfers.has(dateStr)) {
        return 'shift-date';
      }
      return '';
    };
  }

  private splitDates(holidays) {
    this.dates.clear();
    this.transfers.clear();
    holidays.forEach(holiday => {
      this.dates.add(holiday.date);
      if (holiday.transferDate) {
        this.transfers.add(holiday.transferDate);
      }
    })
    this.calendar.updateTodaysDate();
  }

  loadHolidays() {
    this.api.loadCurrentHolidays().subscribe(holidays => this.splitDates(holidays))
  }

  private loadProjects() {
    this.api.loadProjects().subscribe(projects => {
      this.projects = projects;
      this.projects.forEach(project => {
        this.projMap.set(project.id, project);
        this.loadTasks(project.id);
      });
    });
  }

  loadTasks(project) {
    this.api.loadTasks(project).subscribe(tasks => {
      this.tasks = tasks;
      this.tasks.forEach(task => {
        task.project = project;
        this.taskMap.set(task.id, task);
      });
    });
  }

  saveReport() {
    const report: any = { ...this.report, date: formatISO(this.selectedDate, { representation: 'date' }) };
    report.time = report.hours * 60 + report.minutes;
    this.report = new Report();
    let observable: Observable<any[]>;
    if (!report.id) {
      observable = this.api.createReport(report);
    } else {
      observable = this.api.updateReport(report);
    }
    observable.subscribe(() => {
      this.loadReports();
    });
  }

  filterReports() {
    let date = parseISO(formatISO(this.selectedDate, { representation: 'date' }));
    if (this.selectedPeriod === 'day') {
      this.filteredReports = this.reports.filter(report => isSameDay(parseISO(report.date), date));
    } else if (this.selectedPeriod === 'week') {
      this.filteredReports = this.reports.filter(report => isSameWeek(parseISO(report.date), date));
    } else if (this.selectedPeriod === 'month') {
      this.filteredReports = this.reports.filter(report => isSameMonth(parseISO(report.date), date));
    } else {
      this.filteredReports = this.reports;
    }
  }

  edit(report) {
    this.report = new Report(report.id, report.taskId,
        this.taskMap.get(report.taskId).project, report.text, this.floor(report.time / 60), report.time % 60);
    this.selectedDate = parseISO(report.date);
  }

  floor(x) {
    return Math.floor(x);
  }
}
