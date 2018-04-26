package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Tetromino {

    public enum Shape{
	I, J, L, O, S, T, Z;
    }

    public TetrisPiece[] pieces = new TetrisPiece[4];

    public Tetromino(Shape shape) {
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

} // Tetromino
