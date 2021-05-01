package by.dima.employee.service;

import by.dima.model.Report;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReportService {
    Flux<Report> getReports(Long user);
    Mono<Report> createReport(Report report);
    Mono<Report> updateReport(Report report);
}
