package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Position {
    @Id
    private Long id;
}
