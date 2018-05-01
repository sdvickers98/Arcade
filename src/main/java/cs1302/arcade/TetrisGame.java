package cs1302.arcade;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
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
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.util.Duration;

public class TetrisGame extends Stage{

    private final Random rng = new Random();
    private final int CELL_SIZE = 30;
    private final int NUM_COLS = 10;
    private final int NUM_ROWS = 20;

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
    private Label scoreLabel, levelLabel;

    public TetrisGame(){	
		super();
		init();

		scoreLabel = new Label("Score: " + score);
		levelLabel = new Label("Level: " + level);

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

		Button closeButton = new Button("Close");
		closeButton.setMinWidth(75);
		closeButton.setOnAction(e -> checkClose());

		VBox sideMenu = new VBox(20, scoreLabel, levelLabel, playButton, closeButton);
		sideMenu.setMinWidth(150);
		sideMenu.setAlignment(Pos.CENTER);

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

		Scene game = new Scene(root);
		setScene(game);
    } // start

    private void init(){
		initTimeline();

		hasStarted = false;
		score = 0;
		multiplier = 1;
		level = 1;

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
    } // init

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

                score += 20;
				scoreLabel.setText("Score: " + score);

                checkForRowClear();
                tracker = false;
                nextTetromino();
			}
		};

		KeyFrame keyframe = new KeyFrame(Duration.seconds(1), pieceMovement);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyframe);
    } // initTimeline

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

		return isValid;
    } // moveIsValid

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

		grid.requestFocus();
    } // move

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

    private void addToGrid(Tetromino current) {
		TetrisPiece[] pieces = current.getPieces();

		grid.getChildren().addAll(pieces[0], pieces[1], pieces[2], pieces[3]);
    } // addToGrid

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

		Tetromino temp = new Tetromino(shape);
		currentPieces = temp.getPieces();
		temp.setInitialGridPosition();
		addToGrid(temp);

		current = temp;

		if (hasStarted)
			timeline.play();
    } // nextTetromino

	private void checkForRowClear() {
		for (int i = NUM_ROWS -1; i > 1; i--) {
			for (int j = 0; j < NUM_COLS; j++) {
				if (!pieceTracker[i][j])
					break;
				else {
					if (j == NUM_COLS - 1) {
						clearRow(i);
						i++;
					}
				}
			}
		}
	} // checkForRowClear

	private void clearRow(int row) {
    	score += 400 * multiplier;
    	multiplier++;
		scoreLabel.setText("Score: " + score);

    	for (int i = 0; i < NUM_COLS; i++) {
    		pieceTracker[row][i] = false;
    		grid.getChildren().remove(pieceGrid[row][i]);
		}
	} // clearRow

}