package cs1302.arcade;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.control.TextInputDialog;
import javafx.geometry.Pos;
import java.util.Optional;
import java.lang.Math;

//have message tell who winner is , or do a pop up and celebration animation, or dialog option to restart , close or view statistics
public class CheckersGame extends Stage{

    private GridPane grid = new GridPane();
    private BorderPane border = new BorderPane(); //border pane to put grid in center and scoreboard on one of the sides
    private HBox root = new HBox();
    private VBox sideMenu = new VBox();
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

    //set labels and buttons
    Label redLabel = new Label("Red Pieces Taken: " + redPiecesTaken);
    Label blackLabel = new Label("Black Pieces Taken " + blackPiecesTaken);
    Label message = new Label("Somebody make a \nmove!");
    Button closeButton = new Button("Close");    
    Button reart = new Button("Start");

    //for stats
    String playerRedName = "Red";
    String playerBlackName = "Black";
    int playerRedWins = 0;
    int playerBlackWins = 0;

    boolean firstStart = true;
    

    /******************************
     * public CheckersGame()
     *
     * This is the CheckersGame class Constructor
     *
     ******************************/
    public CheckersGame(){	
	super();
	init();
	reart.setMinWidth(75);
	reart.setOnAction(e -> {
		if(firstStart){
		System.out.println("It works");
		//when start click open up dialog box to enter names of players 
		//this will be used to track how many games won
		//use search to find if name alreay exists in stats base
		//add to stats if yes, make new player if not, max 10 players
		TextInputDialog dialog = new TextInputDialog("Red");
		dialog.setTitle("Enter Player Red Name");
		dialog.setContentText("Please enter the name of player Red:");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> playerRedName = name);

		TextInputDialog dialog2 = new TextInputDialog("Black");
		dialog2.setTitle("Enter Player Black Name");
		dialog2.setContentText("Please enter the name of player Black:");
		result = dialog2.showAndWait();
		result.ifPresent(name2 -> playerBlackName = name2);


		System.out.println(playerBlackName + " " + playerRedName);

		reart.setText("Restart");
		firstStart=false;
		}else{
		    reart.setText("Start");
		    restart();
		    firstStart=true;
		}

	    });
	
	closeButton.setMinWidth(75);
	//closeButton.setOnAction(e -> checkClose());
	
	sideMenu = new VBox(20, redLabel, blackLabel, reart, closeButton, message);
	sideMenu.setMinWidth(150);
	sideMenu.setAlignment(Pos.CENTER);

	root = new HBox(sideMenu, grid);
	game = new Scene(root);

	setScene(game);
	setMaxHeight(1000);
	setMaxWidth(950);
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
	Image currentImage;
	if(clickCount == 1){
	    firstRow = row;
	    firstCol = col;
	    message.setText("");
	    currentImage  = tiles[row][col].getImage();

	    if((currentImage==redTile)||currentImage==blackTile){
		clickCount = 0;
		message.setText("Invalid First Click");
	    } 
	    
	}
	if(clickCount == 2){
	    secondRow = row;
	    secondCol = col;
	    message.setText("");
	    currentImage = tiles[row][col].getImage();
	    if((currentImage== redTile)||(currentImage== redPiece)||(currentImage==blackPiece)||(currentImage==redCrownPiece)||(currentImage == blackCrownPiece)){
		message.setText("Invalid Second Click");
		clickCount = 0;
		//	break;
	    }else{


	    int midRow = (firstRow + secondRow)/2;
	    int midCol = (firstCol + secondCol)/2;
	    Image temp = tiles[midRow][midCol].getImage();
	    if(((temp == redPiece) || (temp ==blackPiece))&&(midRow != firstRow)){
		tiles[midRow][midCol].setImage(blackTile); //changes the image if jumped

		//keep track of stats: pieces taken and if there is a winner
		if(temp == redPiece) redPiecesTaken++;
		if(temp == blackPiece) blackPiecesTaken++;
		redLabel.setText("Red Pieces Taken: " + redPiecesTaken);
		blackLabel.setText("Black Pieces Taken: " + blackPiecesTaken);
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
	if(i == 0) {
	    System.out.println("Congratulations "+playerRedName +"! You have Won!!");
	    playerRedWins++;
	}
	if(i == 1){
	    System.out.println("Congratulations "+playerBlackName+"! You have Won!!");
	    playerBlackWins++;
	}
	
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
	
	boolean isRed = true;
	redPiecesTaken = 0;
	blackPiecesTaken = 0;

	//for loop to put imageView on the grid
	for(int x = 0; x < 8; x++){
	    for(int y = 0; y < 8; y++){
		//if-else statement decides whether the piece will be red of black
		if(isRed){
		    tiles[x][y].setImage(redTile); //sets tile to red
		    //  tiles[x][y].setFitWidth(100);
		    //tiles[x][y].setFitHeight(100); 
		    //grid.add(tiles[x][y] ,y,x);
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
		    //tiles[x][y].setFitWidth(100);
		    //tiles[x][y].setFitHeight(100); 
		    //grid.add(tiles[x][y], y, x);
		    isRed = true;
		}
	    }//for
	    isRed = !isRed; //so no tile are same up and down
	}//for
	//border.setCenter(grid);

    }

}