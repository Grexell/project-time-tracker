package by.dima.holiday.dao;

import by.dima.model.Holiday;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface HolidayDao extends ReactiveCrudRepository<Holiday, Long> {
    Flux<Holiday> findAllByCalendarId(Long calendarId);
}
