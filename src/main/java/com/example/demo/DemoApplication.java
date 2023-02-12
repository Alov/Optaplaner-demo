package com.example.demo;

import com.example.demo.domain.Lecture;
import com.example.demo.domain.Room;
import com.example.demo.domain.Teacher;
import com.example.demo.domain.TimeSlot;
import com.example.demo.repository.LectureRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.TimeslotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.DayOfWeek;
import java.time.LocalTime;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        timeslotRepository.save(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotRepository.save(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));


        roomRepository.save(new Room("Room A"));
        roomRepository.save(new Room("Room B"));
        roomRepository.save(new Room("Room C"));
        roomRepository.save(new Room("Room D"));
        roomRepository.save(new Room("Room E"));
        roomRepository.save(new Room("Room F"));

        Teacher t1 = teacherRepository.save(new Teacher("Pavelko", "D."));
        Teacher t2 = teacherRepository.save(new Teacher("Firsov", "O."));
        Teacher t3 = teacherRepository.save(new Teacher("Dolgova", "N."));
        Teacher t4 = teacherRepository.save(new Teacher("Yakimov", "A."));


        lectureRepository.save(new Lecture(t1, "PKS-202", "Algo"));
        lectureRepository.save(new Lecture(t1, "PKS-203", "Algo"));
        lectureRepository.save(new Lecture(t2, "PKS-204", "Algo"));
        lectureRepository.save(new Lecture(t2, "PKS-202", "Web-apps"));
        lectureRepository.save(new Lecture(t2, "PKS-203", "Web-apps"));
        lectureRepository.save(new Lecture(t1, "PKS-204", "Web-apps"));
        lectureRepository.save(new Lecture(t3, "PKS-204", "IS Development"));
        lectureRepository.save(new Lecture(t3, "PKS-203", "IS Development"));
        lectureRepository.save(new Lecture(t3, "PKS-202", "IS Development"));
        lectureRepository.save(new Lecture(t4, "PKS-202", "Computer networks"));
        lectureRepository.save(new Lecture(t4, "PKS-203", "Computer networks"));
        lectureRepository.save(new Lecture(t4, "PKS-203", "Computer networks"));
//

        Lecture lecture = lectureRepository.findAll(Sort.by("id")).iterator().next();
        lecture.setTimeslot(timeslotRepository.findAll(Sort.by("id")).iterator().next());
        lecture.setRoom(roomRepository.findAll(Sort.by("id")).iterator().next());

        lectureRepository.save(lecture);

    }
}
