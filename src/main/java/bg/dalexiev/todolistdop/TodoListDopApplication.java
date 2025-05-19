package bg.dalexiev.todolistdop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class TodoListDopApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(TodoListDopApplication.class, args);
  }

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public void run(String... args) throws Exception {

    jdbcTemplate.execute("DROP TABLE IF EXISTS tasks");

    jdbcTemplate.execute(
        """
                CREATE TABLE tasks(
                   id SERIAL PRIMARY KEY,
                   title varchar(255),
                   description varchar(255),
                   due_at date,
                   status varchar(255),
                   completed_at date,
                   comment varchar(255),
                   cancelled_at date,
                   reason varchar(255)               
                );
            """
    );

  }
}
