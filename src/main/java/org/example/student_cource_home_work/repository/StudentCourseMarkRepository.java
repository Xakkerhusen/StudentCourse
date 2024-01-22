package org.example.student_cource_home_work.repository;

import org.example.student_cource_home_work.entity.StudentCourseMarkEntity;
import org.example.student_cource_home_work.mapper.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
    Optional<StudentCourseMarkEntity> findByStudentAndCourseOrderByCreatedDateAsc(Integer id, Integer courseId);

    @Query("from StudentCourseMarkEntity s where s.student.id=?1 and s.course.id=?2 order by s.createdDate desc limit 1")
    Optional<StudentCourseMarkEntity> findByStudentAndCourseOrderByMarkDesc(Integer studentId, Integer courseId);

    @Query("from StudentCourseMarkEntity s where s.course.id=?1 order by s.mark desc limit 1")
    Optional<StudentCourseMarkEntity> findByCourseOrderByMarkDesc(Integer courseId);

    @Query("from StudentCourseMarkEntity s where s.student.id=?1")
    Page<StudentCourseMarkEntity> findAllByStudentId(Pageable pageable, Integer studentId);

    @Query("from StudentCourseMarkEntity s where s.course.id=?1")
    Page<StudentCourseMarkEntity> findAllByCourseId(Pageable pageable, Integer courseId);

    @Query(value = "select scm.id,scm.mark,scm.created_date,c.id,c.name,c.duration from  course c  " +
            "inner join student_course_mark scm on c.id=scm.id where student_id=?1", nativeQuery = true)
    List<Mapper> getByStudentId(Integer id);

    @Query(value = "select scm.id,scm.mark,scm.created_date,s.id,s.name,s.surname from  student s  " +
            "inner join student_course_mark scm on s.id=scm.id where course_id=?1", nativeQuery = true)
    List<Mapper> getByCourseId(Integer id);


 @Query(value = "select scm.id,scm.mark,scm.created_date,s.id,s.name,s.surname,c.id,c.price from  student s  " +
         "inner join student_course_mark scm on s.id=scm.id inner join public.course c on scm.course_id = c.id", nativeQuery = true)
    List<Mapper> getAll();



}
