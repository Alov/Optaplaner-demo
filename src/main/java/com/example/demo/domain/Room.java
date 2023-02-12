package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @PlanningId
    @GeneratedValue
    private Long id;

    private String name;


    @Override
    public String toString() {
        return name;
    }

    public Room(String name) {
        this.name = name;
    }
}
