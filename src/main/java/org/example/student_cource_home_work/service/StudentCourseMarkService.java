package org.example.student_cource_home_work.service;

import org.example.student_cource_home_work.dto.*;
import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentCourseMarkEntity;
import org.example.student_cource_home_work.entity.StudentEntity;
import org.example.student_cource_home_work.exp.AppBadException;
import org.example.student_cource_home_work.mapper.Mapper;
import org.example.student_cource_home_work.record.Result;
import org.example.student_cource_home_work.repository.CourseRepository;
import org.example.student_cource_home_work.repository.CustomRepository;
import org.example.student_cource_home_work.repository.StudentCourseMarkRepository;
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
    @Autowired
    CustomRepository customRepository;

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

    public List<StudentCourseMark> getByMark(LocalDate time, Integer id) {
        LocalDateTime localDateTime = LocalDateTime.of(time, LocalTime.MIN);
        LocalDateTime localDateTime2 = LocalDateTime.of(time, LocalTime.MAX);
        StudentCourseMarkEntity studentCourseMarkEntity1 = get(id);
        Integer id1 = studentCourseMarkEntity1.getStudent().getId();
        List<StudentCourseMarkEntity> byStudentAndCreatedDateBetween =
                studentCourseMarkRepository.findByStudentAndCreatedDateBetween(id1, localDateTime, localDateTime2);
        List<StudentCourseMark> studentCourseMarkList = new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : byStudentAndCreatedDateBetween) {
            StudentCourseMark dto = new StudentCourseMark();
            dto.setMark(studentCourseMarkEntity.getMark());
            studentCourseMarkList.add(dto);
        }
        return studentCourseMarkList;
    }

    public List<StudentCourseMark> getMarkBetween(Integer id, LocalDate from, LocalDate to) {
        LocalDateTime localDateTime1 = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime localDateTime2 = LocalDateTime.of(to, LocalTime.MAX);
        Integer id1 = get(id).getStudent().getId();
        List<StudentCourseMarkEntity> byStudentAndCreatedDateBetween =
                studentCourseMarkRepository.findByStudentAndCreatedDateBetween(id1, localDateTime1, localDateTime2);
        List<StudentCourseMark> studentCourseMarkList = new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : byStudentAndCreatedDateBetween) {
            StudentCourseMark dto = new StudentCourseMark();
            dto.setMark(studentCourseMarkEntity.getMark());
            studentCourseMarkList.add(dto);
        }
        return studentCourseMarkList;
    }

    public List<StudentCourseMark> getMarkDesc(Integer id) {
        Integer id2 = get(id).getStudent().getId();
        if (id2 == null) {
            throw new AppBadException("Student not found");
        }
        List<StudentCourseMarkEntity> byStudentCreatedDateDesc =
                studentCourseMarkRepository.findByStudentOrderByMarkDesc(id2);
        List<StudentCourseMark> list = new LinkedList<>();
        for (StudentCourseMarkEntity entity : byStudentCreatedDateDesc) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public List<StudentCourseMark> getMarkByCreatedDesc2(Integer studentId, Integer courseId) {
        Result result = getResult(studentId, courseId);
        List<StudentCourseMarkEntity> byStudentCreatedDateDesc =
                studentCourseMarkRepository.findByStudentAndCourseOrderByCreatedDateDesc(result.studentEntity().getId(), result.courseEntity().getId());
        List<StudentCourseMark> list = new LinkedList<>();
        for (StudentCourseMarkEntity entity : byStudentCreatedDateDesc) {
            list.add(toDTO(entity));
        }
        return list;
    }

    public StudentCourseMark getCourseName(Integer studentId, Integer courseId) {
        Result result = getResult(studentId, courseId);
        Optional<StudentCourseMarkEntity> optional =
                studentCourseMarkRepository.findTop1ByStudentAndCourseOrderByCreatedDateDesc(result.studentEntity().getId(), result.courseEntity().getId());
        StudentCourseMark studentCourseMark = new StudentCourseMark();
        Course course = new Course();
        course.setName(optional.get().getCourse().getName());
        studentCourseMark.setCourse(course);
        studentCourseMark.setMark(optional.get().getMark());
        return studentCourseMark;
    }

    public List<StudentCourseMark> getMarkLimit3(Integer studentId) {
        Integer id = get(studentId).getStudent().getId();
        List<StudentCourseMarkEntity> top3ByStudentOrderByMarkDesc =
                studentCourseMarkRepository.findTop3ByStudentOrderByMarkDesc(id);
        List<StudentCourseMark> studentCourseMark = new LinkedList<>();
        for (StudentCourseMarkEntity entity : top3ByStudentOrderByMarkDesc) {
            studentCourseMark.add(toDTO(entity));
        }
        return studentCourseMark;
    }

    public StudentCourseMark getFirstMark(Integer studentId) {
        Integer id = get(studentId).getStudent().getId();
        Optional<StudentCourseMarkEntity> topByStudentOrderByCreatedDateAsc =
                studentCourseMarkRepository.findByStudentOrderByCreatedDateAsc(id);
        StudentCourseMark mark = new StudentCourseMark();
        mark.setMark(topByStudentOrderByCreatedDateAsc.get().getMark());
        mark.setCreatedDate(topByStudentOrderByCreatedDateAsc.get().getCreatedDate());
        return mark;
    }

    public StudentCourseMark getFirstMark2(Integer studentId, Integer courseId) {
        Result result = getResult(studentId, courseId);
        Optional<StudentCourseMarkEntity> topByStudentAndCourseOrderByCreatedDateAsc =
                studentCourseMarkRepository.findByStudentAndCourseOrderByCreatedDateAsc(result.studentEntity().getId(),
                        result.courseEntity().getId());
        StudentCourseMark mark = new StudentCourseMark();
        mark.setMark(topByStudentAndCourseOrderByCreatedDateAsc.get().getMark());
        mark.setCreatedDate(topByStudentAndCourseOrderByCreatedDateAsc.get().getCreatedDate());
        return mark;
    }

    public StudentCourseMark getMaxMark(Integer studentId, Integer courseId) {
        Result result = getResult(studentId, courseId);
        Optional<StudentCourseMarkEntity> topByStudentAndCourseOrderByCreatedDateAsc =
                studentCourseMarkRepository.findByStudentAndCourseOrderByMarkDesc(result.studentEntity().getId(),
                        result.courseEntity().getId());
        StudentCourseMark mark = new StudentCourseMark();
        mark.setMark(topByStudentAndCourseOrderByCreatedDateAsc.get().getMark());
        mark.setCreatedDate(topByStudentAndCourseOrderByCreatedDateAsc.get().getCreatedDate());
        return mark;
    }

    public Double getAverageMark(Integer studentId) {
        Integer id = get(studentId).getStudent().getId();
        if (id == null) {
            throw new AppBadException("Student not found");
        }
        Double sum = 0.0;
        Integer count = 0;
        double average;
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();
        for (StudentCourseMarkEntity entity : all) {
            if (entity.getStudent().getId().equals(id)) {
                sum += entity.getMark();
                count++;
            }
        }
        average = (sum / count);
        return average;
    }

    public Double getAverageMark2(Integer studentId, Integer courseId) {
        Result result = getResult(studentId, courseId);
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

    public Integer getGreaterThanAScoreCount(Integer studentId, Double mark) {
        Integer id = get(studentId).getStudent().getId();
        if (id == null) {
            throw new AppBadException("Student not found");
        }

        Integer count = 0;
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();
        for (StudentCourseMarkEntity entity : all) {
            if (entity.getMark() > mark && entity.getStudent().getId().equals(id)) {
                count++;
            }
        }
        return count;

    }

    public Integer getHighestGradeInTheCourse(Integer courseId) {
        Integer id = get(courseId).getCourse().getId();
        if (id == null) {
            throw new AppBadException("Course not found");
        }
        Optional<StudentCourseMarkEntity> byCourseOrderByMarkDesc =
                studentCourseMarkRepository.findByCourseOrderByMarkDesc(id);
        return byCourseOrderByMarkDesc.get().getMark();

    }

    public Double getAverageGradeOfTheCourse(Integer courseId) {
        Integer id = get(courseId).getCourse().getId();
        if (id == null) {
            throw new AppBadException("Course not found");
        }
        Double sum = 0.0;
        Integer count = 0;
        double average;
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();
        for (StudentCourseMarkEntity entity : all) {
            if (entity.getCourse().getId().equals(id)) {
                sum += entity.getMark();
                count++;
            }
        }
        average = (sum / count);
        return average;

    }

    public Integer getNumberOfGradesInTheCourse(Integer courseId) {
        Integer id = get(courseId).getCourse().getId();
        if (id == null) {
            throw new AppBadException("Course not found");
        }
        Iterable<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll();
        int count = 0;
        for (StudentCourseMarkEntity entity : all) {
            if (entity.getCourse().getId().equals(id)) {
                count++;
            }
        }
        return count;
    }

    public PageImpl<StudentCourseMark> pagination(Pageable pageable) {
        Page<StudentCourseMarkEntity> all = studentCourseMarkRepository.findAll(pageable);

        List<StudentCourseMarkEntity> content = all.getContent();
        long totalElements = all.getTotalElements();

        List<StudentCourseMark> dtoList = new LinkedList<>();

        for (StudentCourseMarkEntity studentCourseMarkEntity : content) {
            dtoList.add(toDTO(studentCourseMarkEntity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public PageImpl<StudentCourseMark> paginationByStudentId(Integer studentId, Pageable pageable) {
        Page<StudentCourseMarkEntity> allByStudentId = studentCourseMarkRepository.findAllByStudentId(pageable, studentId);

        List<StudentCourseMarkEntity> content = allByStudentId.getContent();
        long totalElements = allByStudentId.getTotalElements();
        List<StudentCourseMark> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity studentCourseMarkEntity : content) {
            dtoList.add(toDTO(studentCourseMarkEntity));
        }
        int pageSize = pageable.getPageSize();
        System.out.println("pageable.getPageNumber() = " + pageable.getPageNumber());
        System.out.println("pageSize = " + pageSize);

        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public PageImpl<StudentCourseMark> paginationByCourseId(Integer courseId, Pageable pageable) {
        Page<StudentCourseMarkEntity> allByCourseId = studentCourseMarkRepository.findAllByCourseId(pageable, courseId);

        List<StudentCourseMarkEntity> content = allByCourseId.getContent();
        long totalElements = allByCourseId.getTotalElements();

        List<StudentCourseMark> dtoList = new LinkedList<>();

        for (StudentCourseMarkEntity studentCourseMarkEntity : content) {
            dtoList.add(toDTO(studentCourseMarkEntity));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
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

    private Result getResult(Integer studentId, Integer courseId) {
        StudentEntity studentEntity = new StudentEntity();
        CourseEntity courseEntity = new CourseEntity();
        Optional<StudentCourseMarkEntity> studentById = studentCourseMarkRepository.findById(studentId);
        Optional<StudentCourseMarkEntity> courseById = studentCourseMarkRepository.findById(courseId);
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


    public List<StudentCourseMark> getByStudentIdMarkList(Integer id) {
        StudentCourseMarkEntity entity = get(id);
        if (entity == null) {
            throw new AppBadException("Student not found");
        }
        List<Mapper> mapper100 = studentCourseMarkRepository.getByStudentId(id);
        List<StudentCourseMark> resultList = new LinkedList<>();
        for (Mapper mapper : mapper100) {
            StudentCourseMark dto = new StudentCourseMark();
            Course course = new Course();
            dto.setId(mapper.getId());
            dto.setMark(mapper.getMark());
            dto.setCreatedDate(mapper.getCreatedDate());

            course.setId(mapper.getCourseId());
            course.setName(mapper.getName());
            course.setDuration(mapper.getDuration());

            dto.setCourse(course);
            resultList.add(dto);
        }
        return resultList;
    }

    public List<StudentCourseMark> getByCourseIdMarkList(Integer id) {
        StudentCourseMarkEntity entity = get(id);
        if (entity == null) {
            throw new AppBadException("Course not found");
        }
        List<Mapper> mapper100 = studentCourseMarkRepository.getByCourseId(id);
        List<StudentCourseMark> resultList = new LinkedList<>();
        for (Mapper mapper : mapper100) {
            StudentCourseMark dto = new StudentCourseMark();
            Student student = new Student();
            dto.setId(mapper.getId());
            dto.setMark(mapper.getMark());
            dto.setCreatedDate(mapper.getCreatedDate());

            student.setId(mapper.getCourseId());
            student.setName(mapper.getName());
            student.setSurname(mapper.getSurname());
            dto.setStudent(student);
            resultList.add(dto);
        }
        return resultList;
    }

    public List<StudentCourseMark> getAllByInnerJoin() {
        List<Mapper> mapper100 = studentCourseMarkRepository.getAll();
        List<StudentCourseMark> resultList = new LinkedList<>();
        for (Mapper mapper : mapper100) {
            StudentCourseMark dto = new StudentCourseMark();
            Student student = new Student();
            Course course = new Course();

            dto.setId(mapper.getId());
            dto.setMark(mapper.getMark());
            dto.setCreatedDate(mapper.getCreatedDate());

            student.setId(mapper.getCourseId());
            student.setName(mapper.getName());
            student.setSurname(mapper.getSurname());
            course.setId(mapper.getCourseId());
            course.setName(mapper.getName());
            course.setPrice(mapper.getPrice());

            dto.setCourse(course);
            dto.setStudent(student);
            resultList.add(dto);
        }
        return resultList;

    }

    public PageImpl<StudentCourseMark> filter(Filter filter, Pageable pageable) {
        PaginationResultDTO<StudentCourseMarkEntity> scmFilter = customRepository.studentCourseFilter(filter, pageable);

        List<StudentCourseMark> list = new LinkedList<>();
        for (StudentCourseMarkEntity entity : scmFilter.getList()) {
            list.add(toDTO(entity));
        }
        return new PageImpl<>(list,pageable,scmFilter.getTotalSize());
    }
}
