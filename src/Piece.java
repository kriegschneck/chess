/*
 * Piece
 * 
 * v0.9
 * 
 * 12.10.21
 * 
 * Sergei N
 */

import java.util.ArrayList;

abstract class Piece implements Runnable {
	/*
	 * superclass for all the pieces. implements all the fields and methods but run()
	 */
	
	Board currentBoard;         //a link to the current board to play on
	Thread thread;
	
	private Color color;
	private String name;		//black pieces go with upper case letters
	private Position position;  //a position of a figure
	private ArrayList<Position> eligiblePositions;
	private boolean onInitialPosition;
	
	enum Color {
		WHITE,
		BLACK
	}
	
	enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		UP_LEFT,
		UP_RIGHT,
		DOWN_LEFT,
		DOWN_RIGHT
	}
	
	class Position {
		/*
		 * contains coordinates of a piece
		 */
		
		private int x, y;
		
		Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		int getX() {
			return x;
		}
		
		int getY() {
			return y;
		}
		
		public String toString() {
			return "[" + x + ", " + y + "]";
		}
		
	}

	Piece(Board board, Color color, int x, int y, String name) {
		currentBoard = board;
		this.color = color;
		board.setPiecesPositionOnBoard(this, new Position(x, y));	//set position on the board
		eligiblePositions = new ArrayList<>();
		onInitialPosition = true;
		
		if (isWhite()) {
			this.name = name;
		} else {
			this.name = name.toUpperCase();	//set the name. black pieces go with upper case letters
		}
		
	}
	
	int getX() {
		return position.x;
	}
	
	int getY() {
		return position.y;
	}
	
	void setPosition(Position position) {
		this.position = position;
		if (onInitialPosition) {
			onInitialPosition = false;
		}
	}
	
	String printPosition() {
		return position.toString();
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
	
	boolean isWhite() {
		return this.color.equals(Color.WHITE);
	}
	
	public String toString() {
		return name;
	}
	
	void findEligiblePosition() {
		thread = new Thread(this);
		thread.start();
	}
	
	void pawnCalculating (int x, int y) {
		if (isWhite()) { //calculating eligible moves if the piece is white
			if (y == Board.ROWS - 1) return;
			if (onInitialPosition && currentBoard.isNullHere(x, y + 1) && currentBoard.isNullHere(x, y + 2)) {  //advance
				eligiblePositions.add(new Position(x, y + 2));
			}
			if (currentBoard.isNullHere(x, y + 1)) {  //base move
				eligiblePositions.add(new Position(x, y + 1));
			}
			if (x > 0 && !currentBoard.isNullHere(x - 1, y + 1) && currentBoard.isEnemyHere(this, x - 1, y + 1)) {  //kill to the left
				eligiblePositions.add(new Position(x - 1, y + 1));
			}
			if (x < Board.COLUMNS - 1 && !currentBoard.isNullHere(x + 1, y + 1) && currentBoard.isEnemyHere(this, x + 1, y + 1)) {  //kill to the right
				eligiblePositions.add(new Position(x + 1, y + 1));
			} 
		} else {  //if the piece is black
			if (y == 0) return;
			if (onInitialPosition && currentBoard.isNullHere(x, y - 1) && currentBoard.isNullHere(x, y - 2)) {  //advance
				eligiblePositions.add(new Position(x, y - 2));
			}
			if (currentBoard.isNullHere(x, y - 1)) {  //base move
				eligiblePositions.add(new Position(x, y - 1));
			}
			if (x > 0 && !currentBoard.isNullHere(x - 1, y - 1) && currentBoard.isEnemyHere(this, x - 1, y - 1)) {  //kill to the left
				eligiblePositions.add(new Position(x - 1, y - 1));
			}
			if (x < Board.COLUMNS - 1 && !currentBoard.isNullHere(x + 1, y - 1) && currentBoard.isEnemyHere(this, x + 1, y - 1)) {  //kill to the right
				eligiblePositions.add(new Position(x + 1, y - 1));
			}
		}
		
	}

	void knightCalculating(int x, int y) {
		if (x < Board.COLUMNS - 1 && y > 1) {
			CheckPosition(x + 1, y - 2);
		}
		
		if (x < Board.COLUMNS - 1 && y < Board.ROWS - 2) {
			CheckPosition(x + 1, y + 2);
		}
		
		if (x > 0 && y > 1) {
			CheckPosition(x - 1, y - 2);
		}
		
		if (x > 0 && y < Board.ROWS - 2) {
			CheckPosition(x - 1, y + 2);
		}
		
		if (x < Board.COLUMNS - 2 && y > 0) {
			CheckPosition(x + 2, y - 1);
		}
		
		if (x < Board.COLUMNS - 2 && y < Board.ROWS - 1) {
			CheckPosition(x + 2, y + 1);
		}
		
		if (x > 1 && y > 0) {
			CheckPosition(x - 2,  y - 1);
		}
		
		if (x > 1 && y < Board.ROWS - 1) {
			CheckPosition(x - 2, y + 1);
		}
		
	}

	void lineCalculating(int x, int y, Direction direction) {
		switch (direction) {	
		case UP:
			for (int i = x + 1; i < Board.COLUMNS; i++) {
				if (!CheckPosition(i, y)) break;
		    }
			break;
		case DOWN:
			for (int i = x - 1; i >= 0; i--) {
		    	if (!CheckPosition(i, y)) break;
			}
			break;	
		case LEFT:
			for (int i = y - 1; i >= 0; i--) {
		    	if (!CheckPosition(x, i)) break;
			}
			break;			
		case RIGHT:
			for (int i = y + 1; i < Board.ROWS; i++) {
	    		if (!CheckPosition(x, i)) break;
			}
			break;
		case UP_LEFT:
			for (int i = x - 1, j = y + 1; i >= 0 && j < Board.ROWS; i--, j++) {
				if (!CheckPosition(i, j)) break;
			}
			break;					
		case UP_RIGHT:
			for (int i = x + 1, j = y + 1; i < Board.COLUMNS && j < Board.ROWS; i++, j++) {
				if (!CheckPosition(i, j)) break;
			}
			break;
		case DOWN_LEFT:
			for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
				if (!CheckPosition(i, j)) break;
			}
			break;
		case DOWN_RIGHT:
			for (int i = x + 1, j = y - 1; i < Board.COLUMNS && j >= 0; i++, j--) {
				if (!CheckPosition(i, j)) break;
			}
			break;
		default:
			break;
		}
	
	}
	
	boolean CheckPosition(int x, int y) {	//continue checking if true
		if (currentBoard.isNullHere(x, y)) {
			eligiblePositions.add(new Position(x, y));
			return true;
		} else if (currentBoard.isEnemyHere(this, x, y)) {
			eligiblePositions.add(new Position(x, y));
			return false;
		} else {
			return false;	//if this position is occupied by a piece of the same color 
		}
	}

	void kingCalculating(int x, int y) {
		King anotherKing = currentBoard.getKingOfDifferentColor(this);
		int anotherKingsX = anotherKing.getX();
		int anotherKingsY = anotherKing.getY();
		int dx;	//horizontal distance to another king
		int dy;	//vertical distance to another king

		for (int i = x - 1; i <= x + 1; i++) {
			if (i < 0 || i >= Board.COLUMNS) continue;	
			dx = Math.abs(i - anotherKingsX);
			
			for (int j = y - 1; j <= y + 1; j++) {
				if (j < 0 || j >= Board.ROWS) continue;	
				if (i == x && j == y) continue;			//if this is the initial position
				dy = Math.abs(j - anotherKingsY);
				if (dx <= 1 && dy <= 1) continue;		//this position is too close to another king's one
				
				CheckPosition(i, j);
			}
		}
		
	}
	
	void roque() {
		if(onInitialPosition) {
			
		}
	}
	
	
}

	