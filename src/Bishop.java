import java.util.ArrayList;

class Bishop extends Piece {
	Bishop(Board board, int x, int y) {
		super(board, x, y, "b");
	}

	boolean findEligiblePosition() {
		eligiblePositions = new ArrayList<>();
		int x = getX();
		int y = getY();

		for(int i = x + 1, j = y + 1; i < currentBoard.getRows() && j < currentBoard.getColumns(); i++, j++) {
			if(currentBoard.isNullHere(i, j)) {
				eligiblePositions.add(new Position(i, j));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, j)) {
				eligiblePositions.add(new Position(i, j));
				break;
			}
			else break;
		}
		
		for(int i = x + 1, j = y - 1; i < currentBoard.getRows() && j >= 0; i++, j--) {
			if(currentBoard.isNullHere(i, j)) {
				eligiblePositions.add(new Position(i, j));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, j)) {
				eligiblePositions.add(new Position(i, j));
				break;
			}
			else break;
		}
		
		for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if(currentBoard.isNullHere(i, j)) {
				eligiblePositions.add(new Position(i, j));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, j)) {
				eligiblePositions.add(new Position(i, j));
				break;
			}
			else break;
		}
		
		for(int i = x - 1, j = y + 1; i >= 0 && j < currentBoard.getColumns(); i--, j++) {
			if(currentBoard.isNullHere(i, j)) {
				eligiblePositions.add(new Position(i, j));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, j)) {
				eligiblePositions.add(new Position(i, j));
				break;
			}
			else break;
		}

		if(eligiblePositions.size() == 0) return false;
		else return true;
    
	}
	
}