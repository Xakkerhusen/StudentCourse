package org.example.student_cource_home_work.service;

import org.example.student_cource_home_work.dto.Course;
import org.example.student_cource_home_work.dto.Student;
import org.example.student_cource_home_work.dto.StudentCourseMark;
import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentCourseMarkEntity;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.example.student_cource_home_work.exp.AppBadException;
import org.example.student_cource_home_work.record.Result;
import org.example.student_cource_home_work.repository.CourseRepository;
import org.example.student_cource_home_work.repository.StudentCourseMarkRepository;
import org.example.student_cource_home_work.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseMarkService {
    @Autowired
    private StudentCourseMarkRepository studentCourseMarkRepository;
    //    @Autowired
//    private StudentCourseMarkService studentCourseMarkService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;

    public StudentCourseMark create(StudentCourseMark dto) {
        StudentEntity student = studentService.get(dto.getStudentId());
        CourseEntity course = courseService.get(dto.getCourseId());

        StudentCourseMarkEntity studentCourseMarkEntity = new StudentCourseMarkEntity();
        studentCourseMarkEntity.setStudent(student);
        studentCourseMarkEntity.setCourse(course);
        studentCourseMarkEntity.setMark(dto.getMark());
        studentCourseMarkEntity.setCreatedDate(LocalDateTime.now());

        studentCourseMarkRepository.save(studentCourseMarkEntity);

        dto.setCreatedDate(studentCourseMarkEntity.getCreatedDate());
        dto.setId(studentCourseMarkEntity.getId());
        return dto;

    }

    public List<StudentCourseMark> getAll() {
        Iterable<StudentEntity> studentEntities = studentRepository.findAll();
        Iterable<CourseEntity> courseEntities = courseRepository.findAll();

        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findAll();
        List<StudentCourseMark> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity entity : entityList) {
            StudentCourseMark dto = new StudentCourseMark();
            dto.setId(entity.getId());
            dto.setStudentId(entity.getStudent().getId());
//            dto.setStudentId(studentEntities.iterator().next().getId());
//            dto.setCourseId(courseEntities.iterator().next().getId());
            dto.setCourseId(entity.getCourse().getId());
            dto.setMark(entity.getMark());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean update(Integer id, StudentCourseMark dto) { // name, surname
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        Optional<StudentEntity> student = studentRepository.findById(dto.getStudentId());
        Optional<CourseEntity> course = courseRepository.findById(dto.getCourseId());
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        StudentCourseMarkEntity entity = optional.get();
        entity.setMark(dto.getMark());
        entity.setStudent(student.get());
        entity.setCourse(course.get());
        studentCourseMarkRepository.save(entity); //  update for all fields
        return true;
    }

    public boolean delete(Integer id) { // name, surname
      /*  Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        studentRepository.delete(optional.get());  // 1-usul */

        studentCourseMarkRepository.deleteById(id); // 2-usul

       /* StudentCourseMarkEntity entity = new StudentCourseMarkEntity();
        entity.setId(id);
        studentCourseMarkRepository.delete(entity); // 3-usul */
        return true;
    }

    public StudentCourseMark getById(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        StudentCourseMarkEntity entity = optional.get();
        return toDTO(entity);
    }

    public StudentCourseMark getByIdDetails(Integer id) {

        StudentCourseMarkEntity entity = get(id);

        StudentCourseMark dto = new StudentCourseMark();
        dto.setId(entity.getId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());

        StudentEntity student = entity.getStudent();
        Student studentDTO = new Student();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        dto.setStudent(studentDTO);

        /// course
        CourseEntity course = entity.getCourse();
        Course courseDTO = new Course();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        dto.setCourse(courseDTO);

        return dto;
    }

    public List<StudentCourseMark> getByMark(LocalDate time, StudentEntity id) {
        LocalDateTime localDateTime = LocalDateTime.of(time, LocalTime.MIN);
        LocalDateTime localDateTime2 = LocalDateTime.of(time, LocalTime.MAX);
        StudentEntity entity = studentService.get(id.getId());
        List<StudentCourseMarkEntity> byStudentAndCreatedDateBetween = studentCourseMarkRepository.findByStudentAndCreatedDateBetween(entity, localDateTime, localDateTime2);
        List<StudentCourseMark> studentCourseMarkList = new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : byStudentAndCreatedDateBetween) {
            StudentCourseMark dto = new StudentCourseMark();
            dto.setMark(studentCourseMarkEntity.getMark());
            studentCourseMarkList.add(dto);
        }
        return studentCourseMarkList;
    }

    public List<StudentCourseMark> getMarkBetween(StudentEntity id, LocalDate from, LocalDate to) {
        LocalDateTime localDateTime1 = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime localDateTime2 = LocalDateTime.of(to, LocalTime.MAX);
        StudentEntity studentEntity = studentService.get(id.getId());
        List<StudentCourseMarkEntity> byStudentAndCreatedDateBetween = studentCourseMarkRepository.findByStudentAndCreatedDateBetween(studentEntity, localDateTime1, localDateTime2);
        List<StudentCourseMark> studentCourseMarkList = new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : byStudentAndCreatedDateBetween) {
            StudentCourseMark dto = new StudentCourseMark();
            dto.setMark(studentCourseMarkEntity.getMark());
            studentCourseMarkList.add(dto);
        }
        return studentCourseMarkList;
    }

    public List<StudentCourseMark> getMarkDesc(StudentEntity id) {
//        StudentEntity studentEntity = studentService.get(id.getId());
        StudentEntity studentEntity = new StudentEntity();
        Optional<StudentCourseMarkEntity> byId = studentCourseMarkRepository.findById(id.getId());
        if (byId.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        studentEntity.setId(byId.get().getId());
        List<StudentCourseMarkEntity> byStudentCreatedDateDesc = studentCourseMarkRepository.findByStudentOrderByMarkDesc(studentEntity);
        List<StudentCourseMark> list = new LinkedList<>();
        for (StudentCourseMarkEntity entity : byStudentCreatedDateDesc) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<StudentCourseMark> getMarkByCreatedDesc2(StudentEntity studentId, CourseEntity couseId) {
        Result result = getResult(studentId, couseId);

        List<StudentCourseMarkEntity> byStudentCreatedDateDesc =
                studentCourseMarkRepository.findByStudentAndCourseOrderByCreatedDateDesc(result.studentEntity(), result.courseEntity());
        List<StudentCourseMark> list = new LinkedList<>();
        for (StudentCourseMarkEntity entity : byStudentCreatedDateDesc) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public StudentCourseMark getCourseName(StudentEntity studentId, CourseEntity couseId) {
        Result result = getResult(studentId, couseId);
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findTop1ByStudentAndCourseOrderByCreatedDateDesc(result.studentEntity(), result.courseEntity());
        StudentCourseMark studentCourseMark = new StudentCourseMark();
        Course course = new Course();
        course.setName(optional.get().getCourse().getName());
        studentCourseMark.setCourse(course);
        studentCourseMark.setMark(optional.get().getMark());
        return studentCourseMark;
    }

    public List<StudentCourseMark> getMarkLimit3(StudentEntity studentId) {
        StudentEntity student = studentService.get(studentId.getId());
        List<StudentCourseMarkEntity> top3ByStudentOrderByMarkDesc =
                studentCourseMarkRepository.findTop3ByStudentOrderByMarkDesc(student);
        List<StudentCourseMark> studentCourseMark = new LinkedList<>();
        for (StudentCourseMarkEntity entity : top3ByStudentOrderByMarkDesc) {
            studentCourseMark.add(toDTO(entity));
        }
        return studentCourseMark;
    }

    public StudentCourseMark getFirstMark(StudentEntity studentId) {
        StudentEntity studentEntity = new StudentEntity();
        StudentCourseMarkEntity studentCourseMarkEntity = get(studentId.getId());
        studentEntity.setId(studentCourseMarkEntity.getId());
        Optional<StudentCourseMarkEntity> topByStudentOrderByCreatedDateAsc =
                studentCourseMarkRepository.findTopByStudentOrderByCreatedDateAsc(studentEntity);
        StudentCourseMark mark = new StudentCourseMark();
        mark.setMark(topByStudentOrderByCreatedDateAsc.get().getMark());
        mark.setCreatedDate(topByStudentOrderByCreatedDateAsc.get().getCreatedDate());
        return mark;
    }

    public StudentCourseMark getFirstMark2(StudentEntity studentId, CourseEntity courseId) {
        Result result = getResult(studentId, courseId);
        Optional<StudentCourseMarkEntity> topByStudentAndCourseOrderByCreatedDateAsc =
                studentCourseMarkRepository.findTopByStudentAndCourseOrderByCreatedDateAsc(result.studentEntity(),
                        result.courseEntity());
        StudentCourseMark mark = new StudentCourseMark();
        mark.setMark(topByStudentAndCourseOrderByCreatedDateAsc.get().getMark());
        mark.setCreatedDate(topByStudentAndCourseOrderByCreatedDateAsc.get().getCreatedDate());
        return mark;
    }

    public StudentCourseMark getMaxMark(StudentEntity studentId, CourseEntity courseId) {
        Result result = getResult(studentId, courseId);
        Optional<StudentCourseMarkEntity> topByStudentAndCourseOrderByCreatedDateAsc =
                studentCourseMarkRepository.findTop1ByStudentAndCourseOrderByMarkDesc(result.studentEntity(),
                        result.courseEntity());
        StudentCourseMark mark = new StudentCourseMark();
        mark.setMark(topByStudentAndCourseOrderByCreatedDateAsc.get().getMark());
        mark.setCreatedDate(topByStudentAndCourseOrderByCreatedDateAsc.get().getCreatedDate());
        return mark;
    }

    public Double getAverageMark(StudentEntity studentId) {
        StudentCourseMarkEntity studentCourseMarkEntity = get(studentId.getId());
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentCourseMarkEntity.getId());
        if (studentEntity.getId() == null) {
            throw new AppBadException("Student not found");
        }
        Double sum = 0.0;
        Integer count = 0;
        double average;
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();
        for (StudentCourseMarkEntity entity : all) {
            if (entity.getStudent().getId().equals(studentEntity.getId())) {
                sum += entity.getMark();
                count++;
            }
        }
        average = (sum / count);
        return average;
    }

    public Double getAverageMark2(StudentEntity studentId, CourseEntity courseId) {
        Result result = new Result(studentId, courseId);
        if (result.studentEntity().getId() == null) {
            throw new AppBadException("Student not found");
        }
        if (result.courseEntity().getId() == null) {
            throw new AppBadException("Course not found");
        }
        Double sum = 0.0;
        Integer count = 0;
        double average;
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();
        for (StudentCourseMarkEntity entity : all) {
            if (entity.getStudent().getId().equals(result.studentEntity().getId())
                    && entity.getCourse().getId().equals(result.courseEntity().getId())) {
                sum += entity.getMark();
                count++;
            }
        }
        average = (sum / count);
        return average;
    }

    public Integer getGreaterThanAScoreCount(StudentEntity studentId, Double mark) {
        StudentCourseMarkEntity studentCourseMarkEntity = get(studentId.getId());
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentCourseMarkEntity.getId());
        if (studentEntity.getId() == null) {
            throw new AppBadException("Student not found");
        }

        Integer count = 0;
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();
        for (StudentCourseMarkEntity entity : all) {
            if (entity.getMark() > mark && entity.getStudent().getId().equals(studentEntity.getId())) {
                count++;
            }
        }
        return count;

    }

    public Integer getHighestGradeInTheCourse(CourseEntity courseId) {
        StudentCourseMarkEntity studentCourseMarkEntity = get(courseId.getId());
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(studentCourseMarkEntity.getId());
        if (courseEntity.getId() == null) {
            throw new AppBadException("Course not found");
        }

        Optional<StudentCourseMarkEntity> byCourseOrderByMarkDesc = studentCourseMarkRepository.findTop1ByCourseOrderByMarkDesc(courseEntity);
        return byCourseOrderByMarkDesc.get().getMark();

    }
    public Double getAverageGradeOfTheCourse(CourseEntity courseId) {
        StudentCourseMarkEntity studentCourseMarkEntity = get(courseId.getId());
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(studentCourseMarkEntity.getId());
        if (courseEntity.getId() == null) {
            throw new AppBadException("Student not found");
        }
        Double sum = 0.0;
        Integer count = 0;
        double average;
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();
        for (StudentCourseMarkEntity entity : all) {
            if (entity.getCourse().getId().equals(courseEntity.getId())) {
                sum += entity.getMark();
                count++;
            }
        }
        average = (sum / count);
        return average;

    }
    public Integer getNumberOfGradesInTheCourse(CourseEntity courseId) {
            StudentCourseMarkEntity studentCourseMarkEntity = get(courseId.getId());
            CourseEntity courseEntity = new CourseEntity();
            courseEntity.setId(studentCourseMarkEntity.getId());
            if (courseEntity.getId() == null) {
                throw new AppBadException("Student not found");
            }
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();
            int count=0;
        for (StudentCourseMarkEntity entity : all) {
            if (entity.getCourse().getId().equals(courseEntity.getId())){
                count++;
            }
        }
        return count;
    }
    public PageImpl<StudentCourseMark> getCoursePaginationById(Pageable pageable, StudentEntity studentId) {
        StudentEntity studentEntity = new StudentEntity();
        StudentCourseMarkEntity studentCourseMarkEntity = get(studentId.getId());
        studentEntity.setId(studentCourseMarkEntity.getId());
//        studentEntity.setAge(studentCourseMarkEntity.getStudent().getAge());
//        studentEntity.setGender(studentCourseMarkEntity.getStudent().getGender());
//        studentEntity.setLevel(studentCourseMarkEntity.getStudent().getLevel());
//        studentEntity.setSurname(studentCourseMarkEntity.getStudent().getSurname());
//        studentEntity.setName(studentCourseMarkEntity.getStudent().getName());
//        studentEntity.setPhone(studentCourseMarkEntity.getStudent().getPhone());
//        studentEntity.setCreatedDate(studentCourseMarkEntity.getStudent().getCreatedDate());

        Page<StudentCourseMarkEntity> byPrice = studentCourseMarkRepository.findByStudentId(pageable, studentEntity);

        List<StudentCourseMark>dtoList=new LinkedList<>();
        List<StudentCourseMarkEntity> content = byPrice.getContent();
        long totalElements = byPrice.getTotalElements();
        for (StudentCourseMarkEntity entity : content) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList,pageable,totalElements);
    }

    public StudentCourseMark toDTO(StudentCourseMarkEntity entity) {
        StudentCourseMark dto = new StudentCourseMark();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudent().getId());
        dto.setCourseId(entity.getCourse().getId());
        dto.setMark(entity.getMark());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private Result getResult(StudentEntity studentId, CourseEntity couseId) {
        StudentEntity studentEntity = new StudentEntity();
        CourseEntity courseEntity = new CourseEntity();
        Optional<StudentCourseMarkEntity> studentById = studentCourseMarkRepository.findById(studentId.getId());
        Optional<StudentCourseMarkEntity> courseById = studentCourseMarkRepository.findById(couseId.getId());
        if (studentById.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        if (courseById.isEmpty()) {
            throw new AppBadException("Course not found");
        }
        studentEntity.setId(studentById.get().getId());
        courseEntity.setId(courseById.get().getId());
        Result result = new Result(studentEntity, courseEntity);
        return result;
    }

    public StudentCourseMarkEntity get(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Student not found");
        }
        return optional.get();
    }



}
