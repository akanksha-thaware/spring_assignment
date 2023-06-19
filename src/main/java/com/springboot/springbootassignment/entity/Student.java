package com.springboot.springbootassignment.entity;
import lombok.*;
import jakarta.persistence.*;
import java.util.*;
// db name : students_database, table name : students
@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    // let one course belong to one student
    @OneToOne(cascade=CascadeType.ALL)
    // id = child class from course
    @JoinColumn(name= "course_id", referencedColumnName = "id")
    private Course course;

}
