package com.springboot.springbootassignment.entity;
import lombok.*;
import jakarta.persistence.*;
import java.util.*;
// db name : students_database, table name : students
@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    @Id
    @Column(name="roll_number")
    public int student_id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email", unique = true)
    private String email;

    // one student many different courses (in course table there will be a column with student_id)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="student_id")
    private List<Course> courses;

}
