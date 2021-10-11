
class Queen extends Piece {
	Queen(Board board, int x, int y) {
		super(board, x, y, "q");
	}
	
	Queen(Board board, int x, int y, String name) {
		super(board, x, y, name);
	}

	boolean findEligiblePosition() {
		clearEligiblePosition();
		queenCalculating(getX(), getY());
		if(noEligiblePositions()) return false;
		else return true;
	}	

	void queenCalculating(int x, int y) {
		rookCalculating(x, y);
		bishopCalculating(x, y);
	}
	
	void rookCalculating(int x, int y) {
		
		for(int i = x + 1; i < currentBoard.getColumns(); i++) {
			if(currentBoard.isNullHere(i, y)) {
				addEligiblePosition(new Position(i, y));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, y)) {
				addEligiblePosition(new Position(i, y));
				break;
			}
			else break;
    		}
    	for(int i = x - 1; i >= 0; i--) {
			if(currentBoard.isNullHere(i, y)) {
				addEligiblePosition(new Position(i, y));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, y)) {
				addEligiblePosition(new Position(i, y));
				break;
			}
			else break;
		}
    	for(int i = y + 1; i < currentBoard.getRows(); i++) {
			if(currentBoard.isNullHere(x, i)) {
				addEligiblePosition(new Position(x, i));
				continue;
			}
			if(currentBoard.isEnemyHere(this, x, i)) {
				addEligiblePosition(new Position(x, i));
				break;
			}
			else break;
		}
    	for(int i = y - 1; i >= 0; i--) {
			if(currentBoard.isNullHere(x, i)) {
				addEligiblePosition(new Position(x, i));
				continue;
			}
			if(currentBoard.isEnemyHere(this, x, i)) {
				addEligiblePosition(new Position(x, i));
				break;
			}
			else break;
		}
    	
	}
	
	void bishopCalculating(int x, int y) {
		
		for(int i = x + 1, j = y + 1; i < currentBoard.getRows() && j < currentBoard.getColumns(); i++, j++) {
			if(currentBoard.isNullHere(i, j)) {
				addEligiblePosition(new Position(i, j));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, j)) {
				addEligiblePosition(new Position(i, j));
				break;
			}
			else break;
		}
		
		for(int i = x + 1, j = y - 1; i < currentBoard.getRows() && j >= 0; i++, j--) {
			if(currentBoard.isNullHere(i, j)) {
				addEligiblePosition(new Position(i, j));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, j)) {
				addEligiblePosition(new Position(i, j));
				break;
			}
			else break;
		}
		
		for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if(currentBoard.isNullHere(i, j)) {
				addEligiblePosition(new Position(i, j));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, j)) {
				addEligiblePosition(new Position(i, j));
				break;
			}
			else break;
		}
		
		for(int i = x - 1, j = y + 1; i >= 0 && j < currentBoard.getColumns(); i--, j++) {
			if(currentBoard.isNullHere(i, j)) {
				addEligiblePosition(new Position(i, j));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, j)) {
				addEligiblePosition(new Position(i, j));
				break;
			}
			else break;
		}
		
	}
}