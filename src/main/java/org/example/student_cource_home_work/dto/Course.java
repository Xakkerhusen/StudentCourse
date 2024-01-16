package org.example.student_cource_home_work.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.function.DoubleUnaryOperator;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {
    private Integer id;
    private String name;
    private Double price;
    private String duration;
}
