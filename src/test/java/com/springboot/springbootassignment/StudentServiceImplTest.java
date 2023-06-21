import com.springboot.springbootassignment.dao.StudentRepository;
import com.springboot.springbootassignment.entity.*;
import com.springboot.springbootassignment.service.StudentService;
import com.springboot.springbootassignment.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        // Prepare
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        List<Student> students = Arrays.asList(
                new Student(1, "John", " Doe", "john@example.com", set1),
                new Student(2, "Jane", "Smith", "jane@example.com", set1)
        );
        when(studentRepository.findAll()).thenReturn(students);

        // Execute
        List<Student> result = studentService.getAllStudents();

        // Verify
        assertEquals(students.size(), result.size());
        assertEquals(students.get(0), result.get(0));
        assertEquals(students.get(1), result.get(1));
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById_ExistingStudent() {
        // Prepare
        int studentId = 1;
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student student = new Student(studentId, "John", "Doe", "john@example.com", set1);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Execute
        Student result = studentService.getStudentById(studentId);

        // Verify
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("john@example.com", result.getEmail());
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void testGetStudentById_NonExistingStudent() {
        // Prepare
        int studentId = 1;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Execute and Verify
        assertThrows(RuntimeException.class, () -> {
            studentService.getStudentById(studentId);
        });
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void testCreateStudent() {
        // Prepare
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student student = new Student(1, "John", "Doe", "john@example.com", set1);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Execute
        Student result = studentService.createStudent(student);

        // Verify
        assertNotNull(result);
        assertEquals(student, result);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testCreateStudent_Exception() {
        // Prepare
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student student = new Student(1, "John", "Doe", "john@example.com", set1);
        when(studentRepository.save(any(Student.class)))
                .thenThrow(new RuntimeException());

        // Execute and Verify
        assertThrows(RuntimeException.class, () -> {
            studentService.createStudent(student);
        });
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testUpdateStudent_ExistingStudent() {
        // Prepare
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        int studentId = 1;
        Student existingStudent = new Student(studentId, "John", "Doe", "john@example.com", set1);
        Student updatedStudent = new Student(studentId, "Jane", "Smith", "jane@example.com", set1);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // Execute
        Student result = studentService.updateStudent(studentId, updatedStudent);

        // Verify
        assertNotNull(result);
        assertEquals(updatedStudent.getFirstName(), result.getFirstName());
        assertEquals(updatedStudent.getEmail(), result.getEmail());
    }

    @Test
    void testUpdateStudent_NonExistingStudent() {
        // Prepare
        int studentId = 1;
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student updatedStudent = new Student(studentId, "Jane", "Smith", "jane@example.com", set1);

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Execute and Verify
        assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(studentId, updatedStudent);
        });
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void testDeleteStudent_ExistingStudent() {
        // Prepare
        int studentId = 1;
        List<Course> set1 = new ArrayList<>();
        Course course1 = new Course(10, "DSA");
        set1.add(course1);
        Student student = new Student(studentId, "John", "Doe", "john@example.com", set1);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        // Execute
        Student result = studentService.deleteStudent(studentId);
        // Verify
        assertNotNull(result);
        assertEquals(student, result);
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void testDeleteStudent_NonExistingStudent() {
        // Prepare
        int studentId = 1;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Execute and Verify
        assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudent(studentId);
        });
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, never()).deleteById(anyInt());
    }
}
