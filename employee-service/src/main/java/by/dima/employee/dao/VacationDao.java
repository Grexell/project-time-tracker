package by.dima.employee.dao;

import by.dima.employee.dto.VacationViewDetails;
import by.dima.employee.model.Vacation;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VacationDao extends ReactiveCrudRepository<Vacation, Long> {
    @Query("call approve_vacation(:#{#managerId}, :#{#vacationId})")
    Mono<Void> acceptVacation(Long managerId, Long vacationId);

    @Query("call reject_vacation(:#{#managerId}, :#{#vacationId})")
    Mono<Void> rejectVacation(Long managerId, Long vacationId);
}
