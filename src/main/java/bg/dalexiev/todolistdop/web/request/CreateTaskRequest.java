package bg.dalexiev.todolistdop.web.request;

import java.time.LocalDate;

public record CreateTaskRequest(String title, String description, LocalDate dueAt) {
}
