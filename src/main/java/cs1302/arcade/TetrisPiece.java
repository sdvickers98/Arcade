package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * One single cube that is used to make up a tetromino.
 */
public class TetrisPiece extends Rectangle {

    private static final int PIECE_SIZE = 30;
    private int col;
    private int row;

    /**
     * Creates a new piece that is the specified color.
     *
     * @param color the color to fill the piece with
     */
    public TetrisPiece(Color color) {
	super(PIECE_SIZE, PIECE_SIZE, color);
    } // Piece

    /**
     * Sets the grid position of this piece.
     *
     * @param row the row to put the piece in
     * @param col the column to put the piece in
     */
    public void setGridPosition(int row, int col) {
	this.col = col;
	this.row = row;
	GridPane.setConstraints(this, col, row);
    } // setGridPosition

    /**
     * Returns the row of this piece.
     *
     * @return the row of this piece
     */
    public int getRow() { return row; } // getRow

    /**
     * Returns the column of this piece.
     *
     * @return the column of this piece
     */
    public int getCol() { return col; } // getCol
    
} // Piece
