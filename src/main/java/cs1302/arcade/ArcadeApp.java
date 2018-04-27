
package cs1302.arcade;

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Modality;

public class ArcadeApp extends Application {

    Random rng = new Random();

    @Override
    public void start(Stage stage) {

	/* You are allowed to rewrite this start method, add other methods, 
	 * files, classes, etc., as needed. This currently contains some 
	 * simple sample code for mouse and keyboard interactions with a node
	 * (rectangle) in a group. 
	 */
	
	Group group = new Group();           // main container
	Rectangle r = new Rectangle(20, 20); // some rectangle
	r.setX(50);                          // 50px in the x direction (right)
	r.setY(50);                          // 50ps in the y direction (down)
	group.getChildren().add(r);          // add to main container
	
	HBox homeScreen = new HBox(20);
	Button tetris = new Button("Tetris");
	Button checkers = new Button("Checkers");

	VBox tetrisBox = new VBox(20);
	tetrisBox.setAlignment(Pos.CENTER);
	ImageView tetrisPic = new ImageView("https://assets3.thrillist.com/v1/image/1258932/size/tl-horizontal_main.jpg");
	tetrisPic.setSmooth(true);
	tetrisPic.setFitWidth(300);
	tetrisPic.setFitHeight(300);
	tetrisBox.getChildren().addAll(tetris, tetrisPic);

	VBox checkersBox = new VBox(20);
	checkersBox.setAlignment(Pos.CENTER);
	ImageView checkersPic = new ImageView("https://1001freedownloads.s3.amazonaws.com/vector/thumb/88538/1322756040.png");
	checkersPic.setSmooth(true);
	checkersPic.setFitWidth(300);
	checkersPic.setFitHeight(300);
	checkersBox.getChildren().addAll(checkers, checkersPic);
	
	homeScreen.getChildren().addAll(tetrisBox,checkersBox);


	tetris.setOnAction(e -> {

	       	Stage T = new TetrisGame();
		T.initModality(Modality.APPLICATION_MODAL);
		T.setTitle("Tetris");
		T.showAndWait();
	    });


	checkers.setOnAction(e -> {

		Stage C = new CheckersGame();
		C.initModality(Modality.APPLICATION_MODAL);
		C.setTitle("Checkers");
		C.showAndWait();
	    });

	// when the user clicks on the rectangle, send to random position
	r.setOnMouseClicked(event -> {
		System.out.println(event);
		r.setX(rng.nextDouble() * (640 - r.getWidth()));
		r.setY(rng.nextDouble() * (480 - r.getHeight()));
	    });

	// when the user presses left and right, move the rectangle
	group.setOnKeyPressed(event -> {
		System.out.println(event);
		if (event.getCode() == KeyCode.LEFT)  r.setX(r.getX() - 10.0);
		if (event.getCode() == KeyCode.RIGHT) r.setX(r.getX() + 10.0);
		// TODO bounds checking
	    });

        Scene scene = new Scene(homeScreen);
        stage.setTitle("cs1302-arcade!");
        stage.setScene(scene);
	stage.sizeToScene();
        stage.show();

	// the group must request input focus to receive key events
	// @see https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html#requestFocus--
	group.requestFocus();

    } // start

    public static void main(String[] args) {
	try {
	    Application.launch(args);
	} catch (UnsupportedOperationException e) {
	    System.out.println(e);
	    System.err.println("If this is a DISPLAY problem, then your X server connection");
	    System.err.println("has likely timed out. This can generally be fixed by logging");
	    System.err.println("out and logging back in.");
	    System.exit(1);
	} // try
    } // main

} // ArcadeApp

