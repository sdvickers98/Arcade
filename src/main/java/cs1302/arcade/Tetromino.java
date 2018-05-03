package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Represents a single tetromino. Includes the rotate method for a tetromino.
 */
public class Tetromino {

    public static enum Shape{
	I, J, L, O, S, T, Z;
    }

    private Shape shape;
    private TetrisPiece[] pieces = new TetrisPiece[4];

	/**
	 * Creates four new pieces to be arranged into the desired shape. The shape also determines
	 * what color the tetromino is.
	 *
	 * @param shape the desired shape of the tetromino
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
	 * Gets the pieces in this tetromino.
	 *
	 * @return an array of the pieces in this tetromino
	 */
	public TetrisPiece[] getPieces() { return pieces; } // getPieces
    
    /**
     * Sets the initial grid position of each piece in the tetromino, based on the tetromino's
	 * shape.
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
     * in grid cells already occupied by a tetromino piece. Rotations are subjective to the piece's shape and
	 * orientation before the rotation takes place.
     *
     * @param inTheWay a 2D boolean array that tracks the positions of other tetromino pieces on the grid
     * @return whether the rotation was successful or not
     */
    public boolean rotate(boolean[][] inTheWay, boolean tracker) {
	
		if (shape == Shape.I) {
			if (pieces[0].getRow() == pieces[1].getRow()) {
			if (pieces[1].getRow() > 18 || pieces[1].getRow() < 2 || inTheWay[pieces[0].getRow() + 1][pieces[0].getCol() + 1] ||
				inTheWay[pieces[2].getRow() -1][pieces[2].getCol() - 1] || inTheWay[pieces[3].getRow() - 2][pieces[3].getCol() - 2])
				return false;
			else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() + 1, pieces[0].getCol() + 1);
				pieces[2].setGridPosition(pieces[2].getRow() - 1, pieces[2].getCol() - 1);
				pieces[3].setGridPosition(pieces[3].getRow() - 2, pieces[3].getCol() - 2);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
			}
			} else {
			if (pieces[1].getCol() > 7 || pieces[1].getCol() < 1 || inTheWay[pieces[0].getRow() - 1][pieces[0].getCol() - 1] ||
				inTheWay[pieces[2].getRow() + 1][pieces[2].getCol() + 1] || inTheWay[pieces[3].getRow() + 2][pieces[3].getCol() + 2])
				return false;
			else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() - 1, pieces[0].getCol() - 1);
				pieces[2].setGridPosition(pieces[2].getRow() + 1, pieces[2].getCol() + 1);
				pieces[3].setGridPosition(pieces[3].getRow() + 2, pieces[3].getCol() + 2);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
			}
			}
			} else if (shape == Shape.J) {
			if (pieces[2].getRow() == pieces[3].getRow()) {
			if (pieces[1].getRow() > pieces[2].getRow()) {
				if (pieces[2].getRow() < 1 || pieces[2].getCol() > 7 || inTheWay[pieces[0].getRow() - 2][pieces[0].getCol() + 2] ||
				inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() + 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() - 1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() - 2, pieces[0].getCol() + 2);
				pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() + 1);
				pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() - 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			} else {
				if (pieces[2].getRow() > 18 || pieces[2].getCol() < 2 || inTheWay[pieces[0].getRow() + 2][pieces[0].getCol() - 2] ||
				inTheWay[pieces[1].getRow() + 1][pieces[1].getCol() - 1] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() + 1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() + 2, pieces[0].getCol() - 2);
				pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() - 1);
				pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() + 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			}
			} else {
			if (pieces[3].getRow() > pieces[2].getRow()) {
				if (pieces[2].getRow() > 17 || pieces[2].getCol() > 8 || inTheWay[pieces[0].getRow() + 2][pieces[0].getCol() + 2] ||
				inTheWay[pieces[1].getRow() + 1][pieces[1].getCol() + 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() + 1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() + 2, pieces[0].getCol() + 2);
				pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() + 1);
				pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() + 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			} else {
				if (pieces[2].getRow() < 2 || pieces[2].getCol() < 1 || inTheWay[pieces[0].getRow() - 2][pieces[0].getCol() - 2] ||
				inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() - 1] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() -1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() - 2, pieces[0].getCol() - 2);
				pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() - 1);
				pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() - 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
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
				if (tracker) {
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() - 1);
				pieces[2].setGridPosition(pieces[2].getRow() + 2, pieces[2].getCol() - 2);
				pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() - 1);

				if (tracker) {
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			} else {
				if (pieces[0].getCol() > 7 || inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() + 1] ||
				inTheWay[pieces[2].getRow() - 2][pieces[2].getCol() + 2] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() + 1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() + 1);
				pieces[2].setGridPosition(pieces[2].getRow() - 2, pieces[2].getCol() + 2);
				pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() + 1);

				if (tracker) {
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			}
			} else {
			if (pieces[3].getRow() > pieces[0].getRow()) {
				if (pieces[0].getRow() < 2 || inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() - 1] ||
				inTheWay[pieces[2].getRow() - 2][pieces[2].getCol() - 2] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() + 1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() - 1);
				pieces[2].setGridPosition(pieces[2].getRow() - 2, pieces[2].getCol() - 2);
				pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() + 1);

				if (tracker) {
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			} else {
				if (pieces[2].getRow() > 17 || inTheWay[pieces[1].getRow() + 1][pieces[1].getCol() + 1] ||
				inTheWay[pieces[2].getRow() + 2][pieces[2].getCol() + 2] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() -1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() + 1);
				pieces[2].setGridPosition(pieces[2].getRow() + 2, pieces[2].getCol() + 2);
				pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() - 1);

				if (tracker) {
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
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
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow(), pieces[0].getCol() - 2);
				pieces[1].setGridPosition(pieces[1].getRow() + 1, pieces[1].getCol() - 1);
				pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() + 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
			}
			} else {
			if (pieces[2].getRow() > 18 || pieces[2].getRow() < 1 || inTheWay[pieces[0].getRow()][pieces[0].getCol() + 2] ||
				inTheWay[pieces[1].getRow() - 1][pieces[1].getCol() + 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() - 1])
				return false;
			else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow(), pieces[0].getCol() + 2);
				pieces[1].setGridPosition(pieces[1].getRow() - 1, pieces[1].getCol() + 1);
				pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() - 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[1].getRow()][pieces[1].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
			}
			}
		} else if (shape == Shape.T) {
			if (pieces[0].getRow() == pieces[1].getRow()) {
			if (pieces[3].getRow() > pieces[1].getRow()) {
				if (pieces[1].getRow() < 1 || inTheWay[pieces[0].getRow() + 1][pieces[0].getCol() + 1] ||
				inTheWay[pieces[2].getRow() - 1][pieces[2].getCol() - 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() + 1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() + 1, pieces[0].getCol() + 1);
				pieces[2].setGridPosition(pieces[2].getRow() - 1, pieces[2].getCol() - 1);
				pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() + 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			} else {
				if (pieces[1].getRow() > 18 || inTheWay[pieces[0].getRow() - 1][pieces[0].getCol() - 1] ||
				inTheWay[pieces[2].getRow() + 1][pieces[2].getCol() + 1] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() - 1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() - 1, pieces[0].getCol() - 1);
				pieces[2].setGridPosition(pieces[2].getRow() + 1, pieces[2].getCol() + 1);
				pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() - 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			}
			} else {
			if (pieces[1].getRow() > pieces[2].getRow()) {
				if (pieces[1].getCol() < 1 || inTheWay[pieces[0].getRow() - 1][pieces[0].getCol() + 1] ||
				inTheWay[pieces[2].getRow() + 1][pieces[2].getCol() - 1] || inTheWay[pieces[3].getRow() - 1][pieces[3].getCol() - 1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() - 1, pieces[0].getCol() + 1);
				pieces[2].setGridPosition(pieces[2].getRow() + 1, pieces[2].getCol() - 1);
				pieces[3].setGridPosition(pieces[3].getRow() - 1, pieces[3].getCol() - 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			} else {
				if (pieces[1].getCol() > 8 || inTheWay[pieces[0].getRow() + 1][pieces[1].getCol() - 1] ||
				inTheWay[pieces[2].getRow() - 1][pieces[2].getCol() + 1] || inTheWay[pieces[3].getRow() + 1][pieces[3].getCol() + 1])
				return false;
				else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() + 1, pieces[0].getCol() - 1);
				pieces[2].setGridPosition(pieces[2].getRow() - 1, pieces[2].getCol() + 1);
				pieces[3].setGridPosition(pieces[3].getRow() + 1, pieces[3].getCol() + 1);

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
				}
			}
			} // end of T
		} else {
			 if (pieces[1].getRow() == pieces[2].getRow()) {
			if (pieces[1].getCol() > 8 || pieces[1].getCol() < 1 || inTheWay[pieces[0].getRow() - 1][pieces[0].getCol() - 1] ||
				inTheWay[pieces[2].getRow() + 1][pieces[2].getCol() - 1] || inTheWay[pieces[3].getRow() + 2][pieces[3].getCol()])
				return false;
			else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() - 1, pieces[0].getCol() - 1);
				pieces[2].setGridPosition(pieces[2].getRow() + 1, pieces[2].getCol() - 1);
				pieces[3].setGridPosition(pieces[3].getRow() + 2, pieces[3].getCol());

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
			}
			 } else {
			if (pieces[1].getRow() > 18 || pieces[1].getRow() < 1 || inTheWay[pieces[0].getRow() + 1][pieces[0].getCol() + 1] ||
				inTheWay[pieces[2].getRow() - 1][pieces[2].getCol() + 1] || inTheWay[pieces[3].getRow() - 2][pieces[3].getCol()])
				return false;
			else {
				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = false;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = false;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = false;
				}

				pieces[0].setGridPosition(pieces[0].getRow() + 1, pieces[0].getCol() + 1);
				pieces[2].setGridPosition(pieces[2].getRow() - 1, pieces[2].getCol() + 1);
				pieces[3].setGridPosition(pieces[3].getRow() - 2, pieces[3].getCol());

				if (tracker) {
				inTheWay[pieces[0].getRow()][pieces[0].getCol()] = true;
				inTheWay[pieces[2].getRow()][pieces[2].getCol()] = true;
				inTheWay[pieces[3].getRow()][pieces[3].getCol()] = true;
				}
			}
			}
		}
		// rotates each piece in the Tetromino based on its shape if it is valid

		return true;
    } // rotate

} // Tetromino
