package com.haaivda.g2048.gui;

import com.haaivda.g2048.engine.Board;
import com.haaivda.g2048.engine.Tile;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

class PlayingTable extends GridPane {
    PlayingTable(Board board, int width, int height) {
        super();
        for(int y = 0; y < board.getNumRows(); y++) {
            for(int x = 0; x < board.getNumCols(); x++) {
                Tile currentTile = board.getTile(x, y);
                Text text;
                if(currentTile != null) {
                    text = new Text(currentTile.toString());
                } else {
                    text = new Text();
                }
                StackPane s = new StackPane();
                Rectangle r = new Rectangle((int)(width / board.getNumCols()), (int)(height / board.getNumRows()));
                r.setStroke(Color.BLACK);
                r.setFill(Color.WHITE);
                s.getChildren().addAll(r, text);
                this.add(s, x, y);
            }
        }
    }
}
