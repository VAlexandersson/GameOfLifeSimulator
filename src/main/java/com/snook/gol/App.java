package com.snook.gol;

import com.snook.gol.model.Board;
import com.snook.gol.model.BoundedBoard;
import com.snook.gol.viewmodel.ApplicationState;
import com.snook.gol.viewmodel.ApplicationViewModel;
import com.snook.gol.viewmodel.BoardViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        ApplicationViewModel appViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel = new BoardViewModel();

        Board board = new BoundedBoard(10, 10);

        MainView mainView = new MainView(appViewModel, boardViewModel, board);
        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();

        boardViewModel.setBoard(board);
    }

    public static void main(String[] args) {
        launch();
    }

}