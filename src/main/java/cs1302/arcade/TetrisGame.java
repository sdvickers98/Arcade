package cs1302.arcade;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 * The stage for the tetris game. Contains most of the methods behind how
 * the game works.
 */
public class TetrisGame extends Stage{

    private final Random rng = new Random();
    private final int CELL_SIZE = 30;
    private final int NUM_COLS = 10;
    private final int NUM_ROWS = 20;
    private final double MAX_LEVEL = 8.0;

    private enum Direction {
		UP, DOWN, LEFT, RIGHT
    }

    private Timeline timeline;
    private GridPane grid;
    private HBox root;
    private boolean hasStarted, paused, tracker;
    private boolean[][] pieceTracker;
    private TetrisPiece[][] pieceGrid;
    private TetrisPiece[] currentPieces;
    private int score, level, multiplier;
    private EventHandler<ActionEvent> pieceMovement;
    private Tetromino current;
    private Label scoreLabel, levelLabel, multLabel;

	/**
	 * Creates a stage and and sets up the game to be played.
	 */
	public TetrisGame(){
		super();
		init();
    } // start

	/**
	 * Initializes all necessary variables and nodes, and sets up the scene to be
	 * displayed in this stage.
	 */
    private void init(){
		initTimeline();

		hasStarted = false;
		score = 0;
		multiplier = 1;
		level = 1;
		// initializing important variables

		pieceTracker = new boolean[NUM_ROWS][NUM_COLS];
		pieceGrid = new TetrisPiece[NUM_ROWS][NUM_COLS];

		grid = new GridPane();
		grid.setGridLinesVisible(true);
		for(int i = 0; i < NUM_COLS ; i++){
			ColumnConstraints colConst = new ColumnConstraints(CELL_SIZE);
			grid.getColumnConstraints().add(colConst);
		}
		for(int i = 0; i < NUM_ROWS; i++){
			RowConstraints rowConst = new RowConstraints(CELL_SIZE);
			grid.getRowConstraints().add(rowConst);
		}
		// sets up the lines and constraints for the grid

		scoreLabel = new Label("Score: " + score);
		levelLabel = new Label("Level: " + level);
		multLabel = new Label("Multiplier: " + multiplier);

		Button playButton = new Button("Play");
		playButton.setMinWidth(75);
		playButton.setOnAction(e -> {
			if (!hasStarted) {
				playButton.setText("Pause");

				nextTetromino();
				hasStarted = true;
				paused = false;

				timeline.play();
			} else {
				if (playButton.getText().equals("Pause")) {
					playButton.setText("Resume");
					paused = true;
					timeline.pause();
				} else {
					playButton.setText("Pause");
					paused = false;
					timeline.play();
				}
			}
		});
		playButton.setOnKeyPressed(event -> {
			root.requestFocus();
			event.consume();
		});
		// creates a button that starts the game and can also be used to pause/resume the game

		Button closeButton = new Button("Close");
		closeButton.setMinWidth(75);
		closeButton.setOnAction(e -> checkClose());
		// creates a button to close the window

		VBox sideMenu = new VBox(20, scoreLabel, multLabel, levelLabel, playButton, closeButton);
		sideMenu.setMinWidth(150);
		sideMenu.setAlignment(Pos.CENTER);
		// creates the side menu

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				Rectangle rec = new Rectangle(CELL_SIZE, CELL_SIZE, Color.LIGHTGRAY);
				GridPane.setConstraints(rec, j, i);
				grid.getChildren().add(rec);
			}
		}
		// adds squares to the grid to indicate the "danger zone" at the top of the grid

		root = new HBox(sideMenu, grid);
		root.setOnKeyPressed(event -> {
			if (!paused) {
				if (event.getCode() == KeyCode.SPACE)
					current.rotate(pieceTracker, tracker);
				else if (event.getCode() == KeyCode.LEFT) {
					if (moveIsValid(current, Direction.LEFT))
						move(current, Direction.LEFT);
				} else if (event.getCode() == KeyCode.RIGHT) {
					if (moveIsValid(current, Direction.RIGHT))
						move(current, Direction.RIGHT);
				} else if (event.getCode() == KeyCode.DOWN) {
					if (moveIsValid(current, Direction.DOWN))
						move(current, Direction.DOWN);
				}
			}
		});
		root.requestFocus();
		// adds the event handler for key events to rotate and move pieces

		setOnCloseRequest(event -> {
			if (hasStarted)
				timeline.stop();
		});

		Scene game = new Scene(root);
		setScene(game);
    } // init

	/**
	 * Creates the timeline that makes the tetrominos fall down.
	 */
    private void initTimeline() {
		timeline = new Timeline();

		pieceMovement = event -> {
			if (moveIsValid(current, Direction.DOWN)) {
				move(current, Direction.DOWN);
			}
			// if the move is valid, the tetromino is shifted down by one

			else {
                for (TetrisPiece piece: currentPieces) {
                    pieceTracker[piece.getRow()][piece.getCol()] = true;
                    pieceGrid[piece.getRow()][piece.getCol()] = piece;
                }

                boolean check = false;
                for (int i = 0; i < NUM_COLS; i++) {
                	if (pieceTracker[1][i])
                		check = true;
				}

				if (check)
					lose();
                else {
					addToScore(20 * multiplier);

					if (!checkForRowClear()) {
						multiplier = 1;
						multLabel.setText("Multiplier: " + multiplier);
					}
					// if the player continues to clear at least one row for consecutive tetrominos,
					// the score multiplier increases, with a max of 4

					tracker = false;
					nextTetromino();
				}
			} // if the tetromino cannot fall any further, checks if the player has lost and/or
			// cleared a row
		};
		// the event handler that makes the current tetromino move down

		KeyFrame keyframe = new KeyFrame(Duration.seconds(1.0), pieceMovement);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyframe);
    } // initTimeline

	/**
	 * Checks if the tetromino can move in the specified direction.
	 *
	 * @param current the tetromino whose movement is being checked
	 * @param dir the desired direction of the tetromino's movement
	 * @return true if the move is valid, false otherwise
	 */
    private boolean moveIsValid(Tetromino current, Direction dir) {
		boolean isValid = true;
		TetrisPiece[] pieces = current.getPieces();

		if (dir == Direction.UP) {
			for (TetrisPiece piece: pieces) {
                if (piece.getRow() == 2) return false;
                if (pieceTracker[piece.getRow() - 1][piece.getCol()])
                        isValid = false;
			}
		} else if (dir == Direction.DOWN) {
		    for (TetrisPiece piece: pieces) {
                if (piece.getRow() == NUM_ROWS - 1) return false;
                if (pieceTracker[piece.getRow() + 1][piece.getCol()])
                    isValid = false;
			}
		} else if (dir == Direction.RIGHT) {
			for (TetrisPiece piece: pieces) {
                if (piece.getCol() == NUM_COLS - 1) return false;
                if (pieceTracker[piece.getRow()][piece.getCol() + 1])
                    isValid = false;
			}
		} else if (dir == Direction.LEFT) {
			for (TetrisPiece piece: pieces) {
                if (piece.getCol() == 0)  return false;
                if (pieceTracker[piece.getRow()][piece.getCol() - 1])
                    isValid = false;
			}
		}
		// checks if the movement is valid for the specified direction

		return isValid;
    } // moveIsValid

	/**
	 * Moves the tetromino once in the specified direction
	 *
	 * @param current the tetromino being moved
	 * @param dir the direction to move the tetromino in
	 */
    private void move(Tetromino current, Direction dir) {
		TetrisPiece[] pieces = current.getPieces();

		if (dir == Direction.UP) {
			for (TetrisPiece piece: pieces) {
			piece.setGridPosition(piece.getRow() - 1, piece.getCol());
			}
		} else if (dir == Direction.DOWN) {
			for (TetrisPiece piece: pieces) {
			piece.setGridPosition(piece.getRow() + 1, piece.getCol());
			if (piece.getRow() == 19)
				tracker = true;
			}
		} else if (dir == Direction.RIGHT) {
			for (TetrisPiece piece: pieces) {
			piece.setGridPosition(piece.getRow(), piece.getCol() + 1);
			}
		} else if (dir == Direction.LEFT) {
			for (TetrisPiece piece: pieces) {
			piece.setGridPosition(piece.getRow(), piece.getCol() - 1);
			}
		}
		// moves each piece in the tetromino down one cell in the grid

		root.requestFocus();
    } // move

	/**
	 * Displays a window asking the user if they want to close the game.
	 */
    private void checkClose() {
		if (!paused)
			timeline.pause();

		Stage closeWindow = new Stage();
		closeWindow.initModality(Modality.APPLICATION_MODAL);

		VBox root = new VBox(20);

		Label closeLabel = new Label("Are you sure you want to close the game?");
			root.getChildren().add(closeLabel);
		// creating a label asking the user if they are sure they want to stop playing

		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> {
			closeWindow.close();
			if (!paused)
				timeline.play();
			});
		cancelButton.setMinWidth(75);
		// creating a button to resume the game instead of closing

		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> {
			closeWindow.close();
			this.close();
			});
		closeButton.setMinWidth(75);
		// creating a button to close the game

		HBox buttonBox = new HBox(20);
		buttonBox.getChildren().addAll(cancelButton, closeButton);
		buttonBox.setAlignment(Pos.CENTER);
		// adding the buttons to an HBox

		root.getChildren().add(buttonBox);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10));

		closeWindow.setScene(new Scene(root));
		closeWindow.showAndWait();

    } // checkClose

	/**
	 * Adds the tetromino to the top of the grid
	 *
	 * @param current the tetromino to be added to the grid
	 */
    private void addToGrid(Tetromino current) {
		TetrisPiece[] pieces = current.getPieces();

		grid.getChildren().addAll(pieces[0], pieces[1], pieces[2], pieces[3]);
    } // addToGrid

	/**
	 * Creates a new tetromino with a random shape which will be the current falling tetromino.
	 */
	private void nextTetromino() {
		if (hasStarted)
			timeline.pause();

		int shapeSelect = rng.nextInt(7);
		Tetromino.Shape shape;
			if (shapeSelect == 0)
			shape = Tetromino.Shape.I;
		else if (shapeSelect == 1)
			shape = Tetromino.Shape.J;
		else if (shapeSelect == 2)
			shape = Tetromino.Shape.L;
		else if (shapeSelect == 3)
			shape = Tetromino.Shape.O;
		else if (shapeSelect == 4)
			shape = Tetromino.Shape.S;
		else if (shapeSelect == 5)
			shape = Tetromino.Shape.T;
		else
			shape = Tetromino.Shape.Z;
		// assigns a shape randomly

		Tetromino temp = new Tetromino(shape);
		currentPieces = temp.getPieces();
		temp.setInitialGridPosition();
		addToGrid(temp);
		// creates the tetromino and adds it to the grid

		current = temp;

		if (hasStarted)
			timeline.play();
    } // nextTetromino


	/**
	 * Checks if any of the rows can be cleared. A row can be cleared if there is a tetromino piece
	 * in each column space in that row.
	 *
	 * @return true if at least one row can be cleared, false otherwise
	 */
	private boolean checkForRowClear() {
    	boolean check = false;

		for (int i = NUM_ROWS -1; i > 1; i--) {
			for (int j = 0; j < NUM_COLS; j++) {
				if (!pieceTracker[i][j])
					break;
				else {
					if (j == NUM_COLS - 1) {
						check = true;
						clearRow(i);
						i++;
					}
				}
			}
		}

		return check;
	} // checkForRowClear

	/**
	 * Removes each piece in the specified row and moves each piece above the row down one cell.
	 *
	 * @param row the row to be cleared
	 */
	private void clearRow(int row) {
    	addToScore(400 * multiplier);
    	if (multiplier < 4) {
			multiplier++;
			multLabel.setText("Multiplier: " + multiplier);
		}
    	// adds a number to the player's score equal to 400 times the current multiplier

    	for (int i = 0; i < NUM_COLS; i++) {
    		pieceTracker[row][i] = false;
    		grid.getChildren().remove(pieceGrid[row][i]);
    		pieceGrid[row][i] = null;
		}
		// clears the row

		boolean check;
		for (int i = row - 1; i > 1; i--) {
    		check = false;
    		for (int j = 0; j < NUM_COLS; j++) {
    			if (pieceTracker[i][j]){
    				check = true;
    				pieceGrid[i][j].setGridPosition(i+1, j);
    				pieceGrid[i + 1][j] = pieceGrid[i][j];
    				pieceGrid[i][j] = null;
    				pieceTracker[i + 1][j] = true;
    				pieceTracker[i][j] = false;
				}
			}

			if (!check)
				break;
		}
		// moves all pieces above the cleared row down one cell
	} // clearRow

	/**
	 * Adds the specified amount to the score. Checks if the player's score is high enough to
	 * move to the next level.
	 *
	 * @param num the number to add to the player's score
	 */
	private void addToScore(int num) {
		score += num;
		scoreLabel.setText("Score: " + score);
		if (level < MAX_LEVEL) {
			int cutoff = ((int) Math.pow((double) level, 2.0)) * 1000;

			if (score >= cutoff) {
				increaseLevel();
			}
		}
	} // addToScore

	/**
	 * Increase the level of the game. The pieces will fall faster with each increasing level. The
	 * max level is 8.
	 */
	private void increaseLevel() {

    	level++;
    	levelLabel.setText("Level: " + level);
    	timeline.pause();

    	double rate = (double) MAX_LEVEL / (MAX_LEVEL - level + 1);
    	timeline.setRate(rate);
		// speeds up the falling animation
	} // increaseLevel

	/**
	 * Stops the game and displays a "game over" dialogue to the player. The player can then choose
	 * to exit the game or play again.
	 */
	private void lose() {
    	timeline.pause();

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);

		VBox root = new VBox(20);

		Label loseLabel = new Label("GAME OVER");
		root.getChildren().add(loseLabel);
		// creating a label telling the player the game is over

		root.getChildren().add(scoreLabel);

		Button restartButton = new Button("New Game");
		restartButton.setOnAction(e -> {
			window.close();
			init();
		});
		restartButton.setMinWidth(75);
		// creating a button to restart the game instead of closing

		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> {
			window.close();
			this.close();
		});
		closeButton.setMinWidth(75);
		// creating a button to close the game

		HBox buttonBox = new HBox(20);
		buttonBox.getChildren().addAll(restartButton, closeButton);
		buttonBox.setAlignment(Pos.CENTER);
		// adding the buttons to an HBox

		root.getChildren().add(buttonBox);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(10));

		window.setScene(new Scene(root));
		window.show();
	}
}