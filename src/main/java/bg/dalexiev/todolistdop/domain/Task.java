package bg.dalexiev.todolistdop.domain;

import bg.dalexiev.todolistdop.data.TaskEntity;
import bg.dalexiev.todolistdop.data.TaskStatus;
import java.time.LocalDate;

public sealed interface Task {

  static Task fromEntity(TaskEntity entity) {
    return switch (entity.status()) {
      case PENDING -> 
          new PendingTask(entity.id(), entity.title(), entity.description(), entity.dueAt());
      case COMPLETED ->
          new CompletedTask(entity.id(), entity.title(), entity.description(), entity.completedAt(), entity.comment());
      case CANCELLED ->
          new CancelledTask(entity.id(), entity.title(), entity.description(), entity.cancelledAt(), entity.reason());
    };
  }

  TaskEntity toEntity();

  record PendingTask(
      long id,
      String title,
      String description,
      LocalDate dueAt
  ) implements Task {

    @Override
    public TaskEntity toEntity() {
      return TaskEntity.builder()
          .id(id)
          .title(title)
          .description(description)
          .dueAt(dueAt)
          .status(TaskStatus.PENDING)
          .build();
    }
  }

  record CompletedTask(
      long id,
      String title,
      String description,
      LocalDate completedAt,
      String comment
  ) implements Task {

    @Override
    public TaskEntity toEntity() {
      return TaskEntity.builder()
          .id(id)
          .title(title)
          .description(description)
          .completedAt(completedAt)
          .comment(comment)
          .status(TaskStatus.COMPLETED)
          .build();
    }
  }

  record CancelledTask(
      long id,
      String title,
      String description,
      LocalDate cancelledAt,
      String reason
  ) implements Task {

    @Override
    public TaskEntity toEntity() {
      return TaskEntity.builder()
          .id(id)
          .title(title)
          .description(description)
          .cancelledAt(cancelledAt)
          .reason(reason)
          .status(TaskStatus.CANCELLED)
          .build();
    }
  }

}
