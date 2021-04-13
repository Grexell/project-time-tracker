package by.dima.holiday.dao;

import by.dima.model.Calendar;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CalendarDao extends ReactiveCrudRepository<Calendar, Long> {
}
