package com.springboot.springbootassignment.service;

import com.springboot.springbootassignment.dao.CourseRepository;
import com.springboot.springbootassignment.entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseService = new CourseServiceImpl(courseRepository);
    }

    @Test
    void testCreateCourse() {
        Course course = new Course(1, "Math");

        when(courseRepository.save(course)).thenReturn(course);

        Course createdCourse = courseService.createCourse(course);

        verify(courseRepository, times(1)).save(course);
        assertEquals(course, createdCourse);
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Math"));
        courses.add(new Course(2, "Science"));

        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> retrievedCourses = courseService.getAllCourses();

        verify(courseRepository, times(1)).findAll();
        assertEquals(courses, retrievedCourses);
    }

    @Test
    void testGetCourseById_ExistingId() {
        int courseId = 1;
        Course course = new Course(courseId, "Math");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course retrievedCourse = courseService.getCourseById(courseId);

        verify(courseRepository, times(1)).findById(courseId);
        assertEquals(course, retrievedCourse);
    }

    @Test
    void testGetCourseById_NonExistingId() {
        int courseId = 1;

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> courseService.getCourseById(courseId));

        verify(courseRepository, times(1)).findById(courseId);
    }
}