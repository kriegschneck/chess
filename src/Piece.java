import java.util.ArrayList;

abstract class Piece {
	private boolean color;    	//true = white, false = black
	private String name;
	private Position position;  //a position of a figure
	
	Board currentBoard;         //a link to the current board to play on
	ArrayList<Position> eligiblePositions;
	
	class Position {
		private int x, y;
		
		Position(int x, int y) {
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
		board.setPiecesPositionOnBoard(this, new Position(x, y));	//set position on the board
		
		if(y <= 2) {
			color = true; //set the color of the piece. true = white
			this.name = piecesName; //set the letter. black pieces go with upper case letters
		}
		else {
			color = false;
			this.name = piecesName.toUpperCase();
		}
	}
	
	int getX() {return position.x;}
	int getY() {return position.y;}
	void setPosition(Position position) {
		this.position = position;
	}
	
	boolean isWhite() {return color;}
	
	public String toString() {return name;}
	
	abstract boolean findEligiblePosition();
}
	