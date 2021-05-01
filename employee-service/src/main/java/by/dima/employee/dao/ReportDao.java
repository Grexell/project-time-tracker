package by.dima.employee.dao;

import by.dima.model.Report;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReportDao extends ReactiveCrudRepository<Report, Long> {
}
