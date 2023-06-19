package com.springboot.springbootassignment.dao;
import com.springboot.springbootassignment.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}