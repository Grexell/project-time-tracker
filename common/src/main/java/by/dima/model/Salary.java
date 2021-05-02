package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("salary")
public class Salary {
    @Id
    private long id;
    private double amount;
    private LocalDate changeDate;
    private boolean monthly;
}
