package bg.dalexiev.todolistdop.data;

import java.time.LocalDate;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder(toBuilder = true)
@Table(name = "tasks")
public record TaskEntity(
    @Id long id,

    String title,
    String description,
    LocalDate dueAt,
    TaskStatus status,

    LocalDate completedAt,
    String comment,

    LocalDate cancelledAt,
    String reason
) {}
