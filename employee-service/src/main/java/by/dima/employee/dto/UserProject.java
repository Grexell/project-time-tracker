package by.dima.employee.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("user_project")
public class UserProject {
    @Id
    private long id;
    private long userId;
    private long projectId;
}
