package com.example.demo.repository;

import com.example.demo.domain.Lecture;
import com.example.demo.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ScheduleRepository {

    // There is only one time table, so there is only timeTableId (= problemId).
    public static final Long SINGLETON_TIME_TABLE_ID = 1L;

    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private LectureRepository lectureRepository;

    public Schedule findById(Long id) {
        if (!SINGLETON_TIME_TABLE_ID.equals(id)) {
            throw new IllegalStateException("There is no timeTable with id (" + id + ").");
        }
        // Occurs in a single transaction, so each initialized lesson references the same timeslot/room instance
        // that is contained by the timeTable's timeslotList/roomList.
        return new Schedule(
                timeslotRepository.findAll(),
                roomRepository.findAll(),
                lectureRepository.findAll());
    }

    public void save(Schedule schedule) {
        // TODO this is awfully naive: optimistic locking causes issues if called by the SolverManager
        lectureRepository.saveAll(schedule.getLessonList());
    }

}