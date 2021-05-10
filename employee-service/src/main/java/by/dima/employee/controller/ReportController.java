package by.dima.employee.controller;

import by.dima.employee.service.ReportService;
import by.dima.model.Report;
import by.dima.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static by.dima.utils.TokenUtils.extractUser;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public Flux<Report> getReports(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = extractUser(authHeader);
        return reportService.getReports(user.getId());
    }

    @PostMapping
    public Mono<Report> createReport(@RequestBody Report report, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = extractUser(authHeader);
        return reportService.createReport(user.getId(), report);
    }

    @PutMapping
    public Mono<Report> updateReport(@RequestBody Report report, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = extractUser(authHeader);
        return reportService.updateReport(user.getId(), report);
    }
}
