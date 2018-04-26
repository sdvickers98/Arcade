package cs1302.arcade;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class CheckersGame extends Stage{

    private GridPane grid = new GridPane();
    private BorderPane border = new BorderPane(); //border pane to put grid in center and scoreboard on one of the sides
    private Scene game = new Scene(border);

    //get images for the board
    Image redTile = new Image("https://www.colorcombos.com/images/colors/FF0000.png");
    Image blackTile = new Image("https://www.colorcombos.com/images/colors/000000.png");
    Image blackCrownPiece = new Image("https://goo.gl/yFWSJu");
    Image blackPiece = new Image("http://www.goo.gl/RmmWn2");
    Image redCrownPiece = new Image("https://goo.gl/uYGUsC");
    Image redPiece = new Image("https://goog.gl/qLbxia");

    /******************************
     * public CheckersGame()
     *
     * This is the CheckersGame class Constructor
     *
     ******************************/
    public CheckersGame(){	
	super();
	init();
	setScene(game);
    }

    /********************************
     * public GridPane getGrid()
     *
     * Allows the ability to get the grid the user will play on
     *
     * @returns GridPane
     *******************************/
    public GridPane getGrid(){
	return grid;
    }

    /*********************************
     * public void setGrid(GridPane grid)
     *
     * Allows the ability to access and set the grid the user will play on
     *
     * @param GridPane grid
     * @returns void
     ********************************/
    public void setGrid(GridPane grid){
	this.grid = grid;
    }
   
    /***************************************
     * public void init()
     *
     * This method sets up the grid that the user will play on. 
     * 
     * @returns void*
     **************************************/
    public void init(){
	grid.setGridLinesVisible(true);
	int numCols = 8;
	int numRows = 8;
	ImageView[][] tiles = new ImageView[numRows][numCols]; //2D array holds the imageViews for the board
	ImageView tempTile;
	boolean isRed = true;
	for(int i = 0; i <numCols; i++){
	    ColumnConstraints colConst = new ColumnConstraints();
	    colConst.setPercentWidth(100.0/numCols);
	    grid.getColumnConstraints().add(colConst);
	}//for
	for(int i = 0; i <numRows; i++){
	    RowConstraints rowConst = new RowConstraints();
	    rowConst.setPercentHeight(100.0/numRows);
	    grid.getRowConstraints().add(rowConst);
	}//for

	//for loop to make a 2D array of imageViews that is identical to the 2D grid 
	for(int x = 0; x < numRows; x++){
	    for(int y = 0; y < numCols; y++){
		tempTile = new ImageView();
		tiles[x][y] = tempTile;
	    }//for
	}//for

	//for loop to put imageView on the grid
	for(int x = 0; x < numRows; x++){
	    for(int y = 0; y < numCols; y++){
		//if-else statement decides whether the piece will be red of black
		if(isRed){
		    tiles[x][y].setImage(redTile); //sets tile to red
		    tiles[x][y].setFitWidth(100);
		    tiles[x][y].setFitHeight(100); 
		    grid.add(tiles[x][y] ,y,x);
		    isRed = false; //so no side by side same colors
		}
		else{
		    if(x < 3){
			tiles[x][y].setImage(redPiece); //sets tile to a red piece
		    }else if(x > 4){
			tiles[x][y].setImage(blackPiece);
		    }else{
			tiles[x][y].setImage(blackTile); //sets tile to black
		    }
		    tiles[x][y].setImage(blackTile); //sets tile to black
		    tiles[x][y].setFitWidth(100);
		    tiles[x][y].setFitHeight(100); 
		    grid.add(tiles[x][y], y, x);
		    isRed = true;
		}
	    }//for
	    isRed = !isRed; //so no tile are same up and down
	}//for
	border.setCenter(grid);


    }


}