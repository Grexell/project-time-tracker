package by.dima.holiday.dao;

import by.dima.model.Holiday;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface HolidayDao extends ReactiveCrudRepository<Holiday, Long> {
    Flux<Holiday> findAllByCalendarId(Long calendarId);

    @Query("select * from holiday h where h.calendar_id = (select u.calendar_id from user u where u.id=:#{#userId})")
    Flux<Holiday> findAllByUserId(Long userId);
}
