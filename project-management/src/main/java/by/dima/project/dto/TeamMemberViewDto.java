package by.dima.project.dto;

import by.dima.project.model.TeamMember;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeamMemberViewDto extends TeamMember {
    private double budget;
    private Long position;
    private long month;
}
