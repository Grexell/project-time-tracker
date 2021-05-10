package by.dima.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
public class Task {
    @Id
    private Long id;
    private String name;
    private Date createdAt;
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
