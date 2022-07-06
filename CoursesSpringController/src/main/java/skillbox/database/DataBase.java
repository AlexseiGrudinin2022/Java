package skillbox.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skillbox.model.Course;

@Repository

public interface DataBase extends JpaRepository<Course, Integer> {

}
