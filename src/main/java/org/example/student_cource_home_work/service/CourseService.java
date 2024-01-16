package org.example.student_cource_home_work.service;

import org.example.student_cource_home_work.dto.Course;
import org.example.student_cource_home_work.dto.Student;
import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.example.student_cource_home_work.enums.Gender;
import org.example.student_cource_home_work.exp.AppBadException;
import org.example.student_cource_home_work.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course create(Course dto) {
        if (dto.getName() == null || dto.getName().trim().length() < 3) {
            throw new AppBadException("Course name required");
        }
        if (dto.getDuration() == null) {
            throw new AppBadException("Student surname required");
        }
        if (dto.getPrice() == null || dto.getPrice() < 0) {
            throw new AppBadException("Student age required");
        }

        CourseEntity entity = new CourseEntity();
        entity.setName(dto.getName());
        entity.setDuration(dto.getDuration());
        entity.setPrice(dto.getPrice());
        entity.setCreatedDate(LocalDateTime.now());
        courseRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }

    public List<Course> getAll() {
        Iterable<CourseEntity> entityList = courseRepository.findAll();
        List<Course> dtoList = new LinkedList<>();
        for (CourseEntity entity : entityList) {
            Course dto = new Course();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDuration(entity.getDuration());
            dto.setPrice(entity.getPrice());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean update(Integer id, Course dto) { // name, surname
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Course not found");
        }
        CourseEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        courseRepository.save(entity); //  update for all fields
        return true;
    }

    public boolean delete(Integer id) { // name, surname
      /*  Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Course not found");
        }
        courseRepository.delete(optional.get());  // 1-usul */

        courseRepository.deleteById(id); // 2-usul

       /* CourseEntity entity = new StudentEntity();
        entity.setId(id);
        courseRepository.delete(entity); // 3-usul */
        return true;
    }

    public Course getById(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        CourseEntity entity = optional.get();
//        Course dto = new Course();
//       dto.setId(entity.getId());
//        dto.setName(entity.getName());
//        dto.setPrice(entity.getPrice());
//        dto.setDuration(entity.getDuration());
//        return dto;
        return toDTO(entity);
    }

    public List<Course> getByName(String name) {
        List<CourseEntity> entityList = courseRepository.findByName(name);
        List<Course> dtoList = new LinkedList<>();
        for (CourseEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<Course> getByDuration(String duration) {
        List<CourseEntity> entityList = courseRepository.findByDuration(duration);
        List<Course> dtoList = new LinkedList<>();
        for (CourseEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<Course> getByPrice(Double price) {
        List<CourseEntity> entityList = courseRepository.findByPrice(price);
        List<Course> dtoList = new LinkedList<>();
        for (CourseEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<Course> getByCreatedDateBetween(LocalDateTime fromCreatedDate, LocalDateTime toCreatedDate) {
        List<CourseEntity> entityList = courseRepository.findByCreatedDateBetween(fromCreatedDate, toCreatedDate);
        List<Course> studentList = new LinkedList<>();
        for (CourseEntity studentEntity : entityList) {
            studentList.add(toDTO(studentEntity));
        }
        return studentList;
    }

    public List<Course> getByPriceBetween(Double fromPrice, Double toPrice) {
        List<CourseEntity> entityList = courseRepository.findByPriceBetween(fromPrice, toPrice);
        List<Course> studentList = new LinkedList<>();
        for (CourseEntity studentEntity : entityList) {
            studentList.add(toDTO(studentEntity));
        }
        return studentList;
    }

    public PageImpl<Course> getCoursePaginationSortCreatedDate(Pageable pageable) {

        Page<CourseEntity> coursePage = courseRepository.findAll(pageable);
        List<Course> dtoList = new LinkedList<>();
        List<CourseEntity> content = coursePage.getContent();
        long totalElements = coursePage.getTotalElements();
        for (CourseEntity entity : content) {
            dtoList.add(toDTO(entity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }
    public PageImpl<Course> getCoursePaginationByPrice(Pageable pageable, Integer price) {
        Page<CourseEntity> byPrice = courseRepository.findByPrice(pageable, price);
        List<Course>dtoList=new LinkedList<>();
        List<CourseEntity> content = byPrice.getContent();
        long totalElements = byPrice.getTotalElements();
        for (CourseEntity entity : content) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList,pageable,totalElements);
    }

    public Course toDTO(CourseEntity entity) {
        Course dto = new Course();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        return dto;
    }

    public CourseEntity get(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Course not found");
        }
        return optional.get();
    }



}
