package by.dima.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
public class Vacation {
    @Id
    private Long id;
    private LocalDate startDate;
    private Long length;
    private boolean approved;
    private boolean viewed;

//    @JsonIgnore
    private Long userId;
}
