package by.dima.project.service.impl;

import by.dima.model.Bonus;
import by.dima.model.Position;
import by.dima.model.Salary;
import by.dima.project.dao.BonusDao;
import by.dima.project.dao.EmployeeDao;
import by.dima.project.dao.PositionDao;
import by.dima.project.dao.SalaryDao;
import by.dima.project.model.Employee;
import by.dima.project.service.EmployeeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;
    private final SalaryDao salaryDao;
    private final PositionDao positionDao;
    private final BonusDao bonusDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao, SalaryDao salaryDao, PositionDao positionDao, BonusDao bonusDao) {
        this.employeeDao = employeeDao;
        this.salaryDao = salaryDao;
        this.positionDao = positionDao;
        this.bonusDao = bonusDao;
    }

    @Override
    public Flux<Employee> getEmployees(Long managerId) {
        return employeeDao.findAll(managerId);
    }

    @Override
    public Mono<Void> giveBonus(Long managerId, Long userId, long projectId, Bonus bonus) {
        return bonusDao.addBonus(managerId, userId, projectId, bonus);
    }

    @Override
    public Mono<Void> changeSalary(Long managerId, long userId, long projectId, Salary salary) {
        return salaryDao.increaseSalary(managerId, userId, projectId, salary.getAmount(), salary.isMonthly());
    }

    @Override
    public Flux<Position> getPositions() {
        return positionDao.findAll();
    }

    @Override
    public Mono<Void> changePosition(Long managerId, Long userId, Position position) {
        return positionDao.save(position).flatMap(pos -> positionDao.changePosition(managerId, userId, pos));
    }
}
