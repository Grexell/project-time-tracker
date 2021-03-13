package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Report {
    @Id
    private Long id;
    private String text;
    private Date date;
    private Double hours;
}
