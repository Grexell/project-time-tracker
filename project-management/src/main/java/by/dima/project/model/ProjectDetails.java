package by.dima.project.model;

import by.dima.model.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectDetails extends Project {
    private double budget;
    @Transient
    private boolean attached;
    @Transient
    private List<Employee> team;
    @Transient
    private List<Customer> customers;
//    todo add info about tasks for reporting
}
