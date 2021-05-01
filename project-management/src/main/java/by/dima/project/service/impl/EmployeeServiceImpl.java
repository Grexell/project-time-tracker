package by.dima.project.service.impl;

import by.dima.model.Bonus;
import by.dima.model.Salary;
import by.dima.project.model.Employee;
import by.dima.project.service.EmployeeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public Flux<Employee> getEmployees(Long managerId) {
        return null;
    }

    @Override
    public Mono<Void> giveBonus(Long managerId, Bonus bonus) {
        return null;
    }

    @Override
    public Mono<Void> changeSalary(Long managerId, Salary salary) {
        return null;
    }
}
