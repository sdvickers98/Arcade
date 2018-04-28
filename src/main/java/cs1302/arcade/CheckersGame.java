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
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import java.lang.Math;


public class CheckersGame extends Stage{

    private GridPane grid = new GridPane();
    private BorderPane border = new BorderPane(); //border pane to put grid in center and scoreboard on one of the sides
    private Scene game = new Scene(border);

    //get images for the board
    
    Image redTile = new Image("file:src/main/resources/images/redTile.png");
   
    Image blackTile = new Image("file:src/main/resources/images/blackTile.png");
    Image blackCrownPiece = new Image("file:src/main/resources/images/blackKing.png");
    Image blackPiece = new Image("file:src/main/resources/images/black.png");
    Image redCrownPiece = new Image("file:src/main/resources/images/redKing.png");
    Image redPiece = new Image("file:src/main/resources/images/red.png");
    ImageView[][] tiles = new ImageView[8][8]; //2D array holds the imageViews for the board
    int clickCount = 0;
    int firstRow, secondRow, firstCol, secondCol;
    int redPiecesTaken = 0;
    int blackPiecesTaken = 0;
    

    /******************************
     * public CheckersGame()
     *
     * This is the CheckersGame class Constructor
     *
     ******************************/
    public CheckersGame(){	
	super();
	init();
	/*
Label scoreLabel = new Label("Score: " + score);
	Label levelLabel = new Label("Level: " + level);

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

	Button closeButton = new Button("Close");
	closeButton.setMinWidth(75);
	closeButton.setOnAction(e -> checkClose());
	
	VBox sideMenu = new VBox(20, scoreLabel, levelLabel, playButton, closeButton);
	sideMenu.setMinWidth(150);
	sideMenu.setAlignment(Pos.CENTER);

	HBox root = new HBox(sideMenu, grid);
	Scene game = new Scene(root);
*/
	setScene(game);
	setMaxHeight(800);
	setMaxWidth(800);
	/*
	 * Implement the game
	 * user first clicks image (piece) they want to move
	 * user then click where they want to move the image (piece) too
	 */
	grid.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
		clickCount++; //keeps track
		onClick(e);
		System.out.println(clickCount); //prints the number of times the grid has been clicked so far
		if(clickCount == 2){
		    clickCount = 0;
		    //System.out.println("first click: " + firstClick + "\n" + "second click: " + secondClick); //prints the coordinates of the first and second click
		}
	    });
    }
    
    /************************************
     * public void onClick(MouseEvent event)
     *
     * This method will replace the image between imageViews
     * The images that will be replaces are based on the coordinates
     * the user gives from each mouse click 
     *
     * @param MouseEvent
     * @returns void
     ***********************************/
    public void onClick(MouseEvent event){
	int row = (int)Math.ceil(event.getY()/100) -1;
	int col = (int)Math.ceil(event.getX()/100) -1;
	if(clickCount == 1){
	    firstRow = row;
	    firstCol = col;
	    
	}
	if(clickCount == 2){
	    secondRow = row;
	    secondCol = col;


	    int midRow = (firstRow + secondRow)/2;
	    int midCol = (firstCol + secondCol)/2;
	    Image temp = tiles[midRow][midCol].getImage();
	    if(((temp == redPiece) || (temp ==blackPiece))&&(midRow != firstRow)){
		tiles[midRow][midCol].setImage(blackTile); //changes the image if jumped

		//keep track of stats: pieces taken and if there is a winner
		if(temp == redPiece) redPiecesTaken++;
		if(temp == blackPiece) blackPiecesTaken++;
		System.out.println(redPiecesTaken + " " + blackPiecesTaken);
		if((redPiecesTaken == 12)||(blackPiecesTaken == 12)){
		    if(redPiecesTaken == 12) winner(0);
		    if(blackPiecesTaken == 12) winner(1);
		}
	    }
	    
	    //switch the images
	    temp = tiles[firstRow][firstCol].getImage();
	    tiles[firstRow][firstCol].setImage(blackTile);
	    
	    if(temp == blackPiece){
		
		if(secondRow == 0)tiles[secondRow][secondCol].setImage(blackCrownPiece);
		else tiles[secondRow][secondCol].setImage(blackPiece);
	    } 
	    if(temp == redPiece){
		if(secondRow == 7)tiles[secondRow][secondCol].setImage(redCrownPiece);
		else tiles[secondRow][secondCol].setImage(redPiece);
	    }

	    //take pieces out if needed

	    //  temp = tiles[midRow][midCol].getImage();
	    //System.out.println(midRow+" "+midCol+" "+firstRow+ " "+firstCol+" "+ secondRow+ " " + secondCol);
	    // if(((temp == redPiece) || (temp ==blackPiece))&&(midRow != firstRow)) tiles[midRow][midCol].setImage(blackTile); 
	    
	}

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
	//	ImageView[][] tiles = new ImageView[numRows][numCols]; //2D array holds the imageViews for the board
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
		    // tiles[x][y].setImage(blackTile); //sets tile to black
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

    /*******************************
     * public void winner(int i)
     *
     * Method to do things when the game is finished:
     * -Displays which color wins
     * -Saves stats and updates to scoreboard
     * -Gives option to restart (make restart method)
     * -Gives option to go back to main menu
     * -Gives option to see the High Scores/scoreboard
     * 
     * @param int 
     * @returns void
     ********************************/
    public void winner(int i){
	if(i == 0) System.out.println("Congratulations Red! You have Won!!");
	if(i == 1) System.out.println("Congratulations Black! You have Won!!");

	restart();



    }

    /***************************************
     * public void restart()
     *
     * Restarts the game
     * Resets the instance variables and calls init()
     *
     **************************************/
    public void restart(){
	
       
	redPiecesTaken = 0;
	blackPiecesTaken = 0;

	init();

    }

}