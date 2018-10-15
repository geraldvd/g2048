package com.haaivda.g2048.gui;

import com.haaivda.g2048.engine.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainDesktop extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Board board = Board.createInitialBoard();
        primaryStage.setTitle("g2048");
        VBox layout = new VBox();
        GridPane playingTable = new PlayingTable(board, 400, 400);
        Text scoreField = new Text("Current Score: " + Integer.toString(board.getScore()));
        layout.getChildren().add(playingTable);
        layout.getChildren().add(scoreField);
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
