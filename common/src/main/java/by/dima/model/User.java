package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table("\"user\"")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    @Transient
    private List<Role> roles;
}
