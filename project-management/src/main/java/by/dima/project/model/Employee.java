package by.dima.project.model;

import by.dima.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends User {
    private Long position;
    private Double salary;
}
