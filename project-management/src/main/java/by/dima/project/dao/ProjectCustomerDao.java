package by.dima.project.dao;

import by.dima.project.model.ProjectCustomer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProjectCustomerDao {
    Flux<ProjectCustomer> findAllByProjectId(long projectId);
    Mono<Void> saveAll(List<ProjectCustomer> entities);
}
