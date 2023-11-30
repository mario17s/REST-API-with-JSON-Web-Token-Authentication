package com.example.api.serv;

import com.example.api.repo.StudentRepository;
import com.example.api.entities.Student;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;

    public List<Student> getStudents()
    {
        return repo.findAll();
    }

    public Student saveStudent(Student s)
    {
        String hashed = BCrypt.hashpw(s.getPassword(), BCrypt.gensalt());
        s.setPassword(hashed);
        return repo.save(s);
    }

    public Student updateStudentService(Long id, Student newStudent){
        Student s = repo.findById(id).orElse(new Student());
        s.setFirstName(newStudent.getFirstName());
        s.setLastName(newStudent.getLastName());
        s.setAge(newStudent.getAge());
        s.setDob(newStudent.getDob());
        s.setEmail(newStudent.getEmail());
        String hashed = BCrypt.hashpw(newStudent.getPassword(), BCrypt.gensalt());
        s.setPassword(hashed);
        return repo.save(s);
    }

    public Student getById(Long id)
    {
        return repo.findById(id).orElse(new Student());
    }

    public void removeStudent(Long id)
    {
        Student s = repo.findById(id).orElse(new Student());
        repo.delete(s);
    }
}
