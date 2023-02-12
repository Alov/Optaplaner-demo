package com.example.demo.solver;

import com.example.demo.domain.Lecture;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import java.time.Duration;

public class ScheduleConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                // Hard constraints
                roomConflict(constraintFactory),
                teacherConflict(constraintFactory),
                studentGroupConflict(constraintFactory),
                // Soft constraints
                teacherRoomStability(constraintFactory),
                teacherTimeEfficiency(constraintFactory),
                studentGroupSubjectVariety(constraintFactory)
        };
    }

    Constraint roomConflict(ConstraintFactory constraintFactory) {
        // A room can accommodate at most one Lecture at the same time.
        return constraintFactory
                // Select each pair of 2 different Lectures ...
                .fromUniquePair(Lecture.class,
                        // ... in the same timeslot ...
                        Joiners.equal(Lecture::getTimeslot),
                        // ... in the same room ...
                        Joiners.equal(Lecture::getRoom))
                // ... and penalize each pair with a hard weight.
                .penalize("Room conflict", HardSoftScore.ONE_HARD);
    }

    Constraint teacherConflict(ConstraintFactory constraintFactory) {
        // A teacher can teach at most one Lecture at the same time.
        return constraintFactory
                .fromUniquePair(Lecture.class,
                        Joiners.equal(Lecture::getTimeslot),
                        Joiners.equal(Lecture::getTeacher))
                .penalize("Teacher conflict", HardSoftScore.ONE_HARD);
    }

    Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
        // A student can attend at most one Lecture at the same time.
        return constraintFactory
                .fromUniquePair(Lecture.class,
                        Joiners.equal(Lecture::getTimeslot),
                        Joiners.equal(Lecture::getStudentGroup))
                .penalize("Student group conflict", HardSoftScore.ONE_HARD);
    }

    Constraint teacherRoomStability(ConstraintFactory constraintFactory) {
        // A teacher prefers to teach in a single room.
        return constraintFactory
                .fromUniquePair(Lecture.class,
                        Joiners.equal(Lecture::getTeacher))
                .filter((Lecture1, Lecture2) -> Lecture1.getRoom() != Lecture2.getRoom())
                .penalize("Teacher room stability", HardSoftScore.ONE_SOFT);
    }

    Constraint teacherTimeEfficiency(ConstraintFactory constraintFactory) {
        // A teacher prefers to teach sequential Lectures and dislikes gaps between Lectures.
        return constraintFactory
                .from(Lecture.class)
                .join(Lecture.class, Joiners.equal(Lecture::getTeacher),
                        Joiners.equal((Lecture) -> Lecture.getTimeslot().getDayOfWeek()))
                .filter((Lecture1, Lecture2) -> {
                    Duration between = Duration.between(Lecture1.getTimeslot().getEndTime(),
                            Lecture2.getTimeslot().getStartTime());
                    return !between.isNegative() && between.compareTo(Duration.ofMinutes(30)) <= 0;
                })
                .reward("Teacher time efficiency", HardSoftScore.ONE_SOFT);
    }

    Constraint studentGroupSubjectVariety(ConstraintFactory constraintFactory) {
        // A student group dislikes sequential Lectures on the same subject.
        return constraintFactory
                .from(Lecture.class)
                .join(Lecture.class,
                        Joiners.equal(Lecture::getSubject),
                        Joiners.equal(Lecture::getStudentGroup),
                        Joiners.equal((Lecture) -> Lecture.getTimeslot().getDayOfWeek()))
                .filter((Lecture1, Lecture2) -> {
                    Duration between = Duration.between(Lecture1.getTimeslot().getEndTime(),
                            Lecture2.getTimeslot().getStartTime());
                    return !between.isNegative() && between.compareTo(Duration.ofMinutes(30)) <= 0;
                })
                .penalize("Student group subject variety", HardSoftScore.ONE_SOFT);
    }
}
