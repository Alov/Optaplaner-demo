package com.example.demo.endpoint;

import com.example.demo.domain.Schedule;
import com.example.demo.repository.ScheduleRepository;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timeTable")
public class ScheduleEndpoint {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    private SolverManager<Schedule, Long> solverManager;

    @Autowired
    private ScoreManager<Schedule, HardSoftScore> scoreManager;

    // To try, GET http://localhost:8080/timeTable
    @GetMapping()
    public Schedule getTimeTable() {
        // Get the solver status before loading the solution
        // to avoid the race condition that the solver terminates between them
        SolverStatus solverStatus = getSolverStatus();
        Schedule solution = scheduleRepository.findById(ScheduleRepository.SINGLETON_TIME_TABLE_ID);
        scoreManager.updateScore(solution); // Sets the score
        solution.setSolverStatus(solverStatus);
        return solution;
    }

    @PostMapping("/solve")
    public void solve() {
        solverManager.solveAndListen(ScheduleRepository.SINGLETON_TIME_TABLE_ID,
                scheduleRepository::findById,
                scheduleRepository::save);
    }

    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(ScheduleRepository.SINGLETON_TIME_TABLE_ID);
    }

    @PostMapping("/stopSolving")
    public void stopSolving() {
        solverManager.terminateEarly(ScheduleRepository.SINGLETON_TIME_TABLE_ID);
    }

}
