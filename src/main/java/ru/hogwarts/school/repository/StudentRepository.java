package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int min, int max);

    List<Student> findByFacultyId(long id);

    @Query(value="SELECT COUNT(id) FROM student", nativeQuery = true)
    Integer getAllStudentsAmount();

    @Query(value = "SELECT ROUND(AVG(age),2) FROM student", nativeQuery = true)
    Double getAvgStudentAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();

}
