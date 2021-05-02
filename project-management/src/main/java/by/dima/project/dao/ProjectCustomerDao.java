package by.dima.project.dao;

import by.dima.project.model.ProjectCustomer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ProjectCustomerDao extends ReactiveCrudRepository<ProjectCustomer, Long> {
    Flux<ProjectCustomer> findAllByProjectId(long projectId);
}
