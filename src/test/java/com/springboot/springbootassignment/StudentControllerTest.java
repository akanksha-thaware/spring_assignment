package com.springboot.springbootassignment.controller;
import com.springboot.springbootassignment.entity.*;
import com.springboot.springbootassignment.service.StudentService;
import java.util.*;
import org.springframework.http.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.Before;
import static org.mockito.Mockito.when;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @MockBean
    private StudentService studentService;


    @Autowired
    ObjectMapper mapper;

    @InjectMocks
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    //
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

    }

    @Test
    void testGetAllStudents() throws Exception {
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student s1 = new Student(1, "John", "D", "johnmail", set1);
        Student s2 = new Student(2, "Alice", "F", "alicemail", set1);
        List<Student> records = new ArrayList<>(Arrays.asList(s1, s2));
        Mockito.when(studentService.getAllStudents()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", is("F")));

        // Verifying that the service method was called
        Mockito.verify(studentService, Mockito.times(1)).getAllStudents();
        Mockito.verifyNoMoreInteractions(studentService);
    }

    @Test
    void getStudentById_success() throws Exception {
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student s1 = new Student(1, "John", "D", "johnmail", set1);
        Mockito.when(studentService.getStudentById(1)).thenReturn(s1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", is("John")));
    }

    @Test
    void testGetStudentById_Exception() {
        int studentId = 1;
        when(studentService.getStudentById(studentId))
                .thenThrow(new RuntimeException());
        ResponseEntity<?> response = studentController.getStudentById(studentId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    void testCreateStudent() throws Exception {
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student s1 = new Student(1, "John", "D", "johnmail", set1);
        Mockito.when(studentService.createStudent(s1)).thenReturn(s1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(s1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("email", is("johnmail")));

    }
    @Test
    void testCreateStudent_Exception() {
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student student = new Student(1, "John", "D", "johnmail", set1);
        when(studentService.createStudent(student))
                .thenThrow(new RuntimeException());
        ResponseEntity<?> response = studentController.createStudent(student);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    void testDeleteStudent() throws Exception {
        // Mocking the service response
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student student = new Student(1, "John", "D", "johnmail", set1);
        Mockito.when(studentService.deleteStudent(1)).thenReturn(student);

        // Performing the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("deleted student : " + student.toString()));

        // Verifying that the service method was called
        Mockito.verify(studentService, Mockito.times(1)).deleteStudent(1);
        Mockito.verifyNoMoreInteractions(studentService);
    }

    @Test
    void testDeleteStudent_Exception() {
        when(studentService.deleteStudent(1))
                .thenThrow(new RuntimeException());
        ResponseEntity<?> response = studentController.deleteStudent(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
//    //----------------test for PUT-------------

    @Test
    void updateStudentTest() throws Exception {
        // Mocking the service response
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student body = new Student(150, "Raghav", "N", "raghav@gmail.com", set1);
        Mockito.when(studentService.updateStudent(150, body)).thenReturn(body);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/students/" + 150)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("email", is("raghav@gmail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", is("N")));
    }

    @Test
    void testUpdateStudent_Exception() {
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student student = new Student(1, "John", "D", "johnmail", set1);
        when(studentService.updateStudent(1, student))
                .thenThrow(new RuntimeException());
        ResponseEntity<?> response = studentController.updateStudent(1, student);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}

