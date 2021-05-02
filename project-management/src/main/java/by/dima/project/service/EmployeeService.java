package by.dima.project.service;

import by.dima.model.Bonus;
import by.dima.model.Position;
import by.dima.model.Salary;
import by.dima.project.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Flux<Employee> getEmployees(Long managerId);
    Mono<Void> giveBonus(Long managerId, Long userId, long projectId, Bonus bonus);
    Mono<Void> changeSalary(Long managerId, long userId, long projectId, Salary salary);
    Flux<Position> getPositions();
    Mono<Void> changePosition(Long managerId, Long userId, Position position);
}
