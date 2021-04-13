package by.dima.holiday.service.impl;

import by.dima.holiday.dao.CalendarDao;
import by.dima.holiday.dao.HolidayDao;
import by.dima.holiday.service.CalendarService;
import by.dima.model.Calendar;
import by.dima.model.Holiday;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CalendarServiceImpl implements CalendarService {

    private final CalendarDao calendarDao;
    private final HolidayDao holidayDao;

    public CalendarServiceImpl(CalendarDao calendarDao, HolidayDao holidayDao) {
        this.calendarDao = calendarDao;
        this.holidayDao = holidayDao;
    }

    @Override
    public Flux<Calendar> getCalendars() {
        return calendarDao.findAll();
    }

    @Override
    public Mono<Calendar> createCalendar(Calendar calendar) {
        calendar.setId(null);
        return calendarDao.save(calendar);
    }

    @Override
    public Mono<Calendar> deleteCalendar(Long calendarId) {
        return calendarDao.findById(calendarId).flatMap(calendar -> calendarDao.delete(calendar).thenReturn(calendar));
    }

    @Override
    public Flux<Holiday> getHolidays(Long calendarId) {
        return holidayDao.findAllByCalendarId(calendarId);
    }

    @Override
    public Mono<Holiday> createHoliday(Holiday holiday) {
        return holidayDao.save(holiday);
    }

    @Override
    public Mono<Holiday> deleteHoliday(Long holidayId) {
        return holidayDao.findById(holidayId).flatMap(holiday -> holidayDao.delete(holiday).thenReturn(holiday));
    }
}
