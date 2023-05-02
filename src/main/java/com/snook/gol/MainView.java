package com.snook.gol;

import com.snook.gol.model.Board;
import com.snook.gol.model.CellState;
import com.snook.gol.viewmodel.ApplicationViewModel;
import com.snook.gol.viewmodel.BoardViewModel;
import com.snook.gol.viewmodel.EditorViewModel;
import com.snook.gol.viewmodel.SimulationViewModel;
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
    private Canvas canvas;

    private Affine affine;

    private final EditorViewModel editorViewModel;

    private final BoardViewModel boardViewModel;

    public MainView(ApplicationViewModel appViewModel, BoardViewModel boardViewModel, EditorViewModel editorViewModel, SimulationViewModel simulationViewModel) {

        this.boardViewModel = boardViewModel;
        this.editorViewModel = editorViewModel;

        this.boardViewModel.listenToBoard(this::onBoardChanged);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handledMoved);

        this.canvas.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(editorViewModel, appViewModel, simulationViewModel);

        this.infoBar = new InfoBar(editorViewModel);
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


    private void handledMoved(MouseEvent mouseEvent) {
        Point2D simCoordinate = this.getSimulationCoordinate(mouseEvent);
        this.infoBar.setCursorPosition((int)simCoordinate.getX(), (int)simCoordinate.getY());
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.D) {
            this.editorViewModel.setDrawMode(CellState.ALIVE);
        } else if(keyEvent.getCode() == KeyCode.E) {
            this.editorViewModel.setDrawMode(CellState.ALIVE);
        }
    }

    private void handleDraw(MouseEvent mouseEvent) {
        Point2D simCoordinate = this.getSimulationCoordinate(mouseEvent);

        int simX = (int) simCoordinate.getX();
        int simY = (int) simCoordinate.getY();

        System.out.println(simX + ", " + simY);

        this.editorViewModel.boardPressed(simX, simY);
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
}