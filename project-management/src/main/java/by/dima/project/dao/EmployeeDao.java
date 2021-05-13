package by.dima.project.dao;

import by.dima.project.model.Employee;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EmployeeDao extends ReactiveCrudRepository<Employee, Long> {
    @Query("SELECT mu.* FROM manager_user_details mu where manager_id = :#{#managerId} or manager_id is null")
    Flux<Employee> findAll(long managerId);
}
