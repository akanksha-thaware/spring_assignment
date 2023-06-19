package com.springboot.springbootassignment.dao;

import com.springboot.springbootassignment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Integer> {

}