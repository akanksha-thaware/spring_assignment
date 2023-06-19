package com.springboot.springbootassignment.service;

import com.springboot.springbootassignment.entity.Student;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(int id);
    Student createStudent(Student student);
    Student updateStudent(int id, Student updatedStudent);
    void deleteStudent(int id);
}