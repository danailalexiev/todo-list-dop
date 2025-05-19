package bg.dalexiev.todolistdop.web.response;

import bg.dalexiev.todolistdop.domain.Task;
import bg.dalexiev.todolistdop.domain.TaskStatus;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskResponse {

  public static TaskResponse from(Task task) {
    return new TaskResponse(
        task.getTitle(), task.getDescription(), task.getDueAt(), task.getStatus(), task.getCompletedAt(),
        task.getComment(), task.getCancelledAt(), task.getReason()
    );
  }

  String title;
  String description;
  LocalDate dueAt;
  TaskStatus status;

  LocalDate completedAt;
  String comment;

  LocalDate cancelledAt;
  String reason;

}
