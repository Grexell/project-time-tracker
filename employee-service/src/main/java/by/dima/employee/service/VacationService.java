package by.dima.employee.service;

import by.dima.employee.dto.VacationViewDetails;
import by.dima.employee.model.Vacation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VacationService {
    Flux<VacationViewDetails> getVacations(Long userId);
    Flux<VacationViewDetails> getTeamVacations(Long userId);
    Flux<VacationViewDetails> getManagedVacations(Long managerId);
    Mono<Vacation> requestVacation(Vacation vacation);
    Mono<Void> acceptVacation(Long managerId, Long vacationId);
    Mono<Void> rejectVacation(Long managerId, Long vacationId);
}
