package by.dima.holiday.controller;

import by.dima.holiday.service.CalendarService;
import by.dima.model.Calendar;
import by.dima.model.Holiday;
import by.dima.utils.TokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static by.dima.utils.TokenUtils.ADMIN_ROLE;

@RestController("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public ResponseEntity<Flux<Calendar>> getCalendars(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            return ResponseEntity.ok(calendarService.getCalendars());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Mono<Calendar>> createCalendar(@RequestBody Calendar calendar, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            return ResponseEntity.ok(calendarService.createCalendar(calendar));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{calendarId}")
    public ResponseEntity<Mono<Calendar>> deleteCalendar(@PathVariable Long calendarId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            return ResponseEntity.ok(calendarService.deleteCalendar(calendarId));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{calendarId}/holiday")
    public ResponseEntity<Flux<Holiday>> getCalendars(@PathVariable Long calendarId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            return ResponseEntity.ok(calendarService.getHolidays(calendarId));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("{calendarId}/holiday")
    public ResponseEntity<Mono<Holiday>> createHoliday(@PathVariable Long calendarId, @RequestBody Holiday holiday, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            holiday.setCalendarId(calendarId);
            return ResponseEntity.ok(calendarService.createHoliday(holiday));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{calendarId}/holiday/{holidayId}")
    public ResponseEntity<Mono<Holiday>> deleteHoliday(@PathVariable Long calendarId, @PathVariable Long holidayId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            return ResponseEntity.ok(calendarService.deleteHoliday(holidayId));
        }
        return ResponseEntity.badRequest().build();
    }
}
