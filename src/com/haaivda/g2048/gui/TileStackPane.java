package com.haaivda.g2048.gui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static com.haaivda.g2048.gui.MainDesktop.TILE_HEIGHT;
import static com.haaivda.g2048.gui.MainDesktop.TILE_WIDTH;

class TileStackPane extends StackPane {
    private final Rectangle rectangle;
    private final Text tileText;

    TileStackPane() {
        super();
        this.rectangle = new Rectangle(TILE_WIDTH, TILE_HEIGHT);
        this.rectangle.setArcHeight(15);
        this.rectangle.setArcWidth(15);
        this.tileText = new Text();
        this.tileText.setFont(Font.font("Calibri", FontWeight.EXTRA_BOLD, 48));
        this.tileText.setStroke(Color.GHOSTWHITE);
        this.tileText.setFill(Color.GHOSTWHITE);
        this.getChildren().addAll(this.rectangle, this.tileText);
    }

    void updateTile(String tileText, Color tileColor) {
        this.tileText.setText(tileText);
        this.rectangle.setFill(tileColor);
    }
}
