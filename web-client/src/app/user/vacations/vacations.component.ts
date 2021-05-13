import {Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from "../../api.service";
import {MatCalendar} from "@angular/material/datepicker";
import {addDays, differenceInBusinessDays, formatISO, isWeekend, isWithinInterval, parseISO} from "date-fns";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-vacations',
  templateUrl: './vacations.component.html',
  styleUrls: ['./vacations.component.scss']
})
export class VacationsComponent implements OnInit {
  @ViewChild('calendar') calendar: MatCalendar<Date>;

  vacationColumns = ['startDate', 'endDate', 'status'];
  teamVacationColumns = ['user', 'startDate', 'endDate', 'status'];
  vacations: any[];
  teamVacations: any[];

  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl()
  });
  selectedDate = new Date();

  dates = new Set<string>();
  transfers = new Set<string>();

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadVacations();
    this.loadHolidays();
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

  private loadVacations() {
    this.api.loadVacations().subscribe(vacations => this.vacations = vacations);
    this.api.loadTeamVacations().subscribe(vacations => this.teamVacations = vacations);
  }

  requestVacation(startDate, endDate) {
    startDate = parseISO(formatISO(startDate, { representation: 'date' }));
    endDate = parseISO(formatISO(endDate, { representation: 'date' }));

    const start = formatISO(startDate, { representation: 'date' })
    const interval = { start: startDate, end: endDate };
    formatISO(endDate, { representation: 'date' })
    let length = differenceInBusinessDays(addDays(endDate, 1), startDate);
    this.dates.forEach(date => {
      let iso = parseISO(date);
      if (isWithinInterval(iso, interval) && !isWeekend(iso)) {
        length--;
      }
    });
    this.transfers.forEach(date => {
      let iso = parseISO(date);
      if (isWithinInterval(iso, interval) && isWeekend(iso)) {
        length++;
      }
    })
    this.api.requestVacation({startDate: start, length}).subscribe(() => {
      this.loadVacations();
    })
  }
}
