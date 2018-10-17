package com.haaivda.g2048.gui;

import com.haaivda.g2048.engine.Board;
import com.haaivda.g2048.engine.Move;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainDesktop extends Application {
    private Board board;
    private Stage window;
    private Scene mainScene;
    private Pane mainLayout;
    private PlayingTable playingTable;
    private Text scoreField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize game
         this.board = Board.createInitialBoard();

        // Window settings
        this.window = primaryStage;
        this.window.setTitle("g2048");

        // Setup scene
        this.mainLayout = new VBox();
        this.playingTable = new PlayingTable(this.board, 400, 400);
        this.scoreField = new Text("Current Score: " + Integer.toString(this.board.getScore()));
        this.mainLayout.getChildren().addAll(playingTable, scoreField);
        this.mainScene = new Scene(this.mainLayout);

        // Setup key listeners
        this.mainScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    board = board.makeMove(Move.UP);
                    mainLayout.getChildren().removeAll();
                    mainLayout.getChildren().addAll(playingTable, scoreField);
                    window.setScene(mainScene);
                    break;
                case DOWN:
                    board = board.makeMove(Move.DOWN);
                    break;
                case LEFT:
                    board = board.makeMove(Move.LEFT);
                    break;
                case RIGHT:
                    board = board.makeMove(Move.RIGHT);
                    break;
            }
            event.consume();
        });

        this.window.setScene(this.mainScene);
        this.window.show();
    }
}
