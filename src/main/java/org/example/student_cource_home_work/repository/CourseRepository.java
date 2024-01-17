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
   @Query("from CourseEntity where name=?1")
    List<CourseEntity> findByNameQuery(String name);
   @Query("from CourseEntity where duration=?1")
    List<CourseEntity> findByDurationQuery(String duration);
   @Query("from CourseEntity where price=?1")
    List<CourseEntity> findByPriceQuery(Double price);
   @Query("from CourseEntity where createdDate between ?1 and ?2")
    List<CourseEntity> findByCreatedDateBetweenQuery(LocalDateTime fromCreatedDate, LocalDateTime toCreatedDate);
   @Query("from CourseEntity where price between ?1 and ?2")
    List<CourseEntity> findByPriceBetweenQuery(Double fromPrice, Double toPrice);
   @Query("from CourseEntity where price=?1 ")
    Page<CourseEntity>findByPriceQuery(Pageable pageable,Integer price);


}
