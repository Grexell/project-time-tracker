package by.dima.holiday.service;

import by.dima.model.Calendar;
import by.dima.model.Holiday;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CalendarService {
    Flux<Calendar> getCalendars();
    Mono<Calendar> createCalendar(Calendar calendar);
    Mono<Calendar> deleteCalendar(Long calendarId);

    Flux<Holiday> getHolidays(Long calendarId);
    Flux<Holiday> getCurrentHolidays(Long userId);
    Mono<Holiday> createHoliday(Holiday holiday);
    Mono<Holiday> deleteHoliday(Long holidayId);
}
