package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrisPiece extends Rectangle {

    private static final int PIECE_SIZE = 20;

    public TetrisPiece(Color color) {
	super(PIECE_SIZE, PIECE_SIZE, color);
    } // Piece

    public void setGridPosition(int row, int col) {
	GridPane.setConstraints(this, col, row);
    } // setGridPosition
    
} // Piece
