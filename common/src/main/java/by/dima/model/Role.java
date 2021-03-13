package by.dima.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Role {
    @Id
    private Long id;
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
