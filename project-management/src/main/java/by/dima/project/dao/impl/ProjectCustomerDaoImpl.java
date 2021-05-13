package by.dima.project.dao.impl;

import by.dima.project.dao.ProjectCustomerDao;
import by.dima.project.model.ProjectCustomer;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProjectCustomerDaoImpl implements ProjectCustomerDao {
    private final DatabaseClient client;

    public ProjectCustomerDaoImpl(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public Flux<ProjectCustomer> findAllByProjectId(long projectId) {
        return client.sql("select * from customer_project where project_id = :project")
                .bind("project", projectId)
                .map((row, rowMetadata) -> new ProjectCustomer(row.get("customer_id", Long.class), row.get("project_id", Long.class)))
                .all();
    }

    @Override
    public Mono<Void> deleteAll(long projectId) {
        return  client.sql("delete from customer_project where project_id = :project")
                .bind("project", projectId)
                .then();
    }

    @Override
    public Mono<Void> saveAll(List<ProjectCustomer> entities) {
        return client.sql("insert into customer_project(customer_id, project_id) values :entities")
                .bind("entities", entities.stream()
                        .map(customer -> new Object[]{ customer.getCustomerId(), customer.getProjectId() })
                        .collect(Collectors.toList()))
                .then();
    }
}
