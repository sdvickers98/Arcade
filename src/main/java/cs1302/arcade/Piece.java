import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Piece extends Rectangle {

    private static final int PIECE_SIZE = 20;
    
    public Piece(Color color) {
	super(PIECE_SIZE, PIECE_SIZE, color);
    } // Piece
    
} // Piece
