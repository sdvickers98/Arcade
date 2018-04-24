package cs1302.arcade;

import javafx.scene.paint.Color;

public class Tetromino {

    public enum Shape{
	I, J, L, O, S, T, Z;
    }

    public Piece[] pieces = new Piece[4];

    public Tetromino(Shape shape) {
	if (shape = Shape.I) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new Piece(Color.CYAN);
	    }
	} else if (shape = Shape.J) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new Piece(Color.BLUE);
	    }
	} else if (shape = Shape.L) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new Piece(Color.ORANGE);
	    }
	} else if (shape = Shape.O) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new Piece(Color.YELLOW);
	    }
	} else if (shape = Shape.S) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new Piece(Color.LAWNGREEN);
	    }
	} else if (shape = Shape.T) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new Piece(Color.MEDIUMPURPLE);
	    }
	} else if (shape = Shape.Z) {
	    for (int i = 0; i < pieces.length; i++) {
		pieces[i] = new Piece(Color.RED);
	    }
	} else {
	    System.err.println("Invalid Tetromino Shape entered");
	}
    } // Tetromino

} // Tetromino