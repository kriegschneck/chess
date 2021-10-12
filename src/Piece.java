import java.util.ArrayList;

abstract class Piece implements Runnable {
	
	private Color color;
	private String name;		//black pieces go with upper case letters
	private Position position;  //a position of a figure
	private ArrayList<Position> eligiblePositions;
	private boolean onInitialPosition;
	Board currentBoard;         //a link to the current board to play on
	Thread thread;
	
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

	Piece(Board board, Color color, int x, int y, String name) {
		currentBoard = board;
		board.setPiecesPositionOnBoard(this, new Position(x, y));	//set position on the board
		this.color = color;
		eligiblePositions = new ArrayList<>();
		onInitialPosition = true;
		
		if(isWhite()) this.name = name;
		else this.name = name.toUpperCase();	//set the name. black pieces go with upper case letters
	}
	
	int getX() {return position.x;}
	int getY() {return position.y;}
	void setPosition(Position position) {
		this.position = position;
		if(onInitialPosition) onInitialPosition = false;
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
	
	boolean isWhite() {return this.color.equals(Color.WHITE);}
	
	public String toString() {return name;}
	
	void findEligiblePosition() {
		thread = new Thread(this);
		thread.start();
	}
	
	void pawnCalculating (int x, int y) {
		
		if (isWhite()) { //calculating eligible moves if the piece is white
			if(y == currentBoard.getRows() - 1) return;
			if(onInitialPosition && currentBoard.isNullHere(x, y + 1) && currentBoard.isNullHere(x, y + 2)) {  //advance
				addEligiblePosition(new Position(x, y + 2));
			}
			if(currentBoard.isNullHere(x, y + 1)) {  //base move
				addEligiblePosition(new Position(x, y + 1));
			}
			if(x > 0 && !currentBoard.isNullHere(x - 1, y + 1) && currentBoard.isEnemyHere(this, x - 1, y + 1)) {  //kill to the left
				addEligiblePosition(new Position(x - 1, y + 1));
			}
			if(x < currentBoard.getColumns() - 1 && !currentBoard.isNullHere(x + 1, y + 1) && currentBoard.isEnemyHere(this, x + 1, y + 1)) {  //kill to the right
				addEligiblePosition(new Position(x + 1, y + 1));
			}
		}
		else {  //if the piece is black
			if(y == 0) return;
			if(onInitialPosition && currentBoard.isNullHere(x, y - 1) && currentBoard.isNullHere(x, y - 2)) {  //advance
				addEligiblePosition(new Position(x, y - 2));
			}
			if(currentBoard.isNullHere(x, y - 1)) {  //base move
				addEligiblePosition(new Position(x, y - 1));
			}
			if(x > 0 && !currentBoard.isNullHere(x - 1, y - 1) && currentBoard.isEnemyHere(this, x - 1, y - 1)) {  //kill to the left
				addEligiblePosition(new Position(x - 1, y - 1));
			}
			if(x < currentBoard.getColumns() - 1 && !currentBoard.isNullHere(x + 1, y - 1) && currentBoard.isEnemyHere(this, x + 1, y - 1)) {  //kill to the right
				addEligiblePosition(new Position(x + 1, y - 1));
			}
		}
		
	}

	void knightCalculating(int x, int y) {
		
		if(x < currentBoard.getColumns() - 1 && y > 1) {
			CheckPosition(x + 1, y - 2);
		}
		
		if(x < currentBoard.getColumns() - 1 && y < currentBoard.getRows() - 2) {
			CheckPosition(x + 1, y + 2);
		}
		
		if(x > 0 && y > 1) {
			CheckPosition(x - 1, y - 2);
		}
		
		if(x > 0 && y < currentBoard.getRows() - 2) {
			CheckPosition(x - 1, y + 2);
		}
		
		if(x < currentBoard.getColumns() - 2 && y > 0) {
			CheckPosition(x + 2, y - 1);
		}
		
		if(x < currentBoard.getColumns() - 2 && y < currentBoard.getRows() - 1) {
			CheckPosition(x + 2, y + 1);
		}
		
		if(x > 1 && y > 0) {
			CheckPosition(x - 2,  y - 1);
		}
		
		if(x > 1 && y < currentBoard.getRows() - 1) {
			CheckPosition(x - 2, y + 1);
		}
		
	}

	void lineCalculating(int x, int y, Direction direction) {
		
		switch(direction) {
			
		case UP:
			for(int i = x + 1; i < currentBoard.getColumns(); i++) {
				if(!CheckPosition(i, y)) break;
		    }
			break;
		
		case DOWN:
			for(int i = x - 1; i >= 0; i--) {
		    	if(!CheckPosition(i, y)) break;
			}
			break;
			
		case LEFT:
			for(int i = y - 1; i >= 0; i--) {
		    	if(!CheckPosition(x, i)) break;
			}
			break;	
				
		case RIGHT:
			for(int i = y + 1; i < currentBoard.getRows(); i++) {
	    		if(!CheckPosition(x, i)) break;
			}
			break;
		
		case UP_LEFT:
			for(int i = x - 1, j = y + 1; i >= 0 && j < currentBoard.getRows(); i--, j++) {
				if(!CheckPosition(i, j)) break;
			}
			break;
							
		case UP_RIGHT:
			for(int i = x + 1, j = y + 1; i < currentBoard.getColumns() && j < currentBoard.getRows(); i++, j++) {
				if(!CheckPosition(i, j)) break;
			}
			break;
	
		case DOWN_LEFT:
			for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
				if(!CheckPosition(i, j)) break;
			}
			break;
			
		case DOWN_RIGHT:
			for(int i = x + 1, j = y - 1; i < currentBoard.getColumns() && j >= 0; i++, j--) {
				if(!CheckPosition(i, j)) break;
			}
			break;
		}
	
	}
	
	boolean CheckPosition(int x, int y) {	//continue checking if true
		
		if(currentBoard.isNullHere(x, y)) {
			addEligiblePosition(new Position(x, y));
			return true;
		}
		else if(currentBoard.isEnemyHere(this, x, y)) {
			addEligiblePosition(new Position(x, y));
			return false;
		}
		else return false;	//if this position is occupied by a piece of the same color 
	}

	void kingCalculating(int x, int y) {
		
		King anotherKing = currentBoard.linkToAnotherKing(this);
		int anotherKingsX = anotherKing.getX();
		int anotherKingsY = anotherKing.getY();
		
		if(Math.abs(y - anotherKingsY) >= 3 || Math.abs(x - anotherKingsX) >= 3) { //if another king is too far
			for(int i = x - 1; i <= x + 1; i++) {
				if(i < 0 || i >= currentBoard.getColumns()) continue;
				for(int j = y - 1; j <= y + 1; j++) {
					if(j < 0 || j >= currentBoard.getRows()) continue;
					if(i == x && j == y) continue;
					CheckPosition(i, j);
				}
			}
		}
		else {	//if vertical and horizontal distance between kings are equal or less than 2 
			int dx, dy;
			for(int i = x - 1; i <= x + 1; i++) {
				if(i < 0 || i >= currentBoard.getColumns()) continue;
				dx = Math.abs(i - anotherKingsX);
				
				for(int j = y - 1; j <= y + 1; j++) {
					if(j < 0 || j >= currentBoard.getRows()) continue;
					if(i == x && j == y) continue;
					dy = Math.abs(j - anotherKingsY);
					if(dx <= 1 && dy <= 1) continue;
					
					CheckPosition(i, j);
				}
			}
		}
		
	}
	
	
	
	
	
}

	