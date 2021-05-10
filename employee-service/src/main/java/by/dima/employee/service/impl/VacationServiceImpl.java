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
    public Flux<Vacation> getVacations(Long userId) {
        return vacationDao.findAllByUserId(userId);
    }

    @Override
    public Flux<Vacation> getTeamVacations(Long userId) {
        return vacationDao.findTeamByUserId(userId);
    }

    @Override
    public Flux<Vacation> getManagedVacations(Long managerId) {
        return vacationDao.saveAll(vacationDao.findAllByManagerId(managerId)
                .doOnNext(vacation -> vacation.setViewed(true)));
    }

    @Override
    public Mono<Vacation> requestVacation(Vacation vacation) {
        return vacationDao.save(vacation);
    }

    @Override
    public Mono<Void> acceptVacation(Long managerId, Long vacationId) {
        return vacationDao.acceptVacation(managerId, vacationId);
    }

    @Override
    public Mono<Void> rejectVacation(Long managerId, Long vacationId) {
        return vacationDao.rejectVacation(managerId, vacationId);
    }
}
