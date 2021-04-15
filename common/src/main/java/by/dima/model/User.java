package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
public class User {
    @Id
    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    @Transient
    private Role role;
}
