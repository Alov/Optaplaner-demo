package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@PlanningEntity
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @PlanningId
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Teacher teacher;
    private String studentGroup;
    private String subject;

    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    @ManyToOne
    private Room room;

    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    @ManyToOne
    private TimeSlot timeslot;

    @Override
    public String toString() {
        return subject + "(" + id + ")";
    }

    public Lecture(Teacher teacher, String groupName, String subject) {
        this.teacher = teacher;
        this.studentGroup = groupName;
        this.subject = subject;
    }
}
