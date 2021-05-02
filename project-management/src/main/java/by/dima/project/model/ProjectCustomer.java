package by.dima.project.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("customer_project")
public class ProjectCustomer {
    private long customerId;
    private long projectId;
}

