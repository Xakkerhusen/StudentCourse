package org.example.student_cource_home_work.repository;

import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentCourseMarkEntity;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.example.student_cource_home_work.service.StudentCourseMarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer> {
    List<StudentCourseMarkEntity> findByStudentAndCreatedDateBetween(StudentEntity id, LocalDateTime localDateTime, LocalDateTime localDateTime2);
    List<StudentCourseMarkEntity> findByStudentOrderByMarkDesc(StudentEntity id);
    List<StudentCourseMarkEntity> findByStudentAndCourseOrderByCreatedDateDesc(StudentEntity studentId, CourseEntity courseId);
    Optional<StudentCourseMarkEntity> findTop1ByStudentAndCourseOrderByCreatedDateDesc(StudentEntity studentId, CourseEntity courseId);
    List<StudentCourseMarkEntity> findTop3ByStudentOrderByMarkDesc(StudentEntity studentId);
    Optional<StudentCourseMarkEntity> findTopByStudentOrderByCreatedDateAsc(StudentEntity id);
    Optional<StudentCourseMarkEntity> findTopByStudentAndCourseOrderByCreatedDateAsc(StudentEntity id,CourseEntity courseId);
    Optional<StudentCourseMarkEntity> findTop1ByStudentAndCourseOrderByMarkDesc(StudentEntity studentId,CourseEntity courseId);
    Optional<StudentCourseMarkEntity>findTop1ByCourseOrderByMarkDesc(CourseEntity courseId);

    @Query("from StudentCourseMarkEntity s where s.student.id=?1")
    Page<StudentCourseMarkEntity>findByStudentId(Pageable pageable ,StudentEntity id);

}
