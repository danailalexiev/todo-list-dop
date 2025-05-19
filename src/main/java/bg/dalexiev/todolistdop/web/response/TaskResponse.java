package bg.dalexiev.todolistdop.web.response;

import bg.dalexiev.todolistdop.domain.Task;
import java.time.LocalDate;

public record TaskResponse(
    String title,
    String description,
    LocalDate dueAt,
    String status,
    LocalDate completedAt,
    String comment,
    LocalDate cancelledAt,
    String reason
) {

  public static TaskResponse from(Task task) {
    return switch (task) {
      case Task.CancelledTask cancelledTask -> new TaskResponse(
          cancelledTask.title(), cancelledTask.description(), null, "CANCELLED", null,
          null, cancelledTask.cancelledAt(), cancelledTask.reason()
      );
      case Task.CompletedTask completedTask -> new TaskResponse(
          completedTask.title(), completedTask.description(), null, "COMPLETED", completedTask.completedAt(),
          completedTask.comment(), null, null
      );
      case Task.PendingTask pendingTask -> new TaskResponse(
          pendingTask.title(), pendingTask.description(), pendingTask.dueAt(), "PENDING", null, null, null, null
      );
    };
  }

}
