package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDate;

@Data
public class Holiday {
    @Id
    private Long id;
    private LocalDate date;
    private LocalDate transferDate;
    private Long calendarId;

    @Transient
    private Calendar calendar;
}
