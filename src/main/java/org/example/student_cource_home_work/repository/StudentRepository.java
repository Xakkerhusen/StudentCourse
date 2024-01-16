package org.example.student_cource_home_work.repository;



import org.example.student_cource_home_work.dto.Course;
import org.example.student_cource_home_work.dto.Student;
import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.example.student_cource_home_work.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface StudentRepository extends CrudRepository<StudentEntity,Integer>, PagingAndSortingRepository<StudentEntity, Integer> {
   List<StudentEntity> findByName(String name);
    List<StudentEntity> findBySurname(String surName);
    List<StudentEntity> findByAge(Integer age);
    List<StudentEntity> findByGender(Gender age);
    List<StudentEntity> findByLevel(Integer level);
    List<StudentEntity> findByCreatedDate(LocalDateTime fromDate);
    List<StudentEntity> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
    Page<StudentEntity> findByLevel(Integer level, Pageable pageable);
    Page<StudentEntity> findByGender(Gender gender, Pageable pageable);


   /*  Optional<StudentEntity> findByPhone(String phone);
    List<StudentEntity> findByNameAndSurnameAndPhone(String name, String surname, String phone);
    //   select * from student where name = ? and surname = ? and phone = ?

    List<StudentEntity> findByNameOrSurname(String name, String surname);
    //   select * from student where name = ? or surname = ?

    List<StudentEntity> findByAgeBetween(Integer fromAge, Integer toAge);
    //   select * from student where age between ? and ?

    List<StudentEntity> findByAgeLessThanAndName(Integer age, String name);
    //   select * from student where age < ? and name = ?

    List<StudentEntity> findByCreatedDateAfter(LocalDateTime fromDate);
    //   select * from student where created_date > ?

    List<StudentEntity> findByPhoneIsNull();
    //   select * from student where phone is null

    List<StudentEntity> findByNameLike(String name);
    //   select * from student where name like ?

    List<StudentEntity> findByNameAndPhoneStartingWith(String name, String phone);
    //   select * from student where name = ? and phone %?

    List<StudentEntity> findAllByOrderByCreatedDateDesc();
    //   select * from student order by created_date desc

    List<StudentEntity> findAllByNameOrderByCreatedDateDesc(String name);
    //   select * from student where name = ? order by created_date desc

    List<StudentEntity> findByAgeIn(List<Integer> ageList);  // 22, 24, 26
    //   select * from student where age in (?)*/
/*getByName, getBySurname, getByLevel, getByAge, getByGender*/


}
