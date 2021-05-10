package by.dima.employee.service;

import by.dima.employee.model.Vacation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VacationService {
    Flux<Vacation> getVacations(Long userId);
    Flux<Vacation> getTeamVacations(Long userId);
    Flux<Vacation> getManagedVacations(Long managerId);
    Mono<Vacation> requestVacation(Vacation vacation);
    Mono<Void> acceptVacation(Long managerId, Long vacationId);
    Mono<Void> rejectVacation(Long managerId, Long vacationId);
}
