package by.dima.employee.controller;

import by.dima.employee.service.ReportService;
import by.dima.model.Report;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public Flux<Report> getReports(Long user) {
        return reportService.getReports(user);
    }

    @PostMapping
    public Mono<Report> createReport(@RequestBody Report report) {
        return reportService.createReport(report);
    }

    @PutMapping
    public Mono<Report> updateReport(@RequestBody Report report) {
        return reportService.updateReport(report);
    }
}
