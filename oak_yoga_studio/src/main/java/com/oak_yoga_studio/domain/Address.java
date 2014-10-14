/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oak_yoga_studio.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Weldu
 */
@Entity
public class Address {
    
    @Id
    @GeneratedValue
    private int id;
    
    @NotBlank
    private String street;
    
    private String state;
    
    private String city;
    
    @Pattern(regexp = "\\d{5}", message = "Zip code must be numeric and either 5 characters.")
    private String zipCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.street != null ? this.street.hashCode() : 0);
        hash = 43 * hash + (this.state != null ? this.state.hashCode() : 0);
        hash = 43 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 43 * hash + (this.zipCode != null ? this.zipCode.hashCode() : 0);
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
        final Address other = (Address) obj;
        if ((this.street == null) ? (other.street != null) : !this.street.equals(other.street)) {
            return false;
        }
        if ((this.state == null) ? (other.state != null) : !this.state.equals(other.state)) {
            return false;
        }
        if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
            return false;
        }
        if ((this.zipCode == null) ? (other.zipCode != null) : !this.zipCode.equals(other.zipCode)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
