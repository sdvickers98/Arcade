package cs1302.arcade;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisGame extends Stage{

    private final Random rng = new Random();
    private final int CELL_SIZE = 30;
    private final int NUM_COLS = 10;
    private final int NUM_ROWS = 20;

    private enum Direction {
	UP, DOWN, LEFT, RIGHT;
    }

    private Timeline timeline;
    private GridPane grid;
    private boolean isPlaying;
    private boolean[][] pieceTracker;
    private TetrisPiece[][] pieceGrid;
    private int score, level;
    private EventHandler<ActionEvent> pieceMovement;
    private ScheduledService<Void> newPiece;
    private Tetromino current;

    public TetrisGame(){	
	super();
	init();

	Label scoreLabel = new Label("Score: " + score);
	Label levelLabel = new Label("Level: " + level);

	Button playButton = new Button("Play");
	playButton.setMinWidth(75);
	playButton.setOnAction(e -> {
		if (true) timeline.play();
		else{
		if (!isPlaying) {
		    playButton.setText("Pause");
		    isPlaying = true;

		    newPiece.start();
  		} else {
		    if (playButton.getText().equals("Pause")) {
			playButton.setText("Resume");
			timeline.pause();
		    } else {
			playButton.setText("Pause");
			timeline.play();
		    }
		}
		}
	    });

	//
	 current = new Tetromino(Tetromino.Shape.I);
	 current.setInitialGridPosition();
	 addToGrid(current);
	//

	Button closeButton = new Button("Close");
	closeButton.setMinWidth(75);
	closeButton.setOnAction(e -> checkClose());
	
	VBox sideMenu = new VBox(20, scoreLabel, levelLabel, playButton, closeButton);
	sideMenu.setMinWidth(150);
	sideMenu.setAlignment(Pos.CENTER);

	HBox root = new HBox(sideMenu, grid);
	Scene game = new Scene(root);
	setScene(game);
    }

    private void init(){
	initTimeline();
	initService();

	isPlaying = false;
	score = 0;
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
	    TetrisPiece[] pieces = current.getPieces();

	    if (moveIsValid(current, Direction.DOWN))  
		move(current, Direction.DOWN); 
	    // if the move is valid, the tetromino is shifted down by one 

	    else {
		for (TetrisPiece piece: pieces) {
		    pieceTracker[piece.getRow()][piece.getCol()] = true;
		    pieceGrid[piece.getRow()][piece.getCol()] = piece;
		}
		//newPiece.restart();

		timeline.stop();
	    }
	};

	KeyFrame keyframe = new KeyFrame(Duration.seconds(1), pieceMovement);
	timeline.setCycleCount(Timeline.INDEFINITE);
	timeline.getKeyFrames().add(keyframe);
    } // initTimeline

    private void initService() {
	newPiece = new ScheduledService<Void>() {
	    public Task<Void> createTask() {
		return new Task<Void>() {
		    public Void call() {
			int shapeSelect = rng.nextInt(7);
			Tetromino.Shape shape;

			if (shapeSelect == 0) { shape = Tetromino.Shape.I; }
			else if (shapeSelect == 1) { shape = Tetromino.Shape.J; }
			else if (shapeSelect == 2) { shape = Tetromino.Shape.L; }
			else if (shapeSelect == 3) { shape = Tetromino.Shape.O; }
			else if (shapeSelect == 4) { shape = Tetromino.Shape.S; }
			else if (shapeSelect == 5) { shape = Tetromino.Shape.T; }
			else { shape = Tetromino.Shape.Z; }

			current = new Tetromino(shape);
			current.setInitialGridPosition();
			addToGrid(current);
			timeline.play();

			return null;
		    }
		};
	    }
	};

	newPiece.setPeriod(Duration.INDEFINITE);
    } // initService

    private boolean moveIsValid(Tetromino current, Direction dir) {
	boolean isValid = true;
	TetrisPiece[] pieces = current.getPieces();
	
	if (dir == Direction.UP) {
	    for (TetrisPiece piece: pieces) {
		if (piece.getRow() == 2) return false;
		isValid = !pieceTracker[piece.getRow() - 1][piece.getCol()];
	    }
	} else if (dir == Direction.DOWN) {
	    for (TetrisPiece piece: pieces) {
		if (piece.getRow() == NUM_ROWS - 1) return false;
		isValid = !pieceTracker[piece.getRow() + 1][piece.getCol()];
	    }
	} else if (dir == Direction.RIGHT) {
	    for (TetrisPiece piece: pieces) {
		if (piece.getCol() == NUM_COLS - 1) return false;
		isValid = !pieceTracker[piece.getRow()][piece.getCol() + 1];
	    }
	} else if (dir == Direction.LEFT) {
	    for (TetrisPiece piece: pieces) {
		if (piece.getCol() == 0)  return false;
		isValid = !pieceTracker[piece.getRow()][piece.getCol() - 1];
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
    } // move

    private void checkClose() {
	this.close();
	// TODO implement
    } // checkClose

    private void addToGrid(Tetromino current) {
	TetrisPiece[] pieces = current.getPieces();

	grid.getChildren().addAll(pieces[0], pieces[1], pieces[2], pieces[3]);
    } // addToGrid

}