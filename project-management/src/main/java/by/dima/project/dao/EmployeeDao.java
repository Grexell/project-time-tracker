package by.dima.project.dao;

import by.dima.project.model.Employee;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EmployeeDao extends ReactiveCrudRepository<Employee, Long> {
    @Query("SELECT mu.*, get_month_salary(CURDATE(), id) as salary, get_position(CURDATE(), id) as position FROM manager_users mu " +
            "where manager_id = :#{#managerId} or manager_id is null")
    Flux<Employee> findAll(long managerId);
}
