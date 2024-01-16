package org.example.student_cource_home_work.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentCourseMark {
    private Integer id;
    private Integer mark;
    private Integer studentId;
    private Integer courseId;
    private LocalDateTime createdDate;
    private Student student;
    private Course course;
}
