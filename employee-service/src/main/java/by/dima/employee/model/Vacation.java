package by.dima.employee.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
public class Vacation {
    @Id
    private Long id;
    private LocalDate startDate;
//  todo add endDate, which will be calculated within function in db
    private Long length;
    private boolean approved;
    private boolean viewed;

//    todo add transient user name and, email
    private Long userId;
}
