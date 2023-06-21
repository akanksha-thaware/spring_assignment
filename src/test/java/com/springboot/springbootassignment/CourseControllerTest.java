package com.springboot.springbootassignment.controller;
import com.springboot.springbootassignment.entity.*;
import com.springboot.springbootassignment.service.CourseService;
import java.util.*;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(CourseController.class)
class CourseControllerTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    //
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();

    }
    Course course1 = new Course(10, "DSA");
    Course course2 = new Course(20, "OS");
    Course course3 = new Course(30, "Linux programming");


    //----------------test for GET all---------------
    @Test
     void getAllCourses_success() throws Exception {
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
     void getCourseById_success() throws Exception {
        Mockito.when(courseService.getCourseById(30)).thenReturn(course3);
        mockMvc.perform(get("/api/courses/" + 30)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("courseName", is("Linux programming")));
    }
    @Test
    void testGetCourseById_Exception() {
        when(courseService.getCourseById(1))
                .thenThrow(new RuntimeException());
        ResponseEntity<?> response = courseController.getCourseById(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    //----------------test for POST-------------

    @Test
     void createCourseTest() throws Exception {
        Course record = new Course(30, "Linux programming");
        Mockito.when(courseService.createCourse(course3)).thenReturn(record);
        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(course3)))
                         .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName", is("Linux programming")));
    }
}