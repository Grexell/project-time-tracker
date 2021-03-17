import { Component, OnInit } from '@angular/core';
import {of} from "rxjs";

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
    console.log($event);
  }

  dateClass() {
    return (date: Date) => {
      if (date.getDate() === 1) {
        return 'special-date';
      } else {
        return;
      }
    };
  }

  selectCalendar($event) {
    this.selectedCalendar = $event.option.value;

  }
}
