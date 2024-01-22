package org.example.student_cource_home_work.service;


import org.example.student_cource_home_work.dto.Filter;
import org.example.student_cource_home_work.dto.PaginationResultDTO;
import org.example.student_cource_home_work.dto.Student;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.example.student_cource_home_work.enums.Gender;
import org.example.student_cource_home_work.exp.AppBadException;
import org.example.student_cource_home_work.repository.CustomRepository;
import org.example.student_cource_home_work.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CustomRepository customRepository;

    public Student create(Student dto) {
        if (dto.getName() == null || dto.getName().trim().length() < 3) {
            throw new AppBadException("Student name required");
        }
        if (dto.getSurname() == null || dto.getSurname().trim().length() < 3) {
            throw new AppBadException("Student surname required");
        }
        if (dto.getAge() == null || dto.getAge() < 0) {
            throw new AppBadException("Student age required");
        }
        if (dto.getPhone() == null || dto.getPhone().trim().length() < 3) {
            throw new AppBadException("Student phone required");
        }
        if (dto.getLevel() == null) {
            throw new AppBadException("Student level required");
        }
        StudentEntity entity = new StudentEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setPhone(dto.getPhone());
        entity.setLevel(dto.getLevel());
        entity.setGender(dto.getGender());
        entity.setCreatedDate(LocalDateTime.now());
        studentRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(LocalDateTime.now());
        return dto;
    }

    public List<Student> getAll() {
        Iterable<StudentEntity> entityList = studentRepository.findAll();
        List<Student> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            Student dto = new Student();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setAge(entity.getAge());
            dto.setPhone(entity.getPhone());
            dto.setLevel(entity.getLevel());
            dto.setGender(entity.getGender());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean update(Integer id, Student dto) { // name, surname
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        StudentEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        studentRepository.save(entity); //  update for all fields
        return true;
    }

    public boolean delete(Integer id) { // name, surname
      /*  Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        studentRepository.delete(optional.get());  // 1-usul */

        studentRepository.deleteById(id); // 2-usul

       /* StudentEntity entity = new StudentEntity();
        entity.setId(id);
        studentRepository.delete(entity); // 3-usul */
        return true;
    }

    public Student getById(Integer id) {

        StudentEntity entity = get(id);
//        Student dto = new Student();
//        dto.setId(entity.getId());
//        dto.setName(entity.getName());
//        dto.setSurname(entity.getSurname());
//        dto.setAge(entity.getAge());
//        dto.setPhone(entity.getPhone());
//        entity.setGender(dto.getGender());
//        dto.setCreatedDate(entity.getCreatedDate());
//        return dto;
        return toDTO(entity);
    }

    public List<Student> getByName(String name) {
        List<StudentEntity> entityList = studentRepository.findByNameQuery(name);
        List<Student> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<Student> getBySurname(String surname) {
        List<StudentEntity> entityList = studentRepository.findBySurnameQuery(surname);
        List<Student> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<Student> getByAge(Integer age) {
        List<StudentEntity> entityList = studentRepository.findByAgeQuery(age);
        List<Student> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public List<Student> getByGender(Gender gender) {
        List<StudentEntity> entityList = studentRepository.findByGenderQuery(gender);
        List<Student> studentList = new LinkedList<>();
        for (StudentEntity studentEntity : entityList) {
            studentList.add(toDTO(studentEntity));
        }
        return studentList;
    }

    public List<Student> getByLevel(Integer level) {
        List<StudentEntity> entityList = studentRepository.findByLevelQuery(level);
        List<Student> studentList = new LinkedList<>();
        for (StudentEntity studentEntity : entityList) {
            studentList.add(toDTO(studentEntity));
        }
        return studentList;
    }

    public List<Student> getByCreatedDate(LocalDate createdDate) {
        LocalDateTime from = LocalDateTime.of(createdDate, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(createdDate, LocalTime.MAX);
        List<StudentEntity> entityList = studentRepository.findByCreatedDateBetweenQuery(from, to);
        List<Student> studentList = new LinkedList<>();
        for (StudentEntity studentEntity : entityList) {
            studentList.add(toDTO(studentEntity));
        }
        return studentList;
    }

    public List<Student> getByCreatedDateBetween(LocalDate fromCreatedDate, LocalDate toCreatedDate) {
        LocalDateTime from = LocalDateTime.of(fromCreatedDate, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(toCreatedDate, LocalTime.MAX);
        List<StudentEntity> entityList = studentRepository.findByCreatedDateBetweenQuery(from, to);
        List<Student> studentList = new LinkedList<>();
        for (StudentEntity studentEntity : entityList) {
            studentList.add(toDTO(studentEntity));
        }
        return studentList;
    }

    public PageImpl<Student> getPaginationByLevel(Integer page, Integer size, Integer level) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<StudentEntity> studentPage = studentRepository.findByLevelQuery(level, pageable);

        List<StudentEntity> content = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<Student> dtoList = new LinkedList<>();
        for (StudentEntity entity : content) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public PageImpl<Student> getPaginationByGender(Integer page, Integer size, String gender) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Gender gender1 = Gender.valueOf(gender.toUpperCase());
        Page<StudentEntity> byGender = studentRepository.findByGenderQuery(gender1, pageable);
        List<StudentEntity> content = byGender.getContent();
        long totalElements = byGender.getTotalElements();
        List<Student> dtoList = new LinkedList<>();
        for (StudentEntity entity : content) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

//    public Student getByPhone(String phone) {
////        Optional<StudentEntity> byPhone = studentRepository.findByPhone(phone);
////        return toDTO(byPhone.get());
//    }


    public Student toDTO(StudentEntity entity) {
        Student dto = new Student();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAge(entity.getAge());
        dto.setPhone(entity.getPhone());
        dto.setLevel(entity.getLevel());
        dto.setGender(entity.getGender());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public StudentEntity get(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        return optional.get();
    }


    public PageImpl<Student> studentFilter(Filter filter, Pageable pageable) {
        PaginationResultDTO<StudentEntity> filter1 = customRepository.studentFilter(filter, pageable);

        List<Student> studentList=new LinkedList<>();
        for (StudentEntity entity : filter1.getList()) {
            studentList.add(toDTO(entity));
        }
        return new PageImpl<>(studentList,pageable,filter1.getTotalSize());
    }
}


