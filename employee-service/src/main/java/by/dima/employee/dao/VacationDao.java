package by.dima.employee.dao;

import by.dima.employee.model.Vacation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface VacationDao extends ReactiveCrudRepository<Vacation, Long> {
//    todo add query
    Flux<Vacation> findAllByManager(Long managerId);
}
