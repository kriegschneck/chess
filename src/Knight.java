
class Knight extends Piece {
	
	Knight(Board board, int x, int y) {
		super(board, x, y, "h");
	}

	public void run() {
		clearEligiblePositions();
		knightCalculating(getX(), getY());
	}
	
	void knightCalculating(int x, int y) {
		
		int newX = x + 1;
		int newY = y - 2;
		if(newX < currentBoard.getColumns() - 1 && newY > 1) {
			if(currentBoard.isNullHere(newX, newY) || currentBoard.isEnemyHere(this, newX, newY)) {  //if the new cell is empty or contains a piece of different color
				addEligiblePosition(new Position(newX, newY));
			}
		}
		newX = x + 1;
		newY = y + 2;
		if(newX < currentBoard.getColumns() - 1 && newY < currentBoard.getRows() - 2) {
			if(currentBoard.isNullHere(newX, newY) || currentBoard.isEnemyHere(this, newX, newY)) { 
				addEligiblePosition(new Position(newX, newY));
			}
		}
		newX = x - 1;
		newY = y - 2;
		if(newX > 0 && newY > 1) {
			if(currentBoard.isNullHere(newX, newY) || currentBoard.isEnemyHere(this, newX, newY)) { 
				addEligiblePosition(new Position(newX, newY));
			}
		}
		newX = x - 1;
		newY = y + 2;
		if(newX > 0 && newY < currentBoard.getRows() - 2) {
			if(currentBoard.isNullHere(newX, newY) || currentBoard.isEnemyHere(this, newX, newY)) { 
				addEligiblePosition(new Position(newX, newY));
			}
		}
		newX = x + 2;
		newY = y - 1;
		if(newX < currentBoard.getColumns() - 2 && newY > 0) {
			if(currentBoard.isNullHere(newX, newY) || currentBoard.isEnemyHere(this, newX, newY)) { 
				addEligiblePosition(new Position(newX, newY));
			}
		}
		newX = x + 2;
		newY = y + 1;
		if(newX < currentBoard.getColumns() - 2 && newY < currentBoard.getRows() - 1) {
			if(currentBoard.isNullHere(newX, newY) || currentBoard.isEnemyHere(this, newX, newY)) { 
				addEligiblePosition(new Position(newX, newY));
			}
		}
		newX = x - 2;
		newY = y - 1;
		if(newX > 1 && newY > 0) {
			if(currentBoard.isNullHere(newX, newY) || currentBoard.isEnemyHere(this, newX, newY)) { 
				addEligiblePosition(new Position(newX, newY));
			}
		}
		newX = x - 2;
		newY = y + 1;
		if(newX > 1 && newY < currentBoard.getRows() - 1) {
			if(currentBoard.isNullHere(newX, newY) || currentBoard.isEnemyHere(this, newX, newY)) {
				addEligiblePosition(new Position(newX, newY));
			}
		}
		
	}
	
}