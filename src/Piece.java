
abstract class Piece {
	private boolean piecesColor;    //true = white, false = black
	private String piecesName;    	//a letter to print
	Position piecesPosition;        //a position of a figure
	Board currentBoard;             //a link to the current board to play on
		
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
		board.setPositionOnBoard(this, piecesPosition);	//set position on the board
		
		if(y <= 2) {
			piecesColor = true; //set the color of the piece. true = white
			this.piecesName = piecesName; //set the letter. black pieces go with upper case letters
		}
		else {
			piecesColor = false;
			this.piecesName = piecesName.toUpperCase();
		}
	}
	
	boolean isWhite() {return piecesColor;}
	
	public String toString() {return piecesName;}
	
	abstract boolean move();
}
	