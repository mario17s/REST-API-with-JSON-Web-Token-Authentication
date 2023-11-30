package com.example.api.control;

import com.example.api.serv.StudentService;
import com.example.api.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.apache.tomcat.util.security.ConcurrentMessageDigest.digest;

@Controller
@ResponseBody
@CrossOrigin(value = "http://localhost:8081")
@RequestMapping("/api")
public class StudentController {
    @Autowired
    public StudentService serv;

    @GetMapping("/students")
    public List<Student> getStudents()
    {
        return serv.getStudents();
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return serv.saveStudent(s);
    }

    @GetMapping("/students/{i}")
    public Student getStudentById(@PathVariable Long i)
    {
        return serv.getById(i);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student newStudent) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return serv.updateStudentService(id, newStudent);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id)
    {
        serv.removeStudent(id);
    }
}
