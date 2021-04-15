package by.dima.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {
    @Id
    private Long id;
    private String locale;

    public Calendar(long id) {
        this.id = id;
    }
}
