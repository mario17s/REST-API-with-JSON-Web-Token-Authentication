package com.example.api.entities;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "credits")
    private int credits;

    @Column(name = "professor")
    private String professor;

    @OneToMany(mappedBy = "course")
    Set<Registration> registrations = new HashSet<>();

    public void addRegistration(Registration r)
    {
        registrations.add(r);
    }

    public void removeRegistration(Registration r)
    {
        registrations.remove(r);
    }

    public Course(String title, String description, int credits, String professor) {
        this.title = title;
        this.description = description;
        this.credits = credits;
        this.professor = professor;
    }

    public Course(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Set<Registration> gtRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", credits=" + credits +
                ", professor='" + professor + '\'' +
                '}';
    }
}
