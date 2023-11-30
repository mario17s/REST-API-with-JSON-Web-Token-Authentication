package com.example.api.repo;

import com.example.api.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
