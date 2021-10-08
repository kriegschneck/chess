import java.util.ArrayList;

class Bishop extends Piece {
	Bishop(Board board, int x, int y) {
		super(board, x, y, "b");
	}

	boolean move() {
		eligiblePositions = new ArrayList<>();
		int x = piecesPosition.getX();
		int y = piecesPosition.getY();

		for(int i = x + 1, j = y + 1; i < currentBoard.getRows() && j < currentBoard.getColumns(); i++, j++) {
			if(currentBoard.cells[i][j] == null) {
				eligiblePositions.add(new Position(i, j));
				continue;
			}
			if(isWhite() ^ currentBoard.cells[i][j].isWhite()) {
				eligiblePositions.add(new Position(i, j));
				break;
			}
			else break;
		}
		
		for(int i = x + 1, j = y - 1; i < currentBoard.getRows() && j >= 0; i++, j--) {
			if(currentBoard.cells[i][j] == null) {
				eligiblePositions.add(new Position(i, j));
				continue;
			}
			if(isWhite() ^ currentBoard.cells[i][j].isWhite()) {
				eligiblePositions.add(new Position(i, j));
				break;
			}
			else break;
		}
		
		for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if(currentBoard.cells[i][j] == null) {
				eligiblePositions.add(new Position(i, j));
				continue;
			}
			if(isWhite() ^ currentBoard.cells[i][j].isWhite()) {
				eligiblePositions.add(new Position(i, j));
				break;
			}
			else break;
		}
		
		for(int i = x - 1, j = y + 1; i >= 0 && j < currentBoard.getColumns(); i--, j++) {
			if(currentBoard.cells[i][j] == null) {
				eligiblePositions.add(new Position(i, j));
				continue;
			}
			if(isWhite() ^ currentBoard.cells[i][j].isWhite()) {
				eligiblePositions.add(new Position(i, j));
				break;
			}
			else break;
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