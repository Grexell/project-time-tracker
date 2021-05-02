package by.dima.project.dto;

import by.dima.project.model.ProjectDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectDetailsDto extends ProjectDetails {
    private boolean attached;
}
