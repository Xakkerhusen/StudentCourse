package org.example.student_cource_home_work.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.student_cource_home_work.dto.Filter;
import org.example.student_cource_home_work.dto.PaginationResultDTO;
import org.example.student_cource_home_work.dto.StudentCourseMark;
import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentCourseMarkEntity;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class CustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<StudentEntity> studentFilter(Filter filter, Pageable pageable) {
    /*  id;name;surname;age;LocalDate fromDate;LocalDate toDate;Double price;
      duration;mark;studentId;courseId;Student student;Course course;*/

        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append(" and id=:id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            builder.append(" and lower(name) like :name ");
            params.put("name", "%"+filter.getName().toLowerCase()+"%");
        }
        if (filter.getSurname() != null) {
            builder.append(" and lower(surname) like :surname ");
            params.put("surname", "%"+filter.getSurname().toLowerCase()+"%");
        }
        if (filter.getAge() != null) {
            builder.append(" and age=:age ");
            params.put("age", filter.getAge());
        }
        if (filter.getPhone() != null) {
            builder.append(" and phone=:phone ");
            params.put("phone", filter.getPhone());
        }
        if (filter.getLevel() != null) {
            builder.append(" and level=:level ");
            params.put("level", filter.getLevel());
        }
        if (filter.getGender() != null) {
            builder.append(" and upper(gender) = :gender ");
            params.put("gender", filter.getGender().toString().toUpperCase());
        }if (filter.getFromDate() != null && filter.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append(" and createdDate  between :fromDate and :toDate");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MAX);
            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getToDate() != null) {
            builder.append(" and createdDate <= :toDate");
            params.put("toDate", filter.getToDate());
        }

        StringBuilder selectBuilder = new StringBuilder(" from StudentEntity s where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc ");
        StringBuilder countBuilder = new StringBuilder(" select count(s) from StudentEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(pageable.getPageSize());
        selectQuery.setFirstResult((int) pageable.getOffset());

        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(),param.getValue());
            countQuery.setParameter(param.getKey(),param.getValue());
        }
        List <StudentEntity> entityList = selectQuery.getResultList();
        Long totalElements=(Long)countQuery.getSingleResult();

        return new PaginationResultDTO<>(totalElements,entityList);
    }

    public PaginationResultDTO<CourseEntity> courseFilter(Filter filter, Pageable pageable) {
    /*  id;name;surname;age;LocalDate fromDate;LocalDate toDate;Double price;
      duration;mark;studentId;courseId;Student student;Course course;*/

        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append(" and id=:id ");
            params.put("id", filter.getId());
        }
        if (filter.getName() != null) {
            builder.append(" and lower(name) like :name ");
            params.put("name", "%"+filter.getName().toLowerCase()+"%");
        }
        if (filter.getPrice() != null) {
            builder.append(" and price=:price ");
            params.put("price", filter.getPrice());
        }
        if (filter.getDuration() != null) {
            builder.append(" and duration=:duration ");
            params.put("duration", filter.getDuration());
        }if (filter.getFromDate() != null && filter.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append(" and createdDate  between :fromDate and :toDate");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MAX);
            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getToDate() != null) {
            builder.append(" and createdDate <= :toDate");
            params.put("toDate", filter.getToDate());
        }

        StringBuilder selectBuilder = new StringBuilder(" from CourseEntity s where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc ");
        StringBuilder countBuilder = new StringBuilder(" select count(s) from CourseEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(pageable.getPageSize());
        selectQuery.setFirstResult((int) pageable.getOffset());

        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(),param.getValue());
            countQuery.setParameter(param.getKey(),param.getValue());
        }
        List <CourseEntity> entityList = selectQuery.getResultList();
        Long totalElements=(Long)countQuery.getSingleResult();

        return new PaginationResultDTO<>(totalElements,entityList);
    }

    public PaginationResultDTO<StudentCourseMarkEntity> studentCourseFilter(Filter filter, Pageable pageable) {
        StringBuilder builder = new StringBuilder();
        Map<String,Object> params=new HashMap<>();
        if (filter.getId() != null) {
            builder.append(" and id=:id ");
            params.put("id",filter.getId());
        }
        if (filter.getStudentId() != null) {
            builder.append(" and scm.student.id=:studentId ");
            params.put("studentId",filter.getStudentId());
        }
        if (filter.getCourseId() != null) {
            builder.append(" and scm.course.id=:courseId ");
            params.put("courseId",filter.getCourseId());
        }
        if (filter.getMarkFrom() != null&&filter.getMarkTo()!=null) {
            builder.append(" and mark between :markFrom and :markTo ");
            params.put("markFrom",filter.getMarkFrom());
            params.put("markTo",filter.getMarkTo());
        }else if (filter.getMarkFrom()!=null){
            builder.append(" and mark=:markFrom ");
            params.put("markFrom",filter.getMarkFrom());
        } else if (filter.getMarkTo()!=null) {
            builder.append(" and mark <=:markTo ");
            params.put("markTo",filter.getMarkTo());
        }else if (filter.getFromDate() != null && filter.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append(" and createdDate  between :fromDate and :toDate");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MAX);
            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getToDate() != null) {
            builder.append(" and createdDate <= :toDate");
            params.put("toDate", filter.getToDate());
        }

        StringBuilder selectBuilder = new StringBuilder(" from StudentCourseMarkEntity scm where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc ");
        StringBuilder countBuilder = new StringBuilder("select count(scm) from StudentCourseMarkEntity scm where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(pageable.getPageSize());
        selectQuery.setFirstResult((int) pageable.getOffset());

        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(),param.getValue());
            countQuery.setParameter(param.getKey(),param.getValue());
        }

        List<StudentCourseMarkEntity> entity=selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<>(totalElements, entity);

    }

}
