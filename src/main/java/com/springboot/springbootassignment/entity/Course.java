package com.springboot.springbootassignment.entity;
import lombok.*;
import jakarta.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
// db name : students_database, table name : courses
@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int course_id;

    @Column(name="course_name")
    private String courseName;

}