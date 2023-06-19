package com.springboot.springbootassignment.controller;
import com.springboot.springbootassignment.entity.*;
import com.springboot.springbootassignment.service.CourseService;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseService;
    // MOCK STUDENTS

//    Student s1 = new Student(45, "anku", "t", "a@com", "i397");
//    Student s2 = new Student(4, "trupal", "t", "t@gmail", "i397");
//    List<Student> stu = new ArrayList<>(Arrays.asList(s1, s2));
//    List<Student> stu2 = new ArrayList<>(Arrays.asList(s1));

//    @Before
//public void init() {
    // MOCK COURSES
    Course course1 = new Course(10, "DSA", 4);
    Course course2 = new Course(20, "OS", 3);
    Course course3 = new Course(30, "Linux programming", 3);
//}


    //----------------test for GET all---------------
    @Test
    public void getAllCourses_success() throws Exception {
        List<Course> records = new ArrayList<>(Arrays.asList(course1, course2));
        Mockito.when(courseService.getAllCourses()).thenReturn(records);
        mockMvc.perform(get("/api/courses/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[1].courseName", is("OS")));
    }


    //----------------test for GET course by ID---------------
    @Test
    public void getCourseById_success() throws Exception {
        Mockito.when(courseService.getCourseById(30)).thenReturn(course3);
        mockMvc.perform(get("/api/courses/" + 30)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("courseName", is("Linux programming")));
    }

    //----------------test for POST-------------

    @Test
    public void createCourseTest() throws Exception {
        Course record = new Course(30, "Linux programming", 3);
        Mockito.when(courseService.createCourse(course3)).thenReturn(record);
        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(course3)))
                         .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseCredits", is(3)))
                .andExpect(jsonPath("$.courseName", is("Linux programming")));
    }
}