package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
public class Project {
    @Id
    private Long id;
    private String code;
    private String name;
    private Long managedBy;
    @Transient
    private List<User> team;
    @Transient
    private List<Task> tasks;
}
