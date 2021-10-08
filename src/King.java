import java.util.ArrayList;

class King extends Piece {
	King(Board board, int x, int y) {
		super(board, x, y, "k");
	}

	boolean move() {
		eligiblePositions = new ArrayList<>();
		int x = piecesPosition.getX();
		int y = piecesPosition.getY();
		
		for(int i = x - 1; i <= x + 1; i++) {
			if(i < 0 || i >= currentBoard.getColumns()) continue;
			for(int j = y - 1; j <= y + 1; j++) {
				if(j < 0 || j >= currentBoard.getRows()) continue;
				if(i == x && j == y) continue;
				if(currentBoard.cells[i][j] == null || isWhite() ^ currentBoard.cells[i][j].isWhite()) {
					eligiblePositions.add(new Position(i, j));
				}
			}
		}

		if(eligiblePositions.size() == 0) return false;
		else {
			int randInt = (int) Math.random() * eligiblePositions.size();	//get a random int from the range of eligible positions
			setPosition(eligiblePositions.get(randInt));
			eligiblePositions.clear();
			return true;
		}
		
	}
	
}