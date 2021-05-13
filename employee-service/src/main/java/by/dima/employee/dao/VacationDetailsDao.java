package by.dima.employee.dao;

import by.dima.employee.dto.VacationViewDetails;
import by.dima.employee.model.Vacation;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VacationDetailsDao extends ReactiveCrudRepository<VacationViewDetails, Long> {
    @Query("select v.* from vacation_details v where v.user_id = :#{#userId}")
    Flux<VacationViewDetails> findAllByUserId(Long userId);
    @Query("select v.*, concat(u.first_name, ' ',u.second_name) as user from vacation_details v inner join user u on u.id = v.user_id " +
            "where exists(select * from user_project up inner join user_project me on me.project_id = up.project_id and me.user_id = :#{#userId} where up.user_id = v.user_id)")
    Flux<VacationViewDetails> findTeamByUserId(Long userId);
    @Query("select v.*, concat(u.first_name, ' ',u.second_name) as user from vacation_details v inner join user u on u.id = v.user_id where exists(select * from manager_users mu where mu.id = v.user_id and mu.manager_id = :#{#managerId})")
    Flux<VacationViewDetails> findAllByManagerId(Long managerId);
}
