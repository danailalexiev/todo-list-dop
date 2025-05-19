package bg.dalexiev.todolistdop.web.request;

import java.time.LocalDate;
import lombok.Value;

@Value
public class CreateTaskRequest {
  String title;
  String description;
  LocalDate dueAt;
}
