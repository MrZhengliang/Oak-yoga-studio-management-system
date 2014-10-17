/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oak_yoga_studio.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Fetiya
 */

@Entity
public class Section {
    
    @Id
    @GeneratedValue
    private int id;
    private String sectionName;
    
    public enum Status {
        INPROGRESS,COMPLETED,OPEN ,CANCELED 
    }
    
    @Size(min=1, max = 15)
    private int capacity;
    
    @NotEmpty
    private int roomNumber;
    
    @Min(1)
    @Max(15)
    private static int availableSeat;
    
    private Status status;
    
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;
    
    @ManyToOne
    @JoinColumn(name="professor_id")
    private Faculty professor;
    
    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<Enrollment> enrollements;

    public Section() {
    }

    public Section(String sectionName, int capacity, int roomNumber,Status status, Course course, Faculty professor, List<Enrollment> enrollements) {
        this.sectionName = sectionName;
        this.capacity = capacity;
        this.roomNumber = roomNumber;
        this.status = status;
        this.course = course;
        this.professor = professor;
        this.enrollements = enrollements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getCapacity() {
        return capacity;
    }

    

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

//    public int getAvailableSeat() {
//        return availableSeat;
//    }
//
//    public void setAvailableSeat(int availableSeat) {
//        this.availableSeat = availableSeat;
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Faculty getProfessor() {
        return professor;
    }

    public void setProfessor(Faculty professor) {
        this.professor = professor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    public void addEnrollment(Enrollment enrollment)
    {
        enrollment.setSection(this);
        this.enrollements.add(enrollment);
    }

    public List<Enrollment> getEnrollements() {
        return enrollements;
    }

    public void setEnrollements(List<Enrollment> enrollements) {
        this.enrollements = enrollements;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.sectionName != null ? this.sectionName.hashCode() : 0);
        hash = 79 * hash + this.capacity;
        hash = 79 * hash + this.roomNumber;
        hash = 79 * hash + (this.status != null ? this.status.hashCode() : 0);
        hash = 79 * hash + (this.course != null ? this.course.hashCode() : 0);
        hash = 79 * hash + (this.enrollements != null ? this.enrollements.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Section other = (Section) obj;
        if ((this.sectionName == null) ? (other.sectionName != null) : !this.sectionName.equals(other.sectionName)) {
            return false;
        }
        if (this.capacity != other.capacity) {
            return false;
        }
        if (this.roomNumber != other.roomNumber) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.course != other.course && (this.course == null || !this.course.equals(other.course))) {
            return false;
        }
        if (this.professor != other.professor && (this.professor == null || !this.professor.equals(other.professor))) {
            return false;
        }
        if (this.enrollements != other.enrollements && (this.enrollements == null || !this.enrollements.equals(other.enrollements))) {
            return false;
        }
        return true;
    }
    
    
}
