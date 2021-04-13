package by.dima.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Calendar {
    @Id
    private Long id;
    private String locale;
}
