package by.dima.auth.model;

import by.dima.model.Calendar;
import by.dima.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("\"user\"")
@EqualsAndHashCode(callSuper = true)
public class Principal extends User {
    private String password;
    @JsonIgnore
    private long roleId;
    @JsonIgnore
    private long calendarId;
    private Calendar calendar;
}
