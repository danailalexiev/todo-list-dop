package bg.dalexiev.todolistdop.web.controller;

import bg.dalexiev.todolistdop.domain.Task;
import bg.dalexiev.todolistdop.service.TaskService;
import bg.dalexiev.todolistdop.web.request.CancelTaskRequest;
import bg.dalexiev.todolistdop.web.request.CompleteTaskRequest;
import bg.dalexiev.todolistdop.web.request.CreateTaskRequest;
import bg.dalexiev.todolistdop.web.response.TaskResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @PostMapping("/tasks")
  public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
    final Task newTask = taskService.createTask(request.title(), request.description(), request.dueAt());
    return ResponseEntity.created(URI.create("/tasks/" + newTask.id())).body(TaskResponse.from(newTask));
  }

  @PatchMapping("/tasks/{taskId}/complete")
  public ResponseEntity<TaskResponse> completeTask(
      @PathVariable String taskId, @RequestBody(required = false) CompleteTaskRequest request) {
    return ResponseEntity.of(
        taskService.completeTask(Long.parseLong(taskId), request == null ? null : request.comment())
            .map(TaskResponse::from)
    );
  }

  @PatchMapping("/tasks/{taskId}/cancel")
  public ResponseEntity<TaskResponse> cancelTask(
      @PathVariable String taskId, @RequestBody(required = false) CancelTaskRequest request) {
    return ResponseEntity.of(
        taskService.cancelTask(Long.parseLong(taskId), request == null ? null : request.reason())
            .map(TaskResponse::from)
    );
  }

  @GetMapping("/tasks/{taskId}")
  public ResponseEntity<TaskResponse> getTaskById(@PathVariable String taskId) {
    return ResponseEntity.of(
        taskService.findById(Long.parseLong(taskId))
            .map(TaskResponse::from)
    );
  }
}
