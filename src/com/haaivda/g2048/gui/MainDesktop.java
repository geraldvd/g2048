package com.haaivda.g2048.gui;

import com.haaivda.g2048.engine.Game;
import com.haaivda.g2048.engine.Move;
import com.haaivda.g2048.engine.Tile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MainDesktop extends Application {
    private Game game;
    private Stage window;
    private TileStackPane[][] graphicalTiles;
    private Text scoreField;
    private StackPane gameOverStackPane;

    private final static Map<Integer,Color> TILE_COLORS = initializeTileColors();
    private final static int BOARD_WIDTH = 400;
    private final static int BOARD_HEIGHT = 400;
    final static int TILE_WIDTH = 100;
    final static int TILE_HEIGHT = 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.game = new Game();
        MenuBar menuBar = this.createMenuBar();
        VBox layout = new VBox();
        BorderPane menuLayout = new BorderPane();
        menuLayout.setTop(menuBar);
        layout.getChildren().addAll(menuLayout, this.drawInitialTilePanel());
        Scene mainScene = new Scene(layout);
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
            this.updateTiles();
            event.consume();
        });
        this.window = primaryStage;
        this.window.setTitle("g2048");
        window.setScene(mainScene);
        this.updateTiles();
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

    private Pane drawInitialTilePanel() {
        VBox verticalBoxLayout = new VBox();

        // Draw tile panel
        GridPane gridPane = new GridPane();
        this.graphicalTiles = new TileStackPane[this.game.getBoard().getNumRows()][this.game.getBoard().getNumCols()];
        for(int y = 0; y < this.game.getBoard().getNumRows(); y++) {
            for(int x = 0; x < this.game.getBoard().getNumCols(); x++) {
                graphicalTiles[y][x] = new TileStackPane();
                gridPane.add(graphicalTiles[y][x], x, y);
            }
        }

        // Draw score field
        this.scoreField = new Text();
        scoreField.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
        scoreField.setFill(Color.BLACK);

        // Draw game over pane
        this.gameOverStackPane = new StackPane();
        Rectangle rectangle = new Rectangle(BOARD_WIDTH, BOARD_HEIGHT, Color.rgb(200, 200, 200, 0.5));
        Text gameOverField = new Text();
        gameOverField.setText("GAME OVER!");
        gameOverField.setFont(Font.font("Calibri", FontWeight.EXTRA_BOLD, 72));
        gameOverField.setFill(Color.rgb(180, 50, 50));
        gameOverStackPane.getChildren().setAll(rectangle, gameOverField);

        StackPane mainStack = new StackPane();
        mainStack.getChildren().setAll(gridPane, this.gameOverStackPane);
        verticalBoxLayout.getChildren().setAll(mainStack, this.scoreField);
        return verticalBoxLayout;
    }

    private void updateTiles() {
        for(int y = 0; y < this.game.getBoard().getNumRows(); y++) {
            for (int x = 0; x < this.game.getBoard().getNumCols(); x++) {
                Tile currentTile = this.game.getBoard().getTile(x, y);
                if(currentTile == null) {
                    this.graphicalTiles[y][x].updateTile("", TILE_COLORS.get(0));
                } else {
                    this.graphicalTiles[y][x].updateTile(this.game.getBoard().getTile(x, y).toString(),
                            TILE_COLORS.get(this.game.getBoard().getTile(x, y).getValue()) );
                }
            }
        }
        this.scoreField.setText("Current Score: " + Integer.toString(this.game.getBoard().getScore()));
        this.gameOverStackPane.setVisible(this.game.getBoard().gameOver());
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Game menu
        Menu gameMenu = new Menu("_Game");
        MenuItem newGame = new MenuItem("_New Game");
        newGame.setOnAction(e -> {
            game.newGame();
            this.updateTiles();
        });
        MenuItem undoMove = new MenuItem("Undo Move");
        undoMove.setOnAction(e -> {
            this.game.undo();
            this.updateTiles();
        });
        MenuItem exitGame = new MenuItem("Exit");
        exitGame.setOnAction(e -> this.window.close());
        gameMenu.getItems().addAll(newGame, undoMove, new SeparatorMenuItem(), exitGame);

        // Construct menu bar
        menuBar.getMenus().addAll(gameMenu);
        return menuBar;
    }
}