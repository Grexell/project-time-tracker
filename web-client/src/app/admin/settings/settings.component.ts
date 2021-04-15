import {Component, OnInit, ViewChild} from '@angular/core';
import {of} from "rxjs";
import {MatCalendar} from "@angular/material/datepicker";
import {ApiService} from "../../api.service";

class Holiday {
  constructor(public id?: number, public date?: string, public transferDate?: string) {
  }
}

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  calendars: any[];
  selectedDate: any;
  selectedCalendar: any;
  holidays: any[];
      // new Holiday(1, new Date(), new Date('2021-03-17'))
  // ];
  dates = new Set();
  transfers = new Set();
  holiday = new Holiday();

  @ViewChild('calendar') calendar: MatCalendar<Date>;

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.loadCalendars();
  }

  private loadCalendars() {
    this.api.loadCalendars().subscribe(calendars => this.calendars = calendars);
  }

  addCalendar(locale) {
    this.api.addCalendar({ locale })
        .subscribe(calendar => this.calendars.push(calendar));
  }

  deleteCalendar(calendar) {
    this.api.deleteCalendar(calendar)
        .subscribe(() => this.calendars.splice(this.calendars.indexOf(calendar), 1));
  }

  onSelect($event: any) {
    if (!this.holiday.date) {
      this.holiday.date = $event.toISOString();
    } else if (!this.holiday.transferDate) {
      this.holiday.transferDate = $event.toISOString();
    } else {
      this.holiday.date = $event.toISOString();
      this.holiday.transferDate = null;
    }
    this.calendar.updateTodaysDate();
  }

  dateClass() {
    return (date: Date) => {
      const dateStr = date.toISOString().split('T').shift();
      if (this.holiday && this.holiday.date && dateStr == this.holiday.date) {
        return 'selected-start-date';
      } else if (this.holiday && this.holiday.transferDate && dateStr == this.holiday.transferDate) {
        return 'selected-shift-date';
      } else if (this.dates && this.dates.has(dateStr)) {
        return 'start-date';
      } else if (this.transfers && this.transfers.has(dateStr)) {
        return 'shift-date';
      }
      return '';
    };
  }

  private splitDates() {
    this.dates.clear();
    this.transfers.clear();
    this.holidays.forEach(holiday => {
      this.dates.add(holiday.date);
      if (holiday.transferDate) {
        this.transfers.add(holiday.transferDate);
      }
    })
    this.calendar.updateTodaysDate();
  }

  selectCalendar($event) {
    this.selectedCalendar = $event.option.value;
    this.loadHolidays();
  }

  loadHolidays() {
    this.api.loadHolidays(this.selectedCalendar)
        .subscribe(holidays => {
          this.holidays = holidays;
          this.splitDates();
        })
  }

  addHoliday() {
    this.api.createHoliday(this.selectedCalendar, this.holiday)
        .subscribe(holiday => {
          this.holidays.push(holiday);
          this.holiday = new Holiday();
          this.splitDates();
        })
  }

  deleteHoliday(holiday) {
    this.api.deleteHoliday(this.selectedCalendar, holiday)
        .subscribe(() => {
          this.holidays.splice(this.holidays.indexOf(holiday), 1);
          this.splitDates();
        });
  }
}
