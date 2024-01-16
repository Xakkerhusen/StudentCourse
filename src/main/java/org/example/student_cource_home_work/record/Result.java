package org.example.student_cource_home_work.record;

import org.example.student_cource_home_work.entity.CourseEntity;
import org.example.student_cource_home_work.entity.StudentEntity;

public record Result(StudentEntity studentEntity, CourseEntity courseEntity) {
}
