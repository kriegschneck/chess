
class King extends Piece {
	
	King(Board board, Color color, int x, int y) {
		super(board, color, x, y, "k");
	}

	public void run() {
		clearEligiblePositions();
		kingCalculating(getX(), getY());
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