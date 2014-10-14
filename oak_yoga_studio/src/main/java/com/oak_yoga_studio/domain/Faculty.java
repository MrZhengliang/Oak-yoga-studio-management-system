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
import javax.persistence.OneToMany;

/**
 *
 * @author Weldu
 */
@Entity
public class Faculty extends User {
    
    
    private String status;
    
   
    @OneToMany(mappedBy = "advisor",cascade =CascadeType.ALL )
    private List<Customer> advisees;
    
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Section> sections;
    
    
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Waiver> waivers;

    public Faculty() {
    }

    public Faculty(String status, List<Customer> advisees, List<Section> sections, List<Waiver> waivers) {
        this.status = status;
        this.advisees = advisees;
        this.sections = sections;
        this.waivers = waivers;
    }
   
    
    
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    //Add advisee
    public void addAdvisee(Customer customer)
    {
       this.advisees.add(customer);
    }

    public List<Customer> getAdvisees() {
        return advisees;
    }

    public void setAdvisees(List<Customer> advisees) {
        this.advisees = advisees;
    }
    //Add and Remove Section
    public void addSection(Section section)
    {
        this.sections.add(section);
    }
    public void removeSection(Section section)
    {
        //this.sections.remove(section);
        getSections().remove(section);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
    
    public void addWaiver(Waiver waiver)
    {
      this.waivers.add(waiver);
      waiver.setFaculty(this);
    }
    public List<Waiver> getWaivers() {
        return waivers;
    }

    public void setWaivers(List<Waiver> waivers) {
        this.waivers = waivers;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.status != null ? this.status.hashCode() : 0);
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
        final Faculty other = (Faculty) obj;
        if ((this.status == null) ? (other.status != null) : !this.status.equals(other.status)) {
            return false;
        }
        return true;
    }

   
    
    
}
