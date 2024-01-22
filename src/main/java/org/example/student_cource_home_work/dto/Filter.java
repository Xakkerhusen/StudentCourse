package org.example.student_cource_home_work.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.student_cource_home_work.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class Filter {
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private String phone;
    private Integer level;
    private Gender gender;
    private LocalDate fromDate;
    private LocalDate toDate;

    private Double price;
    private String duration;

    private Integer markFrom;
    private Integer markTo;
    private Integer studentId;
    private Integer courseId;

    private Student student;
    private Course course;
}
