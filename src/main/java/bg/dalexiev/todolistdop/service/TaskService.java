package bg.dalexiev.todolistdop.service;

import bg.dalexiev.todolistdop.data.TaskRepository;
import bg.dalexiev.todolistdop.domain.Task;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;

  @Nonnull
  public Task.PendingTask createTask(@Nonnull String title, @Nonnull String description, @Nonnull LocalDate dueAt) {
    final Task.PendingTask newTask = new Task.PendingTask(0, title, description, dueAt);
    return switch (save(newTask)) {
      case Task.PendingTask pendingTask -> pendingTask;
      case Task.CancelledTask cancelledTask ->
          throw new IllegalStateException("Expected PendingTask, but was " + cancelledTask.getClass().getSimpleName());
      case Task.CompletedTask completedTask ->
          throw new IllegalStateException("Expected PendingTask, but was " + completedTask.getClass().getSimpleName());
    };
  }

  @Nonnull
  public Optional<Task> completeTask(long taskId, @Nullable String comment) {
    return findById(taskId)
        .map(task -> switch (task) {
          case Task.PendingTask pendingTask -> save(
              new Task.CompletedTask(
                  pendingTask.id(), pendingTask.title(), pendingTask.description(), LocalDate.now(),
                  comment
              ));
          case Task.CancelledTask ignored -> null;
          case Task.CompletedTask ignored -> null;
        });
  }

  @Nonnull
  public Optional<Task> cancelTask(long taskId, @Nullable String reason) {
    return findById(taskId)
        .map(task -> switch (task) {
          case Task.PendingTask pendingTask -> save(
              new Task.CancelledTask(
                  pendingTask.id(), pendingTask.title(), pendingTask.description(), LocalDate.now(),
                  reason
              ));
          case Task.CancelledTask ignored -> null;
          case Task.CompletedTask ignored -> null;
        });
  }

  @Nonnull
  public Optional<Task> findById(long taskId) {
    return taskRepository.findById(taskId)
        .map(Task::fromEntity);
  }

  private Task save(Task task) {
    return Task.fromEntity(taskRepository.save(task.toEntity()));
  }
}
