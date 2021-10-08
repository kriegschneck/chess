
import java.util.ArrayList;

abstract class Piece {
	private boolean piecesColor;    //true = white, false = black
	private String piecesName;    //a letter to print
	Position piecesPosition;         //a position of a figure
	Board currentBoard;             //a link to the current board to play on
	ArrayList<Position> eligiblePositions;
		
	class Position {
		private int x, y;
		
		Position(int x, int y) {
			setXY(x, y);
		}
		
		void setXY(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		int getX() {return x;}
		int getY() {return y;}
		
		public String toString() {
			return "[" + x + ", " + y + "]";
		}
	}

	Piece(Board board, int x, int y, String piecesName) {
		currentBoard = board;
		piecesPosition = new Position(x, y);
		currentBoard.cells[x][y] = this;  //set position on the board
		if(y <= 2) {
			piecesColor = true; //set the color of the piece. true = white
			this.piecesName = piecesName; //set the letter. black pieces go with upper case letters
		}
		else {
			piecesColor = false;
			this.piecesName = piecesName.toUpperCase();
		}
	}

	void setPosition(Position newPosition) { //setting a new position of a piece
		int x = newPosition.getX();
		int y = newPosition.getY();
		if(currentBoard.cells[x][y] != null) {
			if(isWhite()) currentBoard.blacks.remove(currentBoard.cells[x][y]); //kill a piece of different color on a new position
			else currentBoard.whites.remove(currentBoard.cells[x][y]);
		}
		currentBoard.cells[piecesPosition.getX()][piecesPosition.getY()] = null;  //erase the current position of a piece
		piecesPosition.setXY(x, y);	//set a new coordinates
		currentBoard.cells[x][y] = this; 	//move a piece to a new position
		currentBoard.pw.println(" - " + piecesPosition);
	}
	
	boolean isWhite() {return piecesColor;}
	
	public String toString() {return piecesName;}
	
	abstract boolean move();
}
	