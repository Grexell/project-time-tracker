package by.dima.employee.service.impl;

import by.dima.employee.dao.ProjectDao;
import by.dima.employee.dao.TaskDao;
import by.dima.employee.dao.UserProjectDao;
import by.dima.employee.service.ProjectService;
import by.dima.model.Project;
import by.dima.model.Task;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;
    private final TaskDao taskDao;
    private final UserProjectDao userDao;

    public ProjectServiceImpl(ProjectDao projectDao, TaskDao taskDao, UserProjectDao userDao) {
        this.projectDao = projectDao;
        this.taskDao = taskDao;
        this.userDao = userDao;
    }

    @Override
    public Flux<Project> getProjects(Long userId) {
        return projectDao.findAllByUserId(userId);
    }

    @Override
    public Flux<Task> getTasks(Long userId, Long projectId) {
        return taskDao.findAllByUserAndProject(userId, projectId);
    }

    @Override
    public Mono<Task> createTask(Long userId, Task task) {
        return userDao.findByUserIdAndProjectId(userId, task.getProjectId()).flatMap(user -> {
            task.setStartDate(LocalDate.now());
            task.setUserId(user.getUserId());
            return taskDao.save(task);
        });
    }

    @Override
    public Mono<Task> updateTask(Long userId, Task task) {
        return userDao.findByUserIdAndProjectId(userId, task.getProjectId()).flatMap(user -> {
            task.setUserId(user.getUserId());
            return taskDao.save(task);
        });
    }
}
