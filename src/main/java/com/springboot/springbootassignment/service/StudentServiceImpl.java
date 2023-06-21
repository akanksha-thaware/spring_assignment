package com.springboot.springbootassignment.service;
import com.springboot.springbootassignment.dao.StudentRepository;
import com.springboot.springbootassignment.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(int id) {
        Optional<Student> result = studentRepository.findById(id);
        Student s = null;
        if (result.isPresent()) {
            s = result.get();
        }
        else {
            throw new RuntimeException("Did not find student id - " + id);
        }
        return s;
    }

    @Override
    public Student createStudent(Student student) {
        try{
            return studentRepository.save(student);
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public Student updateStudent(int id, Student updatedStudent) {
        Optional<Student> result = studentRepository.findById(id);
        if (result.isPresent()) {
            Student existingStudent = getStudentById(id);
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setEmail(updatedStudent.getEmail());
            return studentRepository.save(existingStudent);
        }
        else {
            throw new RuntimeException("Cannot update: Did not find student id - " + id);
        }
    }

    @Override
    public Student deleteStudent(int id){
        Optional<Student> result = studentRepository.findById(id);
        Student s = null;
        if (result.isPresent()) {
            s = result.get();
            studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cannot delete: Did not find student id - " + id);
        }
        return s;
    }

}
