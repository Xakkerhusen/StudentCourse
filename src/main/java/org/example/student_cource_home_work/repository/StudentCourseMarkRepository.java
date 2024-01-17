package org.example.student_cource_home_work.repository;

import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentCourseMarkEntity;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.example.student_cource_home_work.service.StudentCourseMarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer>, PagingAndSortingRepository<StudentCourseMarkEntity, Integer> {
    @Query("from StudentCourseMarkEntity s where s.student.id=?1 and s.createdDate between ?2 and ?3")
    List<StudentCourseMarkEntity> findByStudentAndCreatedDateBetween(Integer id, LocalDateTime localDateTime, LocalDateTime localDateTime2);
    @Query("from StudentCourseMarkEntity s where s.student.id=?1 order by s.mark desc ")
    List<StudentCourseMarkEntity> findByStudentOrderByMarkDesc(Integer id);
    @Query("from StudentCourseMarkEntity s where s.student.id=?1 and s.course.id=?2 order by s.createdDate desc ")
    List<StudentCourseMarkEntity> findByStudentAndCourseOrderByCreatedDateDesc(Integer studentId, Integer courseId);
    @Query("from StudentCourseMarkEntity s where s.student.id=?1 and s.course.id=?2 order by s.createdDate desc limit 1")
    Optional<StudentCourseMarkEntity> findTop1ByStudentAndCourseOrderByCreatedDateDesc(Integer studentId, Integer courseId);
    @Query("from StudentCourseMarkEntity s where s.student.id=?1 order by s.mark desc limit 3")
    List<StudentCourseMarkEntity> findTop3ByStudentOrderByMarkDesc(Integer studentId);
    @Query("from StudentCourseMarkEntity s where s.student.id=?1 order by s.createdDate desc limit 1")
    Optional<StudentCourseMarkEntity> findByStudentOrderByCreatedDateAsc(Integer id);
    @Query("from StudentCourseMarkEntity s where s.student.id=?1 and s.course.id=?2 order by s.createdDate desc limit 1")
    Optional<StudentCourseMarkEntity> findByStudentAndCourseOrderByCreatedDateAsc(Integer id,Integer courseId);
    @Query("from StudentCourseMarkEntity s where s.student.id=?1 and s.course.id=?2 order by s.createdDate desc limit 1")
    Optional<StudentCourseMarkEntity> findByStudentAndCourseOrderByMarkDesc(Integer studentId,Integer courseId);
    @Query("from StudentCourseMarkEntity s where s.course.id=?1 order by s.mark desc limit 1")
    Optional<StudentCourseMarkEntity>findByCourseOrderByMarkDesc(Integer courseId);
    @Query("from StudentCourseMarkEntity s where s.student.id=?1")
    Page<StudentCourseMarkEntity> findAllByStudentId(Pageable pageable, Integer studentId);
    @Query("from StudentCourseMarkEntity s where s.course.id=?1")
    Page<StudentCourseMarkEntity> findAllByCourseId(Pageable pageable, Integer courseId);
}
