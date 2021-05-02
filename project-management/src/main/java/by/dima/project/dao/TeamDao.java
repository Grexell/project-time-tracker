package by.dima.project.dao;

import by.dima.project.model.TeamMember;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface TeamDao extends ReactiveCrudRepository<TeamMember, Long> {
    Flux<TeamMember> findAllByProjectId(long projectId);
}
