package bg.dalexiev.todolistdop.web.request;

import lombok.Value;

@Value
public class CancelTaskRequest {
  String reason;
}
