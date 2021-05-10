package by.dima.employee.service;

import by.dima.model.Report;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReportService {
    Flux<Report> getReports(Long user);
    Mono<Report> createReport(Long user, Report report);
    Mono<Report> updateReport(Long user, Report report);
}
