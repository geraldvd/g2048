package com.haaivda.g2048.gui;

import com.haaivda.g2048.engine.Game;
import com.haaivda.g2048.engine.Move;
import com.haaivda.g2048.engine.Tile;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MainDesktop extends Application {
    private Game game;
    private Stage window;
    private Group rootGroup;

    private final static Map<Integer,Color> TILE_COLORS = initializeTileColors();
    private final static int BOARD_WIDTH = 400;
    private final static int BOARD_HEIGTH = 400;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize game
         this.game = new Game();

        // Window settings
        this.window = primaryStage;
        this.window.setTitle("g2048");

        // Add menu bar
        MenuBar menuBar = this.createMenuBar();


        // Set main scene
        VBox layout = new VBox();
        this.rootGroup = new Group();
        BorderPane menuLayout = new BorderPane();
        menuLayout.setTop(menuBar);
        layout.getChildren().addAll(menuLayout, this.rootGroup);
        Scene mainScene = new Scene(layout);
        window.setScene(mainScene);

        // Draw scene
        this.rootGroup.getChildren().add(this.drawTilePanel());

        // Setup key listeners
        mainScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    this.game.makeMove(Move.UP);
                    break;
                case DOWN:
                    this.game.makeMove(Move.DOWN);
                    break;
                case LEFT:
                    this.game.makeMove(Move.LEFT);
                    break;
                case RIGHT:
                    this.game.makeMove(Move.RIGHT);
                    break;
            }
            this.rootGroup.getChildren().setAll(this.drawTilePanel());
            event.consume();
        });


        // Show window
        this.window.show();
    }

    private static Map<Integer, Color> initializeTileColors() {
        Map<Integer, Color> tileColors = new HashMap<>();
        tileColors.put(0, Color.WHITE);
        tileColors.put(2, Color.GRAY);
        tileColors.put(4, Color.TURQUOISE);
        tileColors.put(8, Color.BROWN);
        tileColors.put(16, Color.MAGENTA);
        tileColors.put(32, Color.YELLOW);
        tileColors.put(64, Color.CYAN);
        tileColors.put(128, Color.GREEN);
        tileColors.put(256, Color.ORANGE);
        tileColors.put(512, Color.LIME);
        tileColors.put(1024, Color.NAVY);
        tileColors.put(2048, Color.PINK);

        return tileColors;
    }

    private Pane drawTilePanel() {
        // Vertical layout for tiles and score
        VBox verticalBoxLayout = new VBox();

        // Draw tile panel
        GridPane gridPane = new GridPane();
        for(int y = 0; y < this.game.getBoard().getNumRows(); y++) {
            for(int x = 0; x < this.game.getBoard().getNumCols(); x++) {
                Tile currentTile = this.game.getBoard().getTile(x, y);
                StackPane stackPane = new StackPane();

                // Draw rectangle
                Rectangle rectangle = new Rectangle((int)(BOARD_WIDTH / this.game.getBoard().getNumCols()), (int)(BOARD_HEIGTH / this.game.getBoard().getNumRows()));
                if(currentTile == null) {
                    rectangle.setFill(TILE_COLORS.get(0));
                } else {
                    rectangle.setFill(TILE_COLORS.get(currentTile.getValue()));
                }

                // Draw text
                Text text = new Text();
                if(currentTile != null) {
                    text.setText(currentTile.toString());
                    Font font = Font.font("Calibri", FontWeight.EXTRA_BOLD, 48);
                    text.setFont(font);
                    text.setStroke(Color.GHOSTWHITE);
                    text.setFill(Color.GHOSTWHITE);
                }

                stackPane.getChildren().addAll(rectangle, text);
                gridPane.add(stackPane, x, y);
            }
        }

        // Set score field
        Text scoreField = new Text();
        scoreField.setText("Current Score: " + Integer.toString(this.game.getBoard().getScore()));
        Font font = Font.font("Calibri", FontWeight.BOLD, 20);
        scoreField.setFont(font);
        scoreField.setFill(Color.BLACK);

        // Check game over
        StackPane gameOverStackPane = new StackPane();
        if(this.game.getBoard().gameOver()) {
            // Partly transparent background
            Rectangle rectangle = new Rectangle(BOARD_WIDTH, BOARD_HEIGTH, Color.rgb(200, 200, 200, 0.5));

            // Text field
            Text gameOverField = new Text();
            gameOverField.setText("GAME OVER!");
            gameOverField.setFont(Font.font("Calibri", FontWeight.EXTRA_BOLD, 72));
            gameOverField.setFill(Color.rgb(180, 50, 50));

            // Fill stack pane
            gameOverStackPane.getChildren().setAll(rectangle, gameOverField);
        }

        // Stack pane to show game over message
        StackPane mainStack = new StackPane();
        mainStack.getChildren().setAll(gridPane, gameOverStackPane);

        // Add tiles and score to layout
        verticalBoxLayout.getChildren().setAll(mainStack, scoreField);

        return verticalBoxLayout;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Game menu
        Menu gameMenu = new Menu("_Game");
        MenuItem newGame = new MenuItem("_New Game");
        newGame.setOnAction(e -> {
            game.newGame();
            this.rootGroup.getChildren().setAll(this.drawTilePanel());
        });
        MenuItem undoMove = new MenuItem("Undo Move");
        undoMove.setOnAction(e -> {
            this.game.undo();
            this.rootGroup.getChildren().setAll(this.drawTilePanel());
        });
        MenuItem exitGame = new MenuItem("Exit");
        exitGame.setOnAction(e -> this.window.close());
        gameMenu.getItems().addAll(newGame, undoMove, new SeparatorMenuItem(), exitGame);

        // Construct menu bar
        menuBar.getMenus().addAll(gameMenu);
        return menuBar;
    }
}