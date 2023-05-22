package com.snook.gol;

import com.snook.gol.model.Board;
import com.snook.gol.model.BoundedBoard;
import com.snook.gol.view.SimulationCanvas;
import com.snook.gol.viewmodel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        Board board = new BoundedBoard(20, 12);

        ApplicationViewModel appViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel = new BoardViewModel();
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, board);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel);

        appViewModel.listenToAppState(editorViewModel::onAppStateChanged);
        appViewModel.listenToAppState(simulationViewModel::onAppStateChanged);

        boardViewModel.setBoard(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(editorViewModel, boardViewModel);
        Toolbar toolbar = new Toolbar(editorViewModel, appViewModel, simulationViewModel);
        InfoBar infoBar = new InfoBar(editorViewModel);


        MainView mainView = new MainView(editorViewModel);

        mainView.setTop(toolbar);
        mainView.setCenter(simulationCanvas);
        mainView.setBottom(infoBar);

        Scene scene = new Scene(mainView, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}