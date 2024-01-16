package org.example.student_cource_home_work.repository;

import org.example.student_cource_home_work.dto.Course;
import org.example.student_cource_home_work.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<CourseEntity, Integer>, PagingAndSortingRepository<CourseEntity, Integer> {
    List<CourseEntity> findByName(String name);
    List<CourseEntity> findByDuration(String duration);
    List<CourseEntity> findByPrice(Double price);
    List<CourseEntity> findByCreatedDateBetween(LocalDateTime fromCreatedDate, LocalDateTime toCreatedDate);
    List<CourseEntity> findByPriceBetween(Double fromPrice, Double toPrice);
    Page<CourseEntity>findByPrice(Pageable pageable,Integer price);


}
