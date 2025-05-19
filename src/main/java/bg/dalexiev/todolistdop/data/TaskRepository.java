package bg.dalexiev.todolistdop.data;

import bg.dalexiev.todolistdop.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
