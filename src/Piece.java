import java.util.ArrayList;

abstract class Piece implements Runnable {
	
	private boolean color;    	//true = white, false = black
	private String name;
	private Position position;  //a position of a figure
	private ArrayList<Position> eligiblePositions;
	Board currentBoard;         //a link to the current board to play on
	Thread thread;
	
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

	Piece(Board board, int x, int y, String name) {
		currentBoard = board;
		board.setPiecesPositionOnBoard(this, new Position(x, y));	//set position on the board
		eligiblePositions = new ArrayList<>();
		
		if(y <= 2) {
			color = true; //set the color of the piece. true = white
			this.name = name; //set the letter. black pieces go with upper case letters
		}
		else {
			color = false;
			this.name = name.toUpperCase();
		}
	}
	
	int getX() {return position.x;}
	int getY() {return position.y;}
	void setPosition(Position position) {
		this.position = position;
	}
	
	String printPosition() {
		return position.toString();
	}
	
	void addEligiblePosition(Position position) {
		eligiblePositions.add(position);
	}
	
	Position getRandomEligiblePosition() {
		int randInt = (int) (Math.random() * eligiblePositions.size());
		return eligiblePositions.get(randInt);
	}
	
	boolean noEligiblePositions() {
		return eligiblePositions.isEmpty();
	}
	
	void clearEligiblePositions() {
		eligiblePositions.clear();
	}
	
	boolean isWhite() {return color;}
	
	public String toString() {return name;}
	
	boolean findEligiblePosition() {
		thread = new Thread(this);
		thread.start();
		if(noEligiblePositions()) return false;
		else return true;
	}
	
}
	