package com.springboot.springbootassignment.controller;
import com.springboot.springbootassignment.entity.*;
import com.springboot.springbootassignment.service.StudentService;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;
    // MOCK COURSES
    Course set1 = new Course(10, "DSA", 4);
    Course set2 = new Course(20, "OS", 3);

    // MOCK STUDENTS
    Student s1 = new Student(145, "akanksha", "t", "a@com", "1234567890", set1);
    Student s2 = new Student(146, "trupal", "t", "t@gmail", "9876543210", set2);
    Student s3 = new Student(150, "Raghav", "Narang", "r@gmail.com", "99817353610", set2);

    //----------------test for GET all---------------
    @Test
    public void getAllStudents_success() throws Exception {
        List<Student> records = new ArrayList<>(Arrays.asList(s1, s2));
        Mockito.when(studentService.getAllStudents()).thenReturn(records);
        mockMvc.perform(get("/api/students/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


    //----------------test for GET Student by ID---------------
    @Test
    public void getStudentById_success() throws Exception {
        Mockito.when(studentService.getStudentById(146)).thenReturn(s2);
        mockMvc.perform(get("/api/students/" + 146)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("trupal")));
    }

    //----------------test for POST-------------

    @Test
    public void  createStudentTest() throws Exception {
        Student record = new Student(150, "Raghav", "Narang", "r@gmail.com", "99817353610", set2);
        Mockito.when(studentService.createStudent(s3)).thenReturn(record);
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(s3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email", is("r@gmail.com")));
    }

//    //----------------test for PUT-------------

    @Test
    public void updateStudentTest() throws Exception {
        Student updatedrecord = new Student(150, "Raghav", "N", "raghav@gmail.com", "99817353610", set2);
        Student body = new Student(150, "Raghav", "N", "raghav@gmail.com", "99817353610", set2);
        Mockito.when(studentService.updateStudent(150, body)).thenReturn(updatedrecord);
        mockMvc.perform(put("/api/students/" + 150)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email", is("raghav@gmail.com")))
                .andExpect(jsonPath("lastName", is("N")));
    }

//    //----------------test for DELETE-------------
//
//    @Test
//    public void deleteStudentTest() throws Exception {
//        Mockito.when(studentService.deleteStudent(145));
//        mockMvc.perform(delete("/api/students/" + 145)
//                .andExpect(status().isOk());
//    }

}