package by.dima.employee.service.impl;

import by.dima.employee.dao.VacationDao;
import by.dima.employee.model.Vacation;
import by.dima.employee.service.VacationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VacationServiceImpl implements VacationService {
    private final VacationDao vacationDao;

    public VacationServiceImpl(VacationDao vacationDao) {
        this.vacationDao = vacationDao;
    }

    @Override
    public Flux<Vacation> getVacations(Long managerId) {
        return vacationDao.findAllByManager(managerId);
    }

    @Override
    public Mono<Vacation> requestVacation(Vacation vacation) {
        return vacationDao.save(vacation);
    }

    @Override
    public Mono<Void> acceptVacation(Long vacationId) {
        return null;
    }

    @Override
    public Mono<Void> rejectVacation(Long vacationId) {
        return null;
    }
}
