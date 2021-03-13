package by.dima.model;

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
    @Transient
    private User user;
}
