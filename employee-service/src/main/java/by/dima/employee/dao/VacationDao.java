package by.dima.employee.dao;

import by.dima.employee.model.Vacation;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VacationDao extends ReactiveCrudRepository<Vacation, Long> {
//    todo add user's name to vacation
    Flux<Vacation> findAllByUserId(Long userId);
    @Query("select * from vacation v " +
            "where exists(select * from user_project up inner join user_project me on me.project_id = up.project_id and me.user_id = :#{#userid} where up.user_id = v.user_id)")
    Flux<Vacation> findTeamByUserId(Long userId);
    @Query("select * from vacation v where exists(select * from manager_users mu where mu.id = v.user_id and mu.manager_id = :#{#managerId})")
    Flux<Vacation> findAllByManagerId(Long managerId);

    @Query("call approve_vacation(:#{#managerId}, :#{#vacationId})")
    Mono<Void> acceptVacation(Long managerId, Long vacationId);

    @Query("call reject_vacation(:#{#managerId}, :#{#vacationId})")
    Mono<Void> rejectVacation(Long managerId, Long vacationId);
}
