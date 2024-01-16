package org.example.student_cource_home_work.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.student_cource_home_work.enums.Gender;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
@Table(name = "student")
public class StudentEntity /*extends BaseEntity*/{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "level")
    private Integer level;
    @Column(name = "phone")
    private String phone;
    @Column(name = "age")
    private Integer age;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
