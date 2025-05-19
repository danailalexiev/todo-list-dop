package bg.dalexiev.todolistdop.service;

import bg.dalexiev.todolistdop.data.TaskRepository;
import bg.dalexiev.todolistdop.domain.Task;
import bg.dalexiev.todolistdop.domain.TaskStatus;
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
  public Task createTask(@Nonnull String title, @Nonnull String description, @Nonnull LocalDate dueAt) {
    final Task newTask = Task.builder()
        .title(title)
        .description(description)
        .dueAt(dueAt)
        .status(TaskStatus.PENDING)
        .build();
    return taskRepository.save(newTask);
  }

  @Nonnull
  public Optional<Task> completeTask(long taskId, @Nullable String comment) {
    return taskRepository.findById(taskId)
        .filter(task -> TaskStatus.PENDING == task.status())
        .map(task -> task.toBuilder()
            .status(TaskStatus.COMPLETED)
            .completedAt(LocalDate.now())
            .comment(comment)
            .build())
        .map(taskRepository::save);
  }

  @Nonnull
  public Optional<Task> cancelTask(long taskId, @Nullable String reason) {
    return taskRepository.findById(taskId)
        .filter(task -> TaskStatus.PENDING == task.status())
        .map(task -> task.toBuilder()
            .status(TaskStatus.CANCELLED)
            .cancelledAt(LocalDate.now())
            .reason(reason)
            .build()
        )
        .map(taskRepository::save);
  }

  @Nonnull
  public Optional<Task> findById(long taskId) {
    return taskRepository.findById(taskId);
  }
}
