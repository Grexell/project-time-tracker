package by.dima.project.dao;

import by.dima.model.Salary;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface SalaryDao extends ReactiveCrudRepository<Salary, Long> {
    @Query("CALL increase_salary(:#{#managerId}, :#{#userId}, :#{#projectId}, :#{#amount}, :#{#monthly})")
    Mono<Void> increaseSalary(Long managerId, Long userId, Long projectId, double amount, boolean monthly);
}
