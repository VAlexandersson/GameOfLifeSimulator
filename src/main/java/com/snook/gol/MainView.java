package com.snook.gol;

import com.snook.gol.model.Board;
import com.snook.gol.model.BoundedBoard;
import com.snook.gol.model.CellState;
import com.snook.gol.model.StandardRule;
import com.snook.gol.viewmodel.ApplicationState;
import com.snook.gol.viewmodel.ApplicationViewModel;
import com.snook.gol.viewmodel.BoardViewModel;
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

    private InfoBar infoBar;
    private Toolbar toolbar;
    private Canvas canvas;

    private Affine affine;

    private Board initialBoard;

    private CellState drawMode = CellState.ALIVE;

    private ApplicationViewModel appViewModel;
    private final BoardViewModel boardViewModel;

    private boolean isDrawingEnabled = true;

    public MainView(ApplicationViewModel appViewModel, BoardViewModel boardViewModel, Board initialBoard) {
        this.appViewModel = appViewModel;
        this.boardViewModel = boardViewModel;
        this.initialBoard = initialBoard;

        this.appViewModel.listenToAppState(this::onApplicationStateChanged);
        this.boardViewModel.listenToBoard(this::onBoardChanged);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handledMoved);

        this.canvas.setOnKeyPressed(this::onKeyPressed);

        this.toolbar = new Toolbar(this, appViewModel, boardViewModel);

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


    }

    private void onBoardChanged(Board board) {
        draw(board);
    }

    private void onApplicationStateChanged(ApplicationState state){
        if(state == ApplicationState.EDITING) {
            this.isDrawingEnabled = true;
            this.boardViewModel.setBoard(this.initialBoard);
        } else if (state == ApplicationState.SIMULATING){
            this.isDrawingEnabled = false;
        } else {
            throw new IllegalArgumentException("Unsupported ApplicationState " + state.name());
        }
    }

    private void handledMoved(MouseEvent mouseEvent) {
        Point2D simCoordinate = this.getSimulationCoordinate(mouseEvent);
        this.infoBar.setCursorPosition((int)simCoordinate.getX(), (int)simCoordinate.getY());
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        System.out.println("Pressed key");
        if(keyEvent.getCode() == KeyCode.D) {
            this.setDrawMode(CellState.ALIVE);
            System.out.println("Draw mode");
        } else if(keyEvent.getCode() == KeyCode.E) {
            this.setDrawMode(CellState.DEAD);
            System.out.println("Erase mode");
        }
    }

    private void handleDraw(MouseEvent mouseEvent) {

        if(!isDrawingEnabled) {
            return;
        }
        Point2D simCoordinate = this.getSimulationCoordinate(mouseEvent);

        int simX = (int) simCoordinate.getX();
        int simY = (int) simCoordinate.getY();

        System.out.println(simX + ", " + simY);

        this.initialBoard.setState(simX, simY, drawMode);
        this.boardViewModel.setBoard(this.initialBoard);
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

    public void draw(Board board) {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine)
        ;
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,450,450);

        this.drawSimulation(board);

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05);
        for (int x = 0; x <= board.getWidth(); x++) {
            g.strokeLine(x, 0, x, 10);
        }
        for (int y = 0; y <= board.getHeight(); y++) {
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

    public void setDrawMode(CellState newDrawMode) {
        this.drawMode = newDrawMode;
        this.infoBar.setDrawMode(newDrawMode);
    }
}
