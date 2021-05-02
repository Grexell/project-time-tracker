package by.dima.project.dao;

import by.dima.project.model.ProjectCustomer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProjectCustomerDao extends ReactiveCrudRepository<ProjectCustomer, Long> {
}
