package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
public class Report {
    @Id
    private Long id;
    private String text;
    private LocalDate date;
    private Double time;
    private Long taskId;
}
