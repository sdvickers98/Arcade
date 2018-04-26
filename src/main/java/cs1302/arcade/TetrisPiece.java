package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrisPiece extends Rectangle {

    private static final int PIECE_SIZE = 50;
    private int col;
    private int row;

    public TetrisPiece(Color color) {
	super(PIECE_SIZE, PIECE_SIZE, color);
    } // Piece

    public void setGridPosition(int row, int col) {
	this.col = col;
	this.row = row;
	GridPane.setConstraints(this, col, row);
    } // setGridPosition
    
} // Piece
