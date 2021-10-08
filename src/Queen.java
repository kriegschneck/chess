import java.util.ArrayList;

class Queen extends Piece {
	Queen(Board board, int x, int y) {
		super(board, x, y, "q");
	}

	boolean move() {
		ArrayList<Position> eligiblePositions = new ArrayList<>();
		int x = piecesPosition.getX();
		int y = piecesPosition.getY();
		
		for(int i = x + 1; i < currentBoard.getColumns(); i++) {
			if(currentBoard.isNullHere(i, y)) {
				eligiblePositions.add(new Position(i, y));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, y)) {
				eligiblePositions.add(new Position(i, y));
				break;
			}
			else break;
    		}
    	for(int i = x - 1; i >= 0; i--) {
			if(currentBoard.isNullHere(i, y)) {
				eligiblePositions.add(new Position(i, y));
				continue;
			}
			if(currentBoard.isEnemyHere(this, i, y)) {
				eligiblePositions.add(new Position(i, y));
				break;
			}
			else break;
		}
    	for(int i = y + 1; i < currentBoard.getRows(); i++) {
			if(currentBoard.isNullHere(x, i)) {
				eligiblePositions.add(new Position(x, i));
				continue;
			}
			if(currentBoard.isEnemyHere(this, x, i)) {
				eligiblePositions.add(new Position(x, i));
				break;
			}
			else break;
		}
    	for(int i = y - 1; i >= 0; i--) {
			if(currentBoard.isNullHere(x, i)) {
				eligiblePositions.add(new Position(x, i));
				continue;
			}
			if(currentBoard.isEnemyHere(this, x, i)) {
				eligiblePositions.add(new Position(x, i));
				break;
			}
			else break;
		}
		
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
		else {
			int randInt = (int) Math.random() * eligiblePositions.size();	//get a random int from the range of eligible positions
			currentBoard.setPositionOnBoard(this, eligiblePositions.get(randInt));
			return true;
		}
    
	}	

}