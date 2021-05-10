package by.dima.employee.dao;

import by.dima.model.Report;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReportDao extends ReactiveCrudRepository<Report, Long> {
    @Query("select * from report r where exists(select 1 from task t inner join user_project u on u.id = t.user_id where u.user_id = :#{#userId})")
    Flux<Report> findAll(long userId);
}
