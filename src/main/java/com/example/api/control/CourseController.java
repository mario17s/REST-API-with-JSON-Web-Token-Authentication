package com.example.api.control;

import com.example.api.entities.Course;
import com.example.api.entities.Registration;
import com.example.api.entities.Student;
import com.example.api.serv.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@CrossOrigin(value = "http://localhost:8081")
@RequestMapping("/api")
public class CourseController {
    public CourseService serv;

    public CourseController(CourseService serv) {
        this.serv = serv;
    }

    @GetMapping("/courses")
    public List<Course> getCourses(){return serv.getCourses();}

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course c){return serv.saveCourse(c);}

    @GetMapping("/courses/{id}")
    public Course getById(@PathVariable Long id){return serv.getCourseById(id);}

    @GetMapping("/courses/stud/{courseId}")
    public List<Student> getStudentFromCourse(@PathVariable Long courseId){
        return serv.getStudentsFromACourse(courseId);
    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@RequestBody Course c, @PathVariable Long id){
        return serv.updateCourseService(c, id);
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable Long id){
        serv.removeCourse(id);
    }
}
