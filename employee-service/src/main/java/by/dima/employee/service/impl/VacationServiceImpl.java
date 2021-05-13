package by.dima.employee.service.impl;

import by.dima.employee.dao.VacationDao;
import by.dima.employee.dao.VacationDetailsDao;
import by.dima.employee.dto.VacationViewDetails;
import by.dima.employee.model.Vacation;
import by.dima.employee.service.VacationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VacationServiceImpl implements VacationService {
    private final VacationDao vacationDao;
    private final VacationDetailsDao vacationDetailsDao;

    public VacationServiceImpl(VacationDao vacationDao, VacationDetailsDao vacationDetailsDao) {
        this.vacationDao = vacationDao;
        this.vacationDetailsDao = vacationDetailsDao;
    }

    @Override
    public Flux<VacationViewDetails> getVacations(Long userId) {
        return vacationDetailsDao.findAllByUserId(userId);
    }

    @Override
    public Flux<VacationViewDetails> getTeamVacations(Long userId) {
        return vacationDetailsDao.findTeamByUserId(userId);
    }

    @Override
    public Flux<VacationViewDetails> getManagedVacations(Long managerId) {
        return vacationDetailsDao.findAllByManagerId(managerId);
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
