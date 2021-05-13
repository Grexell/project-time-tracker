package by.dima.project.model;

import by.dima.model.Project;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("project")
public class ProjectDetails extends Project {
    private double budget;
    @Transient
    private boolean attached;
    @Transient
    private List<? extends TeamMember> team = new ArrayList<>();
    @Transient
    private List<ProjectCustomer> customers = new ArrayList<>();
//    todo add info about tasks for reporting
}
