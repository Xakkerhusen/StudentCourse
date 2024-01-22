package org.example.student_cource_home_work.repository;


import org.example.student_cource_home_work.dto.Course;
import org.example.student_cource_home_work.dto.Student;
import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.example.student_cource_home_work.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface StudentRepository extends CrudRepository<StudentEntity, Integer>, PagingAndSortingRepository<StudentEntity, Integer> {
    @Query("from StudentEntity s where s.name=?1")
    List<StudentEntity> findByNameQuery(String name);

    @Query("from StudentEntity s where s.surname=?1")
    List<StudentEntity> findBySurnameQuery(String surName);

    @Query("from StudentEntity s where s.age=?1")
    List<StudentEntity> findByAgeQuery(Integer age);

    @Query("from StudentEntity s where s.gender=?1")
    List<StudentEntity> findByGenderQuery(Gender gender);

    @Query("from StudentEntity s where s.level=?1")
    List<StudentEntity> findByLevelQuery(Integer level);

    @Query("from StudentEntity s where s.createdDate between ?1 and ?2")
    List<StudentEntity> findByCreatedDateBetweenQuery(LocalDateTime fromDate, LocalDateTime toDate);

    @Query("from StudentEntity where level=?1")
    Page<StudentEntity> findByLevelQuery(Integer level, Pageable pageable);
    @Query("from StudentEntity where gender =?1")
    Page<StudentEntity> findByGenderQuery(Gender gender, Pageable pageable);



}
