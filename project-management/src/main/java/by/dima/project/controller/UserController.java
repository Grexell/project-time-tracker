package by.dima.project.controller;

import by.dima.model.Bonus;
import by.dima.model.Salary;
import by.dima.project.model.Employee;
import by.dima.project.service.EmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employee")
public class UserController {
    private final EmployeeService employeeService;

    public UserController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Flux<Employee> getEmployees(Long managerId) {
        return employeeService.getEmployees(managerId);
    }

    public Mono<Void> giveBonus(Long managerId, Bonus bonus) {
        return employeeService.giveBonus(managerId, bonus);
    }

    public Mono<Void> changeSalary(Long managerId, Salary salary) {
        return employeeService.changeSalary(managerId, salary);
    }
}
