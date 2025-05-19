package bg.dalexiev.todolistdop.web.response;

import bg.dalexiev.todolistdop.domain.Task;
import bg.dalexiev.todolistdop.domain.TaskStatus;
import java.time.LocalDate;

public record TaskResponse(
    String title,
    String description,
    LocalDate dueAt,
    TaskStatus status,
    LocalDate completedAt,
    String comment,
    LocalDate cancelledAt,
    String reason
) {

  public static TaskResponse from(Task task) {
    return new TaskResponse(
        task.title(), task.description(), task.dueAt(), task.status(), task.completedAt(),
        task.comment(), task.cancelledAt(), task.reason()
    );
  }
  
}
