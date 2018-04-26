package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Tetromino {

    public static enum Shape{
	I, J, L, O, S, T, Z;
    }

    private Shape shape;
    private TetrisPiece[] pieces = new TetrisPiece[4];

    public Tetromino(Shape shape) {
	this.shape = shape;

	if (shape == Shape.I) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new TetrisPiece(Color.CYAN);
	    }
	} else if (shape == Shape.J) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new TetrisPiece(Color.BLUE);
	    }
	} else if (shape == Shape.L) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new TetrisPiece(Color.ORANGE);
	    }
	} else if (shape == Shape.O) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new TetrisPiece(Color.YELLOW);
	    }
	} else if (shape == Shape.S) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new TetrisPiece(Color.LAWNGREEN);
	    }
	} else if (shape == Shape.T) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new TetrisPiece(Color.MEDIUMPURPLE);
	    }
	} else if (shape == Shape.Z) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new TetrisPiece(Color.RED);
	    }
	} else {
	    System.err.println("Invalid Tetromino Shape entered");
	}
    } // Tetromino

    public TetrisPiece[] getPieces() { return pieces; } // getPieces
    
    public void setInitialGridPosition() {
	if (shape == Shape.I) {
	    pieces[0].setGridPosition(1, 3);
	    pieces[1].setGridPosition(1, 4);
	    pieces[2].setGridPosition(1, 5);
	    pieces[3].setGridPosition(1, 6);
	} else if (shape == Shape.J) {
	    pieces[0].setGridPosition(0, 3);
	    pieces[1].setGridPosition(0, 4);
	    pieces[2].setGridPosition(0, 5);
	    pieces[3].setGridPosition(1, 5);
	} else if (shape == Shape.L) {
	    pieces[0].setGridPosition(0, 3);
	    pieces[1].setGridPosition(0, 4);
	    pieces[2].setGridPosition(0, 5);
	    pieces[3].setGridPosition(1, 3);
	} else if (shape == Shape.O) {
	    pieces[0].setGridPosition(0, 4);
	    pieces[1].setGridPosition(0, 5);
	    pieces[2].setGridPosition(1, 4);
	    pieces[3].setGridPosition(1, 5);
	} else if (shape == Shape.S) {
	    pieces[0].setGridPosition(1, 3);
	    pieces[1].setGridPosition(1, 4);
	    pieces[2].setGridPosition(0, 4);
	    pieces[3].setGridPosition(0, 5);
	} else if (shape == Shape.T) {
	    pieces[0].setGridPosition(0, 3);
	    pieces[1].setGridPosition(0, 4);
	    pieces[2].setGridPosition(0, 5);
	    pieces[3].setGridPosition(1, 4);
	} else {
	    pieces[0].setGridPosition(0, 3);
	    pieces[1].setGridPosition(0, 4);
	    pieces[2].setGridPosition(1, 4);
	    pieces[3].setGridPosition(1, 5);
	}
	// sets the initial position for each piece in the Tetromino based on its shape

    } // setInitialGridPosition

} // Tetromino
