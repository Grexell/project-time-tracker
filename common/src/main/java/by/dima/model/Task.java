package by.dima.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDate;

@Data
public class Task {
    @Id
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int urgency;
    private int complexity;
    @JsonIgnore
    @Transient
    private Long projectId;
    @JsonIgnore
    private Long userId;
    @Transient
    private User user;
}
