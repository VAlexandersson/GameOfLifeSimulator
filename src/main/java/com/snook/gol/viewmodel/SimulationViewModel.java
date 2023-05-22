package com.snook.gol.viewmodel;

import com.snook.gol.Simulation;
import com.snook.gol.model.StandardRule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimulationViewModel {

    private Timeline timeline;
    private final BoardViewModel boardViewModel;
    private Simulation simulation;
    public SimulationViewModel(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void onAppStateChanged(ApplicationState state) {
        if(state == ApplicationState.SIMULATING) {
            this.simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        }
    }

    public void doStep() {
        this.simulation.step();
        this.boardViewModel.setBoard(this.simulation.getBoard());
    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {
        this.timeline.stop();
    }
}
