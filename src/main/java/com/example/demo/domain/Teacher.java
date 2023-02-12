package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return lastName + " " + firstName.charAt(0) + ".";
    }

    public Teacher(String lastName, String firstName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
