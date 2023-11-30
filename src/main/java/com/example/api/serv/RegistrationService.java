package com.example.api.serv;

import com.example.api.entities.Course;
import com.example.api.entities.Registration;
import com.example.api.entities.Student;
import com.example.api.repo.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    private RegistrationRepository repo;

    @Autowired
    public StudentService studentService;

    @Autowired
    public CourseService courseService;

    public List<Registration> getRegistrations() {return repo.findAll();}

    public String saveRegistration(Long sid,Long cid){
        Student s = studentService.getById(sid);
        String error = "";
        int sum = 0;
        for (Registration r : s.gtRegistrations()) {
            Course currentCourse = r.getCourse();
            if (currentCourse.getId() == cid)
                return "Duplicate!";
            sum += currentCourse.getCredits();
        }
        if (sum >= 50)
            return "Over the limit!";
        Course c = courseService.getCourseById(cid);
        Registration r = repo.save(new Registration(s, c));
        s.addRegistration(r);
        c.addRegistration(r);
        return error;
    }

    public int getStudentCredits(Long sid){
        Student s = studentService.getById(sid);
        int sum = 0;
        for(Registration r: s.gtRegistrations())
        {
            Course currentCourse = r.getCourse();
            sum += currentCourse.getCredits();
        }
        return sum;
    }

    public void removeRegistration(Long sid, Long cid){
        Student s = studentService.getById(sid);
        Course c = courseService.getCourseById(cid);
        Registration r = new Registration();
        for(Registration re: s.gtRegistrations())
        {
            if(re.getStudent().getId() == sid && re.getCourse().getId() == cid)
                r = re;
        }
        repo.delete(r);
        s.removeRegistration(r);
        c.removeRegistration(r);
    }
}
