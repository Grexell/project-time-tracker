package by.dima.project.dao;

import by.dima.project.model.TeamMember;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TeamDao extends ReactiveCrudRepository<TeamMember, Long> {
}
