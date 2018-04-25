package cs1302.arcade;


import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;


public class TetrisGame extends Stage{

    private GridPane grid = new GridPane();
    private Scene game = new Scene(grid);

    public TetrisGame(){	
	super();
	init();
	setScene(game);
    }

    //gets grid
    public GridPane getGrid(){
	return grid;
    }
    //sets grid
    public void setGrid(GridPane grid){
	this.grid = grid;
    }
   

    public void init(){

	grid.setGridLinesVisible(true);
	int numCols = 10;
	int numRows = 20;
	for(int i = 0; i <numCols; i++){
	    ColumnConstraints colConst = new ColumnConstraints();
	    colConst.setPercentWidth(100.0/numCols);
	    grid.getColumnConstraints().add(colConst);
	}
	for(int i = 0; i <numRows; i++){
	    RowConstraints rowConst = new RowConstraints();
	    rowConst.setPercentHeight(100.0/numRows);
	    grid.getRowConstraints().add(rowConst);
	}

    }


}