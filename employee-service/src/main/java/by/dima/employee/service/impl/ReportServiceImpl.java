package by.dima.employee.service.impl;

import by.dima.employee.dao.ReportDao;
import by.dima.employee.dao.TaskDao;
import by.dima.employee.service.ReportService;
import by.dima.model.Report;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportDao reportDao;
    private final TaskDao taskDao;

    public ReportServiceImpl(ReportDao reportDao, TaskDao taskDao) {
        this.reportDao = reportDao;
        this.taskDao = taskDao;
    }

    @Override
    public Flux<Report> getReports(Long user) {
        return reportDao.findAll(user);
    }

    @Override
    public Mono<Report> createReport(Long user, Report report) {
        report.setId(null);
        return updateReport(user, report);
    }

    @Override
    public Mono<Report> updateReport(Long user, Report report) {
        return taskDao.findByUserAndId(user, report.getTaskId()).flatMap(task -> reportDao.save(report));
    }
}
