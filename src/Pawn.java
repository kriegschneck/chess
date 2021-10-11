
class Pawn extends Piece {
	
	Pawn(Board board, int x, int y) {
		super(board, x, y, "p");
	}

	boolean findEligiblePosition() {
		clearEligiblePosition();
		pawnCalculating(getX(), getY());
		if(noEligiblePositions()) return false;
		else return true;
	}
	
	void pawnCalculating (int x, int y) {
		
		if (isWhite()) { //calculating eligible moves if the piece is white
			if(y == currentBoard.getRows() - 1) return;
			if(y == 1 && currentBoard.isNullHere(x, y + 1) && currentBoard.isNullHere(x, y + 2)) {  //advance
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
			if(y == currentBoard.getRows() - 2 && currentBoard.isNullHere(x, y - 1) && currentBoard.isNullHere(x, y - 2)) {  //advance
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
	
}