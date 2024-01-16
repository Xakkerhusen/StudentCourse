package org.example.student_cource_home_work.controller;

import org.example.student_cource_home_work.dto.Course;
import org.example.student_cource_home_work.dto.Student;
import org.example.student_cource_home_work.dto.StudentCourseMark;
import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentCourseMarkEntity;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.example.student_cource_home_work.enums.Gender;
import org.example.student_cource_home_work.service.StudentCourseMarkService;
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
import java.util.Optional;

@RestController
@RequestMapping("student_course_mark")
public class StudentCourseMarkController {
    @Autowired
    private StudentCourseMarkService studentCourseMarkService;
    @PostMapping("")  //  POST /student
    public ResponseEntity<?> create(@RequestBody StudentCourseMark dto) {
        StudentCourseMark student = studentCourseMarkService.create(dto);
        return ResponseEntity.ok(student); // 200
    }
    @CrossOrigin(origins = "*")
    @GetMapping("") // GET /student
    public ResponseEntity<List<StudentCourseMark>> all() {
        return ResponseEntity.ok(studentCourseMarkService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentCourseMark> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getById(id));
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<StudentCourseMark> getByIdDetails(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.getByIdDetails(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody StudentCourseMark dto) {
        return ResponseEntity.ok(studentCourseMarkService.update(id, dto));
    }
    @DeleteMapping("/{id}") // DELETE /student/1
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentCourseMarkService.delete(id));
    }
    @GetMapping("/getMark")//7
    public ResponseEntity<List<StudentCourseMark>> getMarkByCreatedDate(@RequestParam("created_date") LocalDate time,
                                                                        @RequestParam("id") StudentEntity id) {
        return ResponseEntity.ok(studentCourseMarkService.getByMark(time, id));
    }
    @GetMapping("/getMarkBetween")//8
    public ResponseEntity<List<StudentCourseMark>> getMarkByCreatedDateBetween(@RequestParam("id") StudentEntity dto,
                                                                               @RequestParam("from") LocalDate from,
                                                                               @RequestParam("to") LocalDate to) {
        return ResponseEntity.ok(studentCourseMarkService.getMarkBetween(dto, from, to));
    }
    @GetMapping("/getMarkDesc")//9
    public ResponseEntity<List<StudentCourseMark>> getMarkDesc(@RequestParam("id") StudentEntity id) {
        return ResponseEntity.ok(studentCourseMarkService.getMarkDesc(id));
    }
    @GetMapping("/getMarkCreatedDateDesc2")//10
    public ResponseEntity<List<StudentCourseMark>> getMarkCreatedDateDesc2(@RequestParam("studentId") StudentEntity studentId,
                                                                           @RequestParam("couseId") CourseEntity couseId) {
        return ResponseEntity.ok(studentCourseMarkService.getMarkByCreatedDesc2(studentId, couseId));
    }
    @GetMapping("/getCourseName")//11
    public ResponseEntity<StudentCourseMark> getCourseName(@RequestParam("studentId") StudentEntity studentId,
                                                           @RequestParam("couseId") CourseEntity couseId) {
        return ResponseEntity.ok(studentCourseMarkService.getCourseName(studentId, couseId));
    }
    @GetMapping("/getMarkLimit3")//12
    public ResponseEntity<List<StudentCourseMark>> getMarkLimit3(@RequestParam("studentId") StudentEntity studentId) {
        return ResponseEntity.ok(studentCourseMarkService.getMarkLimit3(studentId));
    }

    @GetMapping("/getFirstMark")//13
    public ResponseEntity<StudentCourseMark> getFirstMark(@RequestParam("studentId") StudentEntity studentId) {
        return ResponseEntity.ok(studentCourseMarkService.getFirstMark(studentId));
    }
    @GetMapping("/getFirstMark2")//14
    public ResponseEntity<StudentCourseMark> getFirstMark2(@RequestParam("studentId") StudentEntity studentId,
                                                           @RequestParam("courseId") CourseEntity courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getFirstMark2(studentId, courseId));
    }
    @GetMapping("/getMaxMark")//15
    public ResponseEntity<StudentCourseMark> getMaxMark(@RequestParam("studentId") StudentEntity studentId,
                                                        @RequestParam("courseId") CourseEntity courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getMaxMark(studentId, courseId));
    }
    @GetMapping("/getAverageMark")//16
    public ResponseEntity<Double> getAverageMark(@RequestParam("studentId") StudentEntity studentId) {
        return ResponseEntity.ok(studentCourseMarkService.getAverageMark(studentId));
    }
    @GetMapping("/getAverageMark2")//17
    public ResponseEntity<Double> getAverageMark(@RequestParam("studentId") StudentEntity studentId,
                                                 @RequestParam("courseId") CourseEntity courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getAverageMark2(studentId,courseId));
    }
    @GetMapping("/getGreaterThanAScoreCount")//18
    public ResponseEntity<Integer> getGreaterThanAScoreCount(@RequestParam("studentId") StudentEntity studentId,
                                                 @RequestParam("mark") Double mark) {
        return ResponseEntity.ok(studentCourseMarkService.getGreaterThanAScoreCount(studentId,mark));
    }
    @GetMapping("/getHighestGradeInTheCourse")//19
    public ResponseEntity<Integer> getHighestGradeInTheCourse(@RequestParam("courseId") CourseEntity courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getHighestGradeInTheCourse(courseId));
    }

    @GetMapping ("/getAverageGradeOfTheCourse")//20
    public ResponseEntity<Double> getAverageGradeOfTheCourse(@RequestParam("courseId") CourseEntity courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getAverageGradeOfTheCourse(courseId));
    }

    @GetMapping("/getNumberOfGradesInTheCourse")//21
    public ResponseEntity<Integer> getNumberOfGradesInTheCourse(@RequestParam("courseId") CourseEntity courseId) {
        return ResponseEntity.ok(studentCourseMarkService.getNumberOfGradesInTheCourse(courseId));
    }
    @GetMapping("getCoursePaginationById")//11
    public ResponseEntity<PageImpl<StudentCourseMark>> getCoursePaginationById(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                                       @RequestParam(value = "size",defaultValue = "2") Integer size,
                                                                       @RequestParam("studentId") StudentEntity studentId){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return ResponseEntity.ok(studentCourseMarkService.getCoursePaginationById(pageable,studentId));
    }

//    @GetMapping("/bySurname")
//    public ResponseEntity<List<Student>> getBySurname(@RequestParam("surname") String surname) {
//        return ResponseEntity.ok( studentService.getBySurname(surname));
//    }
//    @GetMapping("/byAge")
//    public ResponseEntity<List<Student>> getByAge(@RequestParam("age") Integer age) {
//        return ResponseEntity.ok( studentService.getByAge(age));
//    }
//    @GetMapping("/byGender")
//    public ResponseEntity<List<Student>> getByGender(@RequestParam("gender") Gender gender) {
//        return ResponseEntity.ok( studentService.getByGender(gender));
//    }
//    @GetMapping("/byLevel")
//    public ResponseEntity<List<Student>> getByLevel(@RequestParam("level") Integer level) {
//        return ResponseEntity.ok( studentService.getByLevel(level));
//    }
//    @GetMapping("/byCreatedDate")
//    public ResponseEntity<List<Student>> getCreatedDate(@RequestParam("createdDate") LocalDateTime createdDate) {
//        return ResponseEntity.ok( studentService.getByCreatedDate(createdDate));
//    }
//    @GetMapping("/byCreatedDateBetween")
//    public ResponseEntity<List<Student>> getCreatedDateBetween(@RequestParam("createdDate") LocalDateTime fromCreatedDate,
//                                                               @RequestParam("createdDate") LocalDateTime toCreatedDate) {
//        return ResponseEntity.ok( studentService.getByCreatedDateBetween(fromCreatedDate,toCreatedDate));
//    }

}
