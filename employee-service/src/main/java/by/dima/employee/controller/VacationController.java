package by.dima.employee.controller;

import by.dima.employee.model.Vacation;
import by.dima.employee.service.VacationService;
import by.dima.model.User;
import by.dima.utils.TokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static by.dima.utils.TokenUtils.*;

@RestController("/vacation")
public class VacationController {
    private final VacationService vacationService;

    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping
    public ResponseEntity<Flux<Vacation>> getVacations(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(vacationService.getVacations(user.getId()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public Mono<Vacation> requestVacation(@RequestBody Vacation vacation, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        vacation.setUserId(user.getId());
        return vacationService.requestVacation(vacation);
    }

    @PostMapping("{vacationId}/accept")
    public ResponseEntity<Mono<Void>> acceptVacation(@PathVariable Long vacationId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (is(authHeader, MANAGER_ROLE)) {
            return ResponseEntity.ok(vacationService.acceptVacation(vacationId));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("{vacationId}/reject")
    public ResponseEntity<Mono<Void>> rejectVacation(@PathVariable Long vacationId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (is(authHeader, MANAGER_ROLE)) {
            return ResponseEntity.ok(vacationService.rejectVacation(vacationId));
        }
        return ResponseEntity.badRequest().build();
    }
}
