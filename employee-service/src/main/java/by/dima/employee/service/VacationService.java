package by.dima.employee.service;

import by.dima.employee.model.Vacation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VacationService {
    Flux<Vacation> getVacations(Long managerId);
    Mono<Vacation> requestVacation(Vacation vacation);
    Mono<Void> acceptVacation(Long vacationId);
    Mono<Void> rejectVacation(Long vacationId);
}
