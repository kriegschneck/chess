
class King extends Piece {
	King(Board board, int x, int y) {
		super(board, x, y, "k");
	}

	boolean findEligiblePosition() {
		clearEligiblePosition();
		kingCalculating(getX(), getY());
		if(noEligiblePositions()) return false;
		else return true;
	}
	
	void kingCalculating(int x, int y) {
		
		for(int i = x - 1; i <= x + 1; i++) {
			if(i < 0 || i >= currentBoard.getColumns()) continue;
			for(int j = y - 1; j <= y + 1; j++) {
				if(j < 0 || j >= currentBoard.getRows()) continue;
				if(i == x && j == y) continue;
				if(currentBoard.isNullHere(i, j) || currentBoard.isEnemyHere(this, i, j)) {
					addEligiblePosition(new Position(i, j));
				}
			}
		}
		
	}
	
}