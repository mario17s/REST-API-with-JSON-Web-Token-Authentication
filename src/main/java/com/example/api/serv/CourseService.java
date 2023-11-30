package com.example.api.serv;

import com.example.api.entities.Course;
import com.example.api.entities.Registration;
import com.example.api.entities.Student;
import com.example.api.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository repo;

    public List<Course> getCourses()
    {
        return repo.findAll();
    }

    public Course saveCourse(Course c)
    {
        return repo.save(c);
    }

    public Course getCourseById(Long id)
    {
        return repo.findById(id).orElse(new Course());
    }

    public void removeCourse(Long id)
    {
        Course c = repo.getById(id);
        repo.delete(c);
    }

    public List<Student> getStudentsFromACourse(Long courseId)
    {
        List<Student> students = new ArrayList<>();
        Course c = repo.findById(courseId).orElse(new Course());
        for(Registration r: c.gtRegistrations())
        {
            students.add(r.getStudent());
        }
        return students;
    }

    public Course updateCourseService(Course c, Long id){
        Course course = repo.getById(id);
        course.setTitle(c.getTitle());
        course.setDescription(c.getDescription());
        course.setCredits(c.getCredits());
        course.setProfessor(c.getProfessor());
        return repo.save(course);
    }
}
