package by.dima.project.dao;

import by.dima.project.dto.TeamMemberViewDto;
import by.dima.project.model.TeamMember;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TeamDao extends ReactiveCrudRepository<TeamMember, Long> {
    @Query("select up.*, ifnull(get_project_salary(curdate(), up.id, true), get_project_salary(curdate(), up.id, false)) as budget," +
            " if(get_project_salary(curdate(), up.id, true) is null, 0, 1) as month, u.position from user_project up" +
            " inner join manager_user_details u on u.id = up.user_id " +
            "where up.project_id = :#{#projectId}")
    Flux<TeamMemberViewDto> findAllByProjectId(long projectId);

    @Modifying
    Mono<Void> deleteAllByIdNotInAndProjectId(List<Long> ids, Long projectId);
}
