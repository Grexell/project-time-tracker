import {Component, OnInit, ViewChild} from '@angular/core';
import {of} from "rxjs";
import {MatCalendar} from "@angular/material/datepicker";

class Holiday {
  constructor(public id?: number, public startDate?: Date, public transferDate?: Date) {
  }
}

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
    calendars = [
      {id: 1, locale: 'ru_by'},
      {id: 2, locale: 'ru_by'},
      {id: 3, locale: 'ru_by'},
      {id: 4, locale: 'ru_by'}
      ];
  selectedDate: any;
  selectedCalendar: any;
  holidays = [
      new Holiday(1, new Date(), new Date('2021-03-17'))
  ];
  dates = new Set();
  transfers = new Set();
  holiday = new Holiday();

  @ViewChild('calendar') calendar: MatCalendar<Date>;

  constructor() { }

  ngOnInit(): void {
  }

  addCalendar(locale) {
    of({ id: 1, locale })
        .subscribe(calendar => this.calendars.push(calendar));
  }

  deleteCalendar(calendar) {
    of(calendar)
        .subscribe(() => this.calendars.splice(this.calendars.indexOf(calendar), 1));
    // this.calendars.push({ id: 1, locale });
  }

  onSelect($event: any) {
    if (!this.holiday.startDate) {
      this.holiday.startDate = $event;
    } else if (!this.holiday.transferDate) {
      this.holiday.transferDate = $event;
    } else {
      this.holiday.startDate = $event;
      this.holiday.transferDate = null;
    }
    this.calendar.updateTodaysDate();
  }

  dateClass() {
    return (date: Date) => {
      if (this.holiday && this.holiday.startDate && date.toDateString() == this.holiday.startDate.toDateString()) {
        return 'selected-start-date';
      } else if (this.holiday && this.holiday.transferDate && date.toDateString() == this.holiday.transferDate.toDateString()) {
        return 'selected-shift-date';
      } else if (this.dates && this.dates.has(date.toDateString())) {
        return 'start-date';
      } else if (this.transfers && this.transfers.has(date.toDateString())) {
        return 'shift-date';
      }
      return '';
    };
  }

  loadHolidays() {
    of([
      new Holiday(1, new Date(), new Date('2021-03-17'))
    ])
        .subscribe(holidays => {
          this.holidays = holidays;
          this.splitDates();
        })
  }

  private splitDates() {
    this.holidays.forEach(holiday => {
      this.dates.add(holiday.startDate.toDateString());
      if (holiday.transferDate) {
        this.transfers.add(holiday.transferDate.toDateString());
      }
    })
    this.calendar.updateTodaysDate();
  }

  selectCalendar($event) {
    this.selectedCalendar = $event.option.value;
    this.loadHolidays();
  }

  deleteHoliday(holiday) {
    of(holiday)
        .subscribe(() => {
          this.holidays.splice(this.holidays.indexOf(holiday), 1);
          this.splitDates();
        });
  }

  addHoliday() {
    of(this.holiday)
        .subscribe(holiday => {
          this.holidays.push(holiday);
          this.holiday = new Holiday();
          this.splitDates();
        })
  }
}
