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
	 ArrayList<Position> eligiblePositions;
	private ArrayList<Position> attackedPositions;
	private boolean onInitialPosition;
	
	enum Color {
		WHITE,
		BLACK;

		boolean isWhite() {
			return this.equals(WHITE);
		}
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
		
		public boolean equals(Object position) {
			if (position instanceof Position) {
				return (x == ((Position) position).x && y == ((Position) position).y);
			}
			return false;
		}
		
		public String toString() {
			return ("[" + (char) (x + 97) + (y + 1) + "]");
		}
		
	}

	Piece(Board board, Color color, int x, int y, String name) {
		currentBoard = board;
		this.color = color;
		board.setPiecesPositionOnBoard(this, new Position(x, y));	//set position on the board
		eligiblePositions = new ArrayList<>();
		attackedPositions = new ArrayList<>();
		onInitialPosition = true;
		
		if (color.isWhite()) {
			this.name = name;
		} else {
			this.name = name.toUpperCase();	//set the name. black pieces go with upper case letters
		}
		
	}
	
	void setPosition(Position position) {
		this.position = position;
		if (onInitialPosition) {
			onInitialPosition = false;
		}
	}
	
	int getX() {
		return position.x;
	}
	
	int getY() {
		return position.y;
	}
	
	boolean isWhite() {
		return this.color.isWhite();
	}
	
	boolean isOnInitialPosition() {
		return onInitialPosition;
	}
	
	public String toString() {
		return name;
	}
	
	String printPosition() {
		return position.toString();
	}
	
	Position getRandomEligiblePosition() {
		int randInt = (int) (Math.random() * eligiblePositions.size());
		return eligiblePositions.get(randInt);
	}
	
	boolean hasNoEligiblePositions() {
		return eligiblePositions.isEmpty();
	}
	
	void clearEligiblePositions() {
		eligiblePositions.clear();
	}
	
	void clearAttackedPositions() {
		attackedPositions.clear();
	}
	
	boolean findPositionAmongAttacked(Position position) {
		return attackedPositions.contains(position);
	}
	
	void calculatePositions() {
		thread = new Thread(this);
		thread.start();
	}
	
	void pawnCalculating (int x, int y) {
		if (color.isWhite()) { //calculating eligible moves if the piece is white
			if (y == Board.ROWS - 1) return;
			if (onInitialPosition && currentBoard.isNullHere(x, y + 1) && currentBoard.isNullHere(x, y + 2)) {  //advance
				eligiblePositions.add(new Position(x, y + 2));
			}
			if (currentBoard.isNullHere(x, y + 1)) {  //base move
				eligiblePositions.add(new Position(x, y + 1));
			}
			if (x > 0 && !currentBoard.isNullHere(x - 1, y + 1) 
					&& !color.equals(currentBoard.getPieceByPosition(x - 1, y + 1).color)) {  //kill to the left
				eligiblePositions.add(new Position(x - 1, y + 1));
				attackedPositions.add(new Position(x - 1, y + 1));
			}
			if (x < Board.COLUMNS - 1 && !currentBoard.isNullHere(x + 1, y + 1) 
					&& !color.equals(currentBoard.getPieceByPosition(x + 1, y + 1).color)) {  //kill to the right
				eligiblePositions.add(new Position(x + 1, y + 1));
				attackedPositions.add(new Position(x + 1, y + 1));
			} 
		} else {  //if the piece is black
			if (y == 0) return;
			if (onInitialPosition && currentBoard.isNullHere(x, y - 1) && currentBoard.isNullHere(x, y - 2)) {  //advance
				eligiblePositions.add(new Position(x, y - 2));
			}
			if (currentBoard.isNullHere(x, y - 1)) {  //base move
				eligiblePositions.add(new Position(x, y - 1));
			}
			if (x > 0 && !currentBoard.isNullHere(x - 1, y - 1) 
					&& !color.equals(currentBoard.getPieceByPosition(x - 1, y - 1).color)) {  //kill to the left
				eligiblePositions.add(new Position(x - 1, y - 1));
				attackedPositions.add(new Position(x - 1, y - 1));
			}
			if (x < Board.COLUMNS - 1 && !currentBoard.isNullHere(x + 1, y - 1) 
					&& !color.equals(currentBoard.getPieceByPosition(x + 1, y - 1).color)) {  //kill to the right
				eligiblePositions.add(new Position(x + 1, y - 1));
				attackedPositions.add(new Position(x + 1, y - 1));
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
			attackedPositions.add(new Position(x, y));
			return true;
		} else if (!color.equals(currentBoard.getPieceByPosition(x, y).color)) {
			eligiblePositions.add(new Position(x, y));
			return false;
		} else {	//if this position is occupied by a piece of the same color 
			attackedPositions.add(new Position(x, y));
			return false;	
		}
	}

	void kingCalculating(int x, int y) {
		for (int i = x - 1; i <= x + 1; i++) {
			if (i < 0 || i >= Board.COLUMNS) continue;	
			
			for (int j = y - 1; j <= y + 1; j++) {
				if (j < 0 || j >= Board.ROWS) continue;	
				if (i == x && j == y) continue;			//if this is the initial position
				
				if(!currentBoard.isPositionUnderAttack(color, new Position(i, j))) {
					CheckPosition(i, j);
				}
				
			}
		}
		
		//Roque
		if (onInitialPosition) {
			if (color.isWhite()) {
				if (!currentBoard.isNullHere(0, 0)
						&& currentBoard.getPieceByPosition(0, 0).onInitialPosition	//rook is on the initial position
						&& currentBoard.isNullHere(1, 0)					
						&& currentBoard.isNullHere(2, 0) 
						&& currentBoard.isNullHere(3, 0)
						&& !currentBoard.isPositionUnderAttack(color, position)
						&& !currentBoard.isPositionUnderAttack(color, new Position(2, 0))) {	
					eligiblePositions.add(new Position(2, 0));
				}
				
				if (!currentBoard.isNullHere(7, 0)
						&& currentBoard.getPieceByPosition(7, 0).onInitialPosition
						&& currentBoard.isNullHere(5, 0)					
						&& currentBoard.isNullHere(6, 0) 
						&& !currentBoard.isPositionUnderAttack(color, position)
						&& !currentBoard.isPositionUnderAttack(color, new Position(6, 0))) {
					eligiblePositions.add(new Position(6, 0));
				}
				
			} else {
				if (!currentBoard.isNullHere(0, 7)
						&& currentBoard.getPieceByPosition(0, 7).onInitialPosition	//rook is on the initial position
						&& currentBoard.isNullHere(1, 7)					
						&& currentBoard.isNullHere(2, 7) 
						&& currentBoard.isNullHere(3, 7)
						&& !currentBoard.isPositionUnderAttack(color, position)
						&& !currentBoard.isPositionUnderAttack(color, new Position(2, 7))) {
					eligiblePositions.add(new Position(2, 7));
				}
				
				if (!currentBoard.isNullHere(7, 7)
						&& currentBoard.getPieceByPosition(7, 7).onInitialPosition
						&& currentBoard.isNullHere(5, 7)					
						&& currentBoard.isNullHere(6, 7) 
						&& !currentBoard.isPositionUnderAttack(color, position)
						&& !currentBoard.isPositionUnderAttack(color, new Position(6, 7))) {
					eligiblePositions.add(new Position(6, 7));
				}
			}
			
		}
		
	}

}

	