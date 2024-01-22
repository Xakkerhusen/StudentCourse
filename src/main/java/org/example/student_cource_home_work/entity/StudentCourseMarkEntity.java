package org.example.student_cource_home_work.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
@Table(name = "student_course_mark")
public class StudentCourseMarkEntity /*extends BaseEntity*/ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "mark")
    private Integer mark;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @JoinColumn(name = "student_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private StudentEntity student;

    @JoinColumn(name = "course_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private CourseEntity course;


}
