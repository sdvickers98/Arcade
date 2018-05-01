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
import javafx.stage.Modality;
import javafx.geometry.Insets;
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
	closeButton.setOnAction(e -> checkClose());
	
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
		//	System.out.println(clickCount); //prints the number of times the grid has been clicked so far
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
	Image currentImage = tiles[row][col].getImage();
	int check = isInvalid(row,col,currentImage);
	message.setText("");

	switch(check){

	case 1:
	    clickCount = 0;
	    message.setText("Invalid First Click");
	    break;

	case 2:
	    clickCount = 0;
	    message.setText("Invalid Second Click");
	    break;

	case -1:
	    firstRow = row;
	    firstCol = col;
	    break;

	case -2:
	    secondRow = row;
	    secondCol = col;
	    break;
	}

	if(check == -2){
	    currentImage = tiles[firstRow][firstCol].getImage();
	    check = isInvalid(firstRow, firstCol, secondRow, secondCol, currentImage);

	    System.out.println(check);
	    if(check == -1){
	    int midRow = (firstRow + secondRow)/2;
	    int midCol = (firstCol + secondCol)/2;
	    Image temp = tiles[midRow][midCol].getImage();
	    //remember to change so error if jump over your own piece
	    if(((((temp == redPiece)||(temp == redCrownPiece))&&(currentImage != redPiece)) || (((temp == blackPiece)||(temp == blackCrownPiece))&&(currentImage != blackPiece)))&&(midRow != firstRow)){
		tiles[midRow][midCol].setImage(blackTile); //changes the image if jumped

		//keep track of stats: pieces taken and if there is a winner
		if((temp == redPiece)||(temp == redCrownPiece)) redPiecesTaken++;
		if((temp == blackPiece)||(temp == blackCrownPiece)) blackPiecesTaken++;
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
	    if(temp == blackCrownPiece) tiles[secondRow][secondCol].setImage(blackCrownPiece);
	    if(temp == redCrownPiece) tiles[secondRow][secondCol].setImage(redCrownPiece);
	    }else{

		switch(check){

		case 1:
		    message.setText("Red Piece can't \nmove here");
		    break;

		case 2:
		    message.setText("Black Piece can't \nmove here");
		    break;

		case 3:
		    message.setText("Red Crown Piece can't \nmove here");
		    break;

		case 4:
		    message.setText("Black Crown Piece can't \nmove here");
		    break;
		} //switch
	    }//else
	}//if 
	    //take pieces out if needed

	    //  temp = tiles[midRow][midCol].getImage();
	    //System.out.println(midRow+" "+midCol+" "+firstRow+ " "+firstCol+" "+ secondRow+ " " + secondCol);
	    // if(((temp == redPiece) || (temp ==blackPiece))&&(midRow != firstRow)) tiles[midRow][midCol].setImage(blackTile); 
	    
    }//onClick



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

    public void checkClose() {
	Stage closeWindow = new Stage();
	closeWindow.initModality(Modality.APPLICATION_MODAL);

	VBox root = new VBox(20);
	
	Label closeLabel = new Label("Are you sure you want to close the game?");
     	root.getChildren().add(closeLabel);
	// creating a label asking the user if they are sure they want to stop playing

	Button cancelButton = new Button("Cancel");
	cancelButton.setOnAction(e -> {
		closeWindow.close();
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

    }

    /****************************
     * public int isInvalid(int row, int col, Image currentImage)
     *
     * This method will check for invalid moves.
     * Will return specified int values that will be mapped to 
     * a certain invalid warning
     *
     * @param int, int, Image
     * @returns int
     *****************************/

    public int isInvalid(int row, int col, Image currentImage){

	if(clickCount == 1){
	    if((currentImage==redTile)||currentImage==blackTile){
		clickCount = 0;
		return 1;
	    }
	    return -1;
	}else{
	    if(currentImage != blackTile){
		clickCount = 0;
		return 2;
		
	    }
	    return -2;
	}	
    }

    /************************************
     * public int isInvalid(int firstRow, int firstCol, int secondRow, int secondCol, Image currentImage)
     *
     * This method checks if the space where the user wants to move is a valid move.
     * Returns an int that is mapped to specific definition to decide what to do.
     *
     * @param int, int, int, int, Image
     * @returns int
     ************************************/

    public int isInvalid(int firstRow, int firstCol, int secondRow, int secondCol, Image currentImage){
	
	if(currentImage == redPiece){

	    if(firstRow == secondRow) return 1;
	    if((firstRow + 1 != secondRow)&&(firstRow + 2 != secondRow)) return 1;



	}else if(currentImage == blackPiece){
	    
	    if(firstRow == secondRow) return 2;
	    if((firstRow -1 != secondRow)&&(firstRow - 2 != secondRow)) return 2;


	}else if(currentImage == redCrownPiece){

	    //	    if(((firstRow + 1 == secondRow)||(firstRow - 1 == secondRow))&&((firstCol -1 == secondCol)||(firstCol + 1 == secondCol))) return 3;

	    if(firstRow == secondRow) return 3;
	    if((firstRow + 1 != secondRow)&&(firstRow + 2 != secondRow)) return 3;
	    if((firstRow -1 != secondRow)&&(firstRow - 2 != secondRow)) return 3;


	}else if(currentImage == blackCrownPiece){

	    // if(((firstRow + 1 == secondRow)||(firstRow - 1 == secondRow))&&((firstCol -1 == secondCol)||(firstCol + 1 == secondCol))) return 4;
	    if(firstRow == secondRow) return 4;
	    if(((firstRow + 1 != secondRow)&&(firstRow + 2 != secondRow))&&((firstRow -1 != secondRow)&&(firstRow - 2 != secondRow))) return 4;

	}
	return -1;
    }//isInvalid
}