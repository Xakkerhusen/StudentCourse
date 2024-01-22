package org.example.student_cource_home_work.controller;


import org.example.student_cource_home_work.dto.Filter;
import org.example.student_cource_home_work.dto.Student;
import org.example.student_cource_home_work.enums.Gender;
import org.example.student_cource_home_work.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("")  //  POST /student
    public ResponseEntity<?> create(@RequestBody Student dto) {
        Student student = studentService.create(dto);
        return ResponseEntity.ok(student); // 200
    }

    @CrossOrigin(origins = "*")
    @GetMapping("") // GET /student
    public ResponseEntity<List<Student>> all() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody Student dto) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @DeleteMapping("/{id}") // DELETE /student/1
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentService.delete(id));
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Student>> getByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(studentService.getByName(name));
    }

    @GetMapping("/bySurname")
    public ResponseEntity<List<Student>> getBySurname(@RequestParam("surname") String surname) {
        return ResponseEntity.ok(studentService.getBySurname(surname));
    }

    @GetMapping("/byAge")
    public ResponseEntity<List<Student>> getByAge(@RequestParam("age") Integer age) {
        return ResponseEntity.ok(studentService.getByAge(age));
    }

    @GetMapping("/byGender")
    public ResponseEntity<List<Student>> getByGender(@RequestParam("gender") Gender gender) {
        return ResponseEntity.ok(studentService.getByGender(gender));
    }

    @GetMapping("/byLevel")
    public ResponseEntity<List<Student>> getByLevel(@RequestParam("level") Integer level) {
        return ResponseEntity.ok(studentService.getByLevel(level));
    }

    @GetMapping("/byCreatedDate")//7
    public ResponseEntity<List<Student>> getCreatedDate(@RequestParam("createdDate") LocalDate createdDate) {
        return ResponseEntity.ok(studentService.getByCreatedDate(createdDate));
    }

    @GetMapping("/byCreatedDateBetween")//8
    public ResponseEntity<List<Student>> getCreatedDateBetween(@RequestParam("createdDate1") LocalDate fromCreatedDate,
                                                               @RequestParam("createdDate2") LocalDate toCreatedDate) {
        return ResponseEntity.ok(studentService.getByCreatedDateBetween(fromCreatedDate, toCreatedDate));
    }

    @GetMapping("/getPaginationByLevel")//10
    public ResponseEntity<PageImpl<Student>> getPaginationByLevel(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                  @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                                  @RequestParam("level") Integer level) {
        return ResponseEntity.ok(studentService.getPaginationByLevel(page, size, level));
    }

    @GetMapping("/getPaginationByGender")//11
    public ResponseEntity<PageImpl<Student>> getPaginationByGender(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                   @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                                   @RequestParam(value = "gender") String gender) {
        return ResponseEntity.ok(studentService.getPaginationByGender(page, size, gender));
    }

    @PostMapping("/studentFilter")//12
    public ResponseEntity<PageImpl<?>> studentFilter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "2") Integer size,
                                                           @RequestBody Filter filter) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC,"createdDate"));
        return ResponseEntity.ok(studentService.studentFilter(filter,pageable));
    }


}
