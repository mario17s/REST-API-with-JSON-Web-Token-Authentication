package com.example.api.control;

import com.example.api.entities.Course;
import com.example.api.entities.Registration;
import com.example.api.entities.Student;
import com.example.api.repo.RegistrationRepository;
import com.example.api.serv.CourseService;
import com.example.api.serv.RegistrationService;
import com.example.api.serv.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@ResponseBody
@CrossOrigin(value = "http://localhost:8081")
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    public RegistrationService serv;

    @GetMapping("/registrations")
    public List<Registration> getRegistrations()
    {
        return serv.getRegistrations();
    }

    @PostMapping("/registrations/{sid}/{cid}")
    public String addRegistration(@PathVariable Long sid, @PathVariable Long cid){
        return serv.saveRegistration(sid, cid);
    }

    @GetMapping("/registrations/{sid}")
    public int getCredits(@PathVariable Long sid){
        return serv.getStudentCredits(sid);
    }

    @DeleteMapping("/registrations/{sid}/{cid}")
    public void deleteRegistration(@PathVariable Long sid, @PathVariable Long cid){
        serv.removeRegistration(sid, cid);
    }

}
