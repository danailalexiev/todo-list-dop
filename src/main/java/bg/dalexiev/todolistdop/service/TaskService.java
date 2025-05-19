package bg.dalexiev.todolistdop.service;

import bg.dalexiev.todolistdop.domain.TaskStatus;
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
        .filter(task -> TaskStatus.PENDING == task.getStatus())
        .map(task -> {
          task.setComment(comment);
          task.setDueAt(LocalDate.now());
          task.setStatus(TaskStatus.COMPLETED);
          return task;
        })
        .map(taskRepository::save);
  }
  
  @Nonnull
  public Optional<Task> cancelTask(long taskId, @Nullable String reason) {
    return taskRepository.findById(taskId)
        .filter(task -> TaskStatus.PENDING == task.getStatus())
        .map(task -> {
          task.setReason(reason);
          task.setCancelledAt(LocalDate.now());
          task.setStatus(TaskStatus.CANCELLED);
          return task;
        })
        .map(taskRepository::save);
  }

  @Nonnull
  public Optional<Task> findById(long taskId) {
    return taskRepository.findById(taskId);
  }
}
