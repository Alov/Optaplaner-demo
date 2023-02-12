package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

@Data
@PlanningSolution
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @ValueRangeProvider(id = "timeslotRange")
    @ProblemFactCollectionProperty
    private List<TimeSlot> timeslotList;

    @ValueRangeProvider(id = "roomRange")
    @ProblemFactCollectionProperty
    private List<Room> roomList;

    @PlanningEntityCollectionProperty
    private List<Lecture> lessonList;

    @PlanningScore
    private HardSoftScore score;

    private SolverStatus solverStatus;

    public Schedule(List<TimeSlot> slots, List<Room> rooms, List<Lecture> lectures) {
        this.timeslotList = slots;
        this.roomList = rooms;
        this.lessonList = lectures;
    }
}
