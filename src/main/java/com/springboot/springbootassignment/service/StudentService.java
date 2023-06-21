package com.springboot.springbootassignment.service;
import com.springboot.springbootassignment.entity.*;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(int id);
    Student createStudent(Student student);
    Student updateStudent(int id, Student updatedStudent);
    Student deleteStudent(int id);
}