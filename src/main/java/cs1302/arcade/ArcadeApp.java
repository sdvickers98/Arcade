
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

/**
 * The driver class for the arcade application.
 */
public class ArcadeApp extends Application {

    Random rng = new Random();

	/**
	 * @inheritDoc
	 */
	@Override
    public void start(Stage stage) {

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
		// sets up the tetris image and button to start tetris

		VBox checkersBox = new VBox(20);
		checkersBox.setAlignment(Pos.CENTER);
		ImageView checkersPic = new ImageView("https://1001freedownloads.s3.amazonaws.com/vector/thumb/88538/1322756040.png");
		checkersPic.setSmooth(true);
		checkersPic.setFitWidth(300);
		checkersPic.setFitHeight(300);
		checkersBox.getChildren().addAll(checkers, checkersPic);
		// sets up the checkers image and button to start checkers

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

		Scene scene = new Scene(homeScreen);
		stage.setTitle("cs1302-arcade!");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();

    } // start

	/**
	 * Starts the application.
	 *
	 * @param args command line arguments passed to the program
	 */
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

