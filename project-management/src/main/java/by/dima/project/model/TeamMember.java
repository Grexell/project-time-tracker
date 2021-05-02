package by.dima.project.model;

import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user_project")
public class TeamMember {
    private long userId;
    private long projectId;
    @Transient
    private double salary;
    @Transient
    private boolean monthly;
}
