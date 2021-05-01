package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDate;
import java.util.List;

@Data
public class Project {
    @Id
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
