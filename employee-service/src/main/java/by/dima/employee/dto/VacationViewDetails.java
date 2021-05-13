package by.dima.employee.dto;

import by.dima.employee.model.Vacation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class VacationViewDetails extends Vacation {
    private String user;
    private LocalDate endDate;
}
