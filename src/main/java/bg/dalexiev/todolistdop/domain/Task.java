package bg.dalexiev.todolistdop.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "tasks")
public class Task {
  
  @Id
  private long id;
  
  private String title;
  private String description;
  private LocalDate dueAt;
  private TaskStatus status;
  
  private LocalDate completedAt;
  private String comment;
  
  private LocalDate cancelledAt;
  private String reason;
  
}
