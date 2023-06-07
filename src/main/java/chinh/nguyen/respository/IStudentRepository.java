package chinh.nguyen.respository;

import chinh.nguyen.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContaining(String name);
    @Query("SELECT st FROM Student AS st WHERE st.name LIKE CONCAT('%',:name,'%')")
    List<Student> findByNameFull(@Param("name") String name);

}
