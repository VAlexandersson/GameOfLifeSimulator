package com.snook;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private final MainView mainView;
    Button draw;
    Button erase;
    Button step;
    public Toolbar(MainView mainView){
        this.mainView = mainView;
        draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);
        erase = new Button("Erase");
        erase.setOnAction(this::handleErase);
        step = new Button("Step");
        step.setOnAction(this::handleStep);
        this.getItems().addAll(draw, erase, step);
    }

    private void handleStep(ActionEvent actionEvent) {
        this.mainView.getSimulation().step();
        this.mainView.draw();
        System.out.println("Step Pressed");
    }

    private void handleDraw(ActionEvent actionEvent) {
        System.out.println("Draw Pressed");
        this.mainView.setDrawMode(Simulation.ALIVE);
    }
    private void handleErase(ActionEvent actionEvent) {
        System.out.println("Erase Pressed");
        this.mainView.setDrawMode(Simulation.DEAD);
    }


}
