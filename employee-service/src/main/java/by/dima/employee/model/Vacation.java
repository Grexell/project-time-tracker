package by.dima.employee.model;

import by.dima.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

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

    @JsonIgnore
    private Long userId;

    @Transient
    private User user;
}
