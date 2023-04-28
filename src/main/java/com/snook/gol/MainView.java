package com.snook.gol;

import com.snook.gol.model.Board;
import com.snook.gol.model.BoundedBoard;
import com.snook.gol.model.CellState;
import com.snook.gol.model.StandardRule;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    public static final int  EDITING = 0;
    public static final int  SIMULATING = 1;

    private InfoBar infoBar;
    private Toolbar toolbar;
    private Canvas canvas;

    private Affine affine;

    private Simulation simulation;
    private Board initialBoard;

    private CellState drawMode = CellState.ALIVE;

    private int applicationState = EDITING;
    public MainView() {

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handledMoved);

        this.canvas.setOnKeyPressed(this::onKeyPressed);

        this.toolbar = new Toolbar(this);
        this.infoBar = new InfoBar();
        this.infoBar.setDrawMode(this.drawMode);
        this.infoBar.setCursorPosition(0, 0);

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(toolbar, this.canvas, spacer, infoBar);

        this.affine = new Affine();
        this.affine.appendScale(400/10f, 400/10f);


        this.initialBoard = new BoundedBoard(10, 10);
    }

    private void handledMoved(MouseEvent mouseEvent) {
        Point2D simCoordinate = this.getSimulationCoordinate(mouseEvent);
        this.infoBar.setCursorPosition((int)simCoordinate.getX(), (int)simCoordinate.getY());
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        System.out.println("Pressed key");
        if(keyEvent.getCode() == KeyCode.D) {
            this.setDrawMode(CellState.ALIVE);
            //this.drawMode = CellState.ALIVE;
            System.out.println("Draw mode");
        } else if(keyEvent.getCode() == KeyCode.E) {
            this.setDrawMode(CellState.DEAD);
            //this.drawMode = CellState.DEAD;
            System.out.println("Erase mode");
        }
    }

    private void handleDraw(MouseEvent mouseEvent) {

        if(applicationState == SIMULATING) {
            return;
        }
        Point2D simCoordinate = this.getSimulationCoordinate(mouseEvent);

        int simX = (int) simCoordinate.getX();
        int simY = (int) simCoordinate.getY();

        System.out.println(simX + ", " + simY);

        this.initialBoard.setState(simX, simY, drawMode);

        draw();
    }

    private Point2D getSimulationCoordinate(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        try {
            return this.affine.inverseTransform(mouseX, mouseY);
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Non invertible transform");
        }
    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine)
        ;
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,450,450);

        if(this.applicationState == EDITING) {
            drawSimulation(this.initialBoard);
        } else {
            drawSimulation(this.simulation.getBoard());
        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= this.initialBoard.getWidth(); x++) {
            g.strokeLine(x, 0, x, 10);
        }
        for (int y = 0; y <= this.initialBoard.getHeight(); y++) {
            g.strokeLine(0,y,10,y);
        }
        canvas.requestFocus();
    }

    private void drawSimulation(Board simulationToDraw) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for (int x = 0; x < simulationToDraw.getWidth(); x++) {
            for (int y = 0; y < simulationToDraw.getHeight(); y++) {
                if (simulationToDraw.getState(x, y) == CellState.ALIVE) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void setDrawMode(CellState newDrawMode) {
        this.drawMode = newDrawMode;
        this.infoBar.setDrawMode(newDrawMode);
    }

    public void setApplicationState(int applicationState) {
        if(applicationState == this.applicationState) {
            return;
        }
        if(applicationState == SIMULATING) {
            this.simulation = new Simulation(this.initialBoard, new StandardRule());
        }

        this.applicationState = applicationState;
        System.out.println("Application State: " + this.applicationState);
    }

    public int getApplicationState() {
        return applicationState;
    }
}
