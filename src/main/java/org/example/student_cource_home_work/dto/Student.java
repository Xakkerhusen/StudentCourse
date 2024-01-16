package org.example.student_cource_home_work.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.student_cource_home_work.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private Integer level;
    private Integer age;
    private Gender gender;
    private LocalDateTime createdDate;
}
