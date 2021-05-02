package by.dima.project.controller;

import by.dima.model.Bonus;
import by.dima.model.Position;
import by.dima.model.Salary;
import by.dima.model.User;
import by.dima.project.model.Employee;
import by.dima.project.service.EmployeeService;
import by.dima.utils.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static by.dima.utils.TokenUtils.MANAGER_ROLE;
import static by.dima.utils.TokenUtils.is;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/employee")
public class UserController {
    private final EmployeeService employeeService;

    public UserController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Flux<Employee>> getEmployees(@RequestHeader(AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(employeeService.getEmployees(user.getId()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{projectId}/{userId}/bonus")
    public ResponseEntity<Mono<Void>> giveBonus(@PathVariable long projectId, @PathVariable Long userId, @RequestBody Bonus bonus, @RequestHeader(AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(employeeService.giveBonus(user.getId(), userId, projectId, bonus));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{projectId}/{userId}/salary")
    public ResponseEntity<Mono<Void>> changeSalary(@PathVariable long projectId, @PathVariable long userId, @RequestBody Salary salary, @RequestHeader(AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(employeeService.changeSalary(user.getId(), userId, projectId, salary));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/position")
    public ResponseEntity<Flux<Position>> getPositions(@RequestHeader(AUTHORIZATION) String authHeader) {
        if (is(authHeader, MANAGER_ROLE)) {
            return ResponseEntity.ok(employeeService.getPositions());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{userId}/position")
    public ResponseEntity<Mono<Void>> changePosition(@PathVariable long userId, @RequestBody Position position, @RequestHeader(AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(employeeService.changePosition(user.getId(), userId, position));
        }
        return ResponseEntity.badRequest().build();
    }
}
