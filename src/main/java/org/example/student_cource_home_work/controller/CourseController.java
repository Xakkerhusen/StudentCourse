package org.example.student_cource_home_work.controller;

import org.example.student_cource_home_work.dto.Course;
import org.example.student_cource_home_work.dto.Student;
import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.enums.Gender;
import org.example.student_cource_home_work.service.CourseService;
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
import java.util.LinkedList;
import java.util.List;
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("")  //  POST /student
    public ResponseEntity<?> create(@RequestBody Course dto) {
        Course course = courseService.create(dto);
        return ResponseEntity.ok(course); // 200
    }

    @CrossOrigin(origins = "*")
    @GetMapping("") // GET /student
    public ResponseEntity<List<Course>> all() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody Course dto) {
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @DeleteMapping("/{id}") // DELETE /student/1
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(courseService.delete(id));
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Course>> getByName(@RequestParam("name") String name) {
        return ResponseEntity.ok( courseService.getByName(name));
    }
    @GetMapping("/byDuration")
    public ResponseEntity<List<Course>> getByDuration(@RequestParam("duration") String duration) {
        return ResponseEntity.ok( courseService.getByDuration(duration));
    }
    @GetMapping("/byPrice")
    public ResponseEntity<List<Course>> getByPrice(@RequestParam("price") Double price) {
        return ResponseEntity.ok( courseService.getByPrice(price));
    }

    @GetMapping("/byCreatedDateBetween")//8
    public ResponseEntity<List<Course>> getCreatedDateBetween(@RequestParam("createdDate1") LocalDateTime fromCreatedDate,
                                                               @RequestParam("createdDate2") LocalDateTime toCreatedDate) {
        return ResponseEntity.ok( courseService.getByCreatedDateBetween(fromCreatedDate,toCreatedDate));
    }

    @GetMapping("/byPriceBetween")//7
    public ResponseEntity<List<Course>> getByPriceBetween(@RequestParam("price1") Double fromPrice,
                                                              @RequestParam("price2") Double toPrice) {
        return ResponseEntity.ok( courseService.getByPriceBetween(fromPrice,toPrice));
    }
    @GetMapping("getCoursePaginationSortCreatedDate")//10
    public ResponseEntity<PageImpl<Course>> getCoursePaginationSortCreatedDate(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                             @RequestParam(value = "size",defaultValue = "2") Integer size){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return ResponseEntity.ok(courseService.getCoursePaginationSortCreatedDate(pageable));
    }
    @GetMapping("getCoursePaginationByPrice")//11
    public ResponseEntity<PageImpl<Course>> getCoursePaginationByPrice(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                               @RequestParam(value = "size",defaultValue = "2") Integer size,
                                                                       @RequestParam("price") Integer price){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return ResponseEntity.ok(courseService.getCoursePaginationByPrice(pageable,price));
    }
}
