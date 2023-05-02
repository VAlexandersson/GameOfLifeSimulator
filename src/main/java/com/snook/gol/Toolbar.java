package com.snook.gol;

import com.snook.gol.model.CellState;
import com.snook.gol.model.StandardRule;
import com.snook.gol.viewmodel.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private final EditorViewModel editorViewModel;
    private final ApplicationViewModel applicationViewModel;
    private final SimulationViewModel simulationViewModel;

    public Toolbar(EditorViewModel editorViewModel, ApplicationViewModel applicationViewModel, SimulationViewModel simulationViewModel){
        this.editorViewModel = editorViewModel;
        this.applicationViewModel = applicationViewModel;
        this.simulationViewModel = simulationViewModel;
        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);
        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);
        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
        this.getItems().addAll(draw, erase, reset, step, start, stop);
    }

    private void handleStop(ActionEvent actionEvent) {
        this.simulationViewModel.stop();
    }

    private void handleStart(ActionEvent actionEvent) {
        System.out.println("Start Pressed");
        switchToSimulatingState();
        this.simulationViewModel.start();
    }

    private void handleReset(ActionEvent actionEvent) {
        System.out.println("Reset Pressed");
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);
    }

    private void handleStep(ActionEvent actionEvent) {
        System.out.println("Step Pressed");
        switchToSimulatingState();
        this.simulationViewModel.doStep();
    }


    private void switchToSimulatingState(){
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
//        Simulation simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
//        this.simulator = new SimulationViewModel(this.boardViewModel, simulation);
    }

    private void handleDraw(ActionEvent actionEvent) {
        System.out.println("Draw Pressed");
        this.editorViewModel.setDrawMode(CellState.ALIVE);
    }
    private void handleErase(ActionEvent actionEvent) {
        System.out.println("Erase Pressed");
        this.editorViewModel.setDrawMode(CellState.DEAD);
    }

}
