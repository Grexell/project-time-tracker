package by.dima.employee.dao;

import by.dima.employee.dto.UserProject;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserProjectDao extends ReactiveCrudRepository<UserProject, Long> {
    Mono<UserProject> findByUserIdAndProjectId(long userId, long projectId);
}
