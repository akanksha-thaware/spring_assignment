package com.springboot.springbootassignment.controller;
import org.springframework.http.*;
import com.springboot.springbootassignment.entity.Student;
import com.springboot.springbootassignment.service.StudentService;
import org.springframework.web.bind.annotation.*;
import lombok.Data;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@Data
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id)
    {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        try{
            Student stu = studentService.createStudent(student);
            return ResponseEntity.ok(stu);
        }
        catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        try{
            Student stu = studentService.updateStudent(id, updatedStudent);
            return ResponseEntity.ok(stu);
        }
        catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        try{
            Student stu = studentService.deleteStudent(id);
            return ResponseEntity.ok("deleted student : " + stu);
        }
        catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
