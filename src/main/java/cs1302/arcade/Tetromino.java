package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Tetromino {

    public static enum Shape{
	I, J, L, O, S, T, Z;
    }

    private Shape shape;
    private TetrisPiece[] pieces = new TetrisPiece[4];

    /**
     *
     */
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

    /**
     *
     */
    public TetrisPiece[] getPieces() { return pieces; } // getPieces
    
    /**
     *
     */
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

    /**
     * Rotates the tetromino counter-clockwise around a piece that is specific to the tetromino's shape, if the 
     * rotation is valid. A valid rotation is one that would not place any tetromino pieces outside of the grid or 
     * in grid cells already occupied by a tetromino piece.
     *
     * @param inTheWay a 2D boolean array that tracks the positions of other tetromino pieces on the grid
     * @return whether the rotation was successful or not
     */
    public boolean rotate(boolean[][] inTheWay) {
	
	if (shape == Shape.I) {
	    if (pieces[0].getRow() == pieces[1].getRow()) {
		if (pieces[1].getRow() > 18 || pieces[1].getRow() < 2 || inTheWay[pieces[0].getRow() + 1][pieces[0].getCol() + 1] || 
		    inTheWay[pieces[2].getRow() -1][pieces[2].getCol() - 1] || inTheWay[pieces[3].getRow() - 2][pieces[3].getCol() - 2])
		    return false;
		else {
		    pieces[0].setGridPosition(pieces[0].getRow() + 1, pieces[0].getCol() + 1);
		    pieces[2].setGridPosition(pieces[2].getRow() - 1, pieces[2].getCol() - 1);
		    pieces[3].setGridPosition(pieces[3].getRow() - 2, pieces[3].getCol() - 2);
		}		    
	    } else {
		if (pieces[1].getCol() > 7 || pieces[1].getCol() < 1 || inTheWay[pieces[0].getRow() - 1][pieces[0].getCol() - 1] ||
		    inTheWay[pieces[2].getRow() + 1][pieces[2].getCol() + 1] || inTheWay[pieces[3].getRow() + 2][pieces[3].getCol() + 2])
		    return false;
		else {
		    pieces[0].setGridPosition(pieces[0].getRow() - 1, pieces[0].getCol() - 1);
		    pieces[2].setGridPosition(pieces[2].getRow() + 1, pieces[2].getCol() + 1);
		    pieces[3].setGridPosition(pieces[3].getRow() + 2, pieces[3].getCol() + 2);
		} 
	    }
     	} else if (shape == Shape.J) {
	    if (pieces[2].getRow() == pieces[3].getRow()) {
		if (pieces[1].getRow() > pieces[2].getRow()) {
		    if (pieces[2].getRow() < 1 || pieces[2].getCol() > 7 || inTheWay[pieces[0].getRow() - 2][pieces[0].getCol() + 2] ||
			inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() + 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() - 1])
			return false;
		    else {
			pieces[0].setGridPosition(pieces[0].getRow() - 2, pieces[0].getCol() + 2);
			pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() + 1);
			pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() - 1);
		    }
		} else {
		    if (pieces[2].getRow() > 18 || pieces[2].getCol() < 2 || inTheWay[pieces[0].getRow() + 2][pieces[0].getCol() - 2] ||
			inTheWay[pieces[1].getRow() + 1][pieces[1].getCol() - 1] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() + 1])
			return false;
		    else {
			pieces[0].setGridPosition(pieces[0].getRow() + 2, pieces[0].getCol() - 2);
			pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() - 1);
			pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() + 1);
		    }
		}
	    } else {
		if (pieces[3].getRow() > pieces[2].getRow()) {
		    if (pieces[2].getRow() > 17 || pieces[2].getCol() > 8 || inTheWay[pieces[0].getRow() + 2][pieces[0].getCol() + 2] ||
			inTheWay[pieces[1].getRow() + 1][pieces[1].getCol() + 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() + 1])
			return false;
		    else {
			pieces[0].setGridPosition(pieces[0].getRow() + 2, pieces[0].getCol() + 2);
			pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() + 1);
			pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() + 1);
		    }
		} else {
		    if (pieces[2].getRow() < 2 || pieces[2].getCol() < 1 || inTheWay[pieces[0].getRow() - 2][pieces[0].getCol() - 2] ||
			inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() - 1] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() -1])
			return false;
		    else {
			pieces[0].setGridPosition(pieces[0].getRow() - 2, pieces[0].getCol() - 2);
			pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() - 1);
			pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() - 1);
		    }
		}
	    }
	} else if (shape == Shape.L) {
	    if (pieces[0].getRow() == pieces[3].getRow()) {
		if (pieces[0].getRow() > pieces[1].getRow()) {
		    if (pieces[0].getCol() < 2 || inTheWay[pieces[1].getRow() + 1][pieces[1].getCol() - 1] ||
			inTheWay[pieces[2].getRow() + 2][pieces[2].getCol() - 2] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() - 1])
			return false;
		    else {
			pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() - 1);
			pieces[2].setGridPosition(pieces[2].getRow() + 2, pieces[2].getCol() - 2);
			pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() - 1);
		    }
		} else {
		    if (pieces[0].getCol() > 7 || inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() + 1] ||
			inTheWay[pieces[2].getRow() - 2][pieces[2].getCol() + 2] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() + 1])
			return false;
		    else {
			pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() + 1);
			pieces[2].setGridPosition(pieces[2].getRow() - 2, pieces[2].getCol() + 2);
			pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() + 1);
		    }
		}
	    } else {
		if (pieces[3].getRow() > pieces[0].getRow()) {
		    if (pieces[0].getRow() < 2 || inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() - 1] ||
			inTheWay[pieces[2].getRow() - 2][pieces[2].getCol() - 2] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() + 1])
			return false;
		    else {
			pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() - 1);
			pieces[2].setGridPosition(pieces[2].getRow() - 2, pieces[2].getCol() - 2);
			pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() + 1);
		    }
		} else {
		    if (pieces[2].getRow() > 17 || inTheWay[pieces[1].getRow() + 1][pieces[1].getCol() + 1] ||
			inTheWay[pieces[2].getRow() + 2][pieces[2].getCol() + 2] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() -1])
			return false;
		    else {
			pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() + 1);
			pieces[2].setGridPosition(pieces[2].getRow() + 2, pieces[2].getCol() + 2);
			pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() - 1);
		    }
		}
	    }
	} else if (shape == Shape.O) {
	    return true;
	} else if (shape == Shape.S) {
	    if (pieces[1].getRow() == pieces[2].getRow()) {
		if (pieces[2].getCol() > 8 || pieces[2].getCol() < 1 || inTheWay[pieces[0].getRow()][pieces[0].getCol() - 2] || 
		    inTheWay[pieces[1].getRow() + 1][pieces[1].getCol() - 1] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() + 1])
		    return false;
		else {
		    pieces[0].setGridPosition(pieces[0].getRow(), pieces[0].getCol() - 2);
		    pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() - 1);
		    pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() + 1);
		}		    
	    } else {
		if (pieces[2].getRow() > 18 || pieces[2].getRow() < 1 || inTheWay[pieces[0].getRow()][pieces[0].getCol() + 2] ||
		    inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() + 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() - 1])
		    return false;
		else {
		    pieces[0].setGridPosition(pieces[0].getRow(), pieces[0].getCol() + 2);
		    pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() + 1);
		    pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() - 1);
		} 
	    }
	} else if (shape == Shape.T) {
	    if (pieces[0].getRow() == pieces[1].getRow()) {
		if (pieces[3].getRow() > pieces[1].getRow()) {
		    if (pieces[1].getRow() < 1 || inTheWay[pieces[0].getRow() + 1][pieces[0].getCol() + 1] ||
			inTheWay[pieces[2].getRow() - 1][pieces[2].getCol() - 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() + 1])
			return false;
		    else {
			pieces[0].setGridPosition(pieces[0].getRow() + 1, pieces[0].getCol() + 1);
			pieces[2].setGridPosition(pieces[2].getRow() - 1, pieces[2].getCol() - 1);
			pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() + 1);
		    }
		} else {
		    if (pieces[1].getRow() > 18 || inTheWay[pieces[0].getRow() - 1][pieces[0].getCol() - 1] ||
			inTheWay[pieces[2].getRow() + 1][pieces[2].getCol() + 1] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() - 1])
			return false;
		    else {
			pieces[0].setGridPosition(pieces[0].getRow() - 1, pieces[0].getCol() - 1);
			pieces[2].setGridPosition(pieces[2].getRow() + 1, pieces[2].getCol() + 1);
			pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() - 1);
		    }
		}
	    } else {
		if (pieces[1].getRow() > pieces[2].getRow()) {
		    if (pieces[1].getCol() < 1 || inTheWay[pieces[0].getRow() - 1][pieces[0].getCol() + 1] ||
			inTheWay[pieces[2].getRow() + 1][pieces[2].getCol() - 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() - 1])
			return false;
		    else {
			pieces[0].setGridPosition(pieces[0].getRow() - 1, pieces[0].getCol() + 1);
			pieces[2].setGridPosition(pieces[2].getRow() + 1, pieces[2].getCol() - 1);
			pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() - 1);
		    }
		} else {
		    if (pieces[1].getCol() > 8 || inTheWay[pieces[0].getRow() + 1][pieces[1].getCol() - 1] ||
			inTheWay[pieces[2].getRow() - 1][pieces[2].getCol() + 1] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() + 1])
			return false;
		    else {
			pieces[0].setGridPosition(pieces[0].getRow() + 1, pieces[0].getCol() - 1);
			pieces[2].setGridPosition(pieces[2].getRow() - 1, pieces[2].getCol() + 1);
			pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() + 1);
		    }
		}
	    } // end of T 
	} else {
	     if (pieces[1].getRow() == pieces[2].getRow()) {
		if (pieces[1].getCol() > 8 || pieces[1].getCol() < 1 || inTheWay[pieces[0].getRow() - 1][pieces[0].getCol() - 1] || 
		    inTheWay[pieces[2].getRow() + 1][pieces[2].getCol() - 1] || inTheWay[pieces[3].getRow() + 2][pieces[3].getCol()])
		    return false;
		else {
		    pieces[0].setGridPosition(pieces[0].getRow() - 1, pieces[0].getCol() - 1);
		    pieces[2].setGridPosition(pieces[2].getRow() + 1, pieces[2].getCol() - 1);
		    pieces[3].setGridPosition(pieces[3].getRow() + 2, pieces[3].getCol());
		}		    
	    } else {
		if (pieces[1].getRow() > 18 || pieces[1].getRow() < 1 || inTheWay[pieces[0].getRow() + 1][pieces[0].getCol() + 1] ||
		    inTheWay[pieces[2].getRow() - 1][pieces[2].getCol() + 1] || inTheWay[pieces[3].getRow() - 2][pieces[3].getCol()])
		    return false;
		else {
		    pieces[0].setGridPosition(pieces[0].getRow() + 1, pieces[0].getCol() + 1);
		    pieces[2].setGridPosition(pieces[2].getRow() - 1, pieces[2].getCol() + 1);
		    pieces[3].setGridPosition(pieces[3].getRow() - 2, pieces[3].getCol());
		} 
	    }
	}
	// rotates each piece in the Tetromino based on its shape if it is valid

	return true;
    } // rotate

} // Tetromino
