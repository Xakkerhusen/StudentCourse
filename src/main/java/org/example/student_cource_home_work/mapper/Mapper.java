package org.example.student_cource_home_work.mapper;

import org.example.student_cource_home_work.dto.Course;

import java.time.LocalDateTime;

public interface Mapper {
    Integer getId();
    Integer getCourseId();
    String getDuration();
    Integer getMark();
    Double getPrice();
    LocalDateTime getCreatedDate();
    String getName();
    String getSurname();

}
