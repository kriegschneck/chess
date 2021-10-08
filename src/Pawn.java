import java.util.ArrayList;

class Pawn extends Piece {
	
	Pawn(Board board, int x, int y) {
		super(board, x, y, "p");
	}

	boolean move() {
		ArrayList<Position> eligiblePositions = new ArrayList<>();
		int x = piecesPosition.getX();
		int y = piecesPosition.getY();
		
		if (isWhite()) { //calculating eligible moves if the piece is white
			if(y == currentBoard.getRows() - 1) return false;
			if(y == 1 && currentBoard.isNullHere(x, y + 1) && currentBoard.isNullHere(x, y + 2)) {  //advance
				eligiblePositions.add(new Position(x, y + 2));
			}
			if(currentBoard.isNullHere(x, y + 1)) {  //base move
				eligiblePositions.add(new Position(x, y + 1));
			}
			if(x > 0 && !currentBoard.isNullHere(x - 1, y + 1) && currentBoard.isEnemyHere(this, x - 1, y + 1)) {  //kill to the left
				eligiblePositions.add(new Position(x - 1, y + 1));
			}
			if(x < currentBoard.getColumns() - 1 && !currentBoard.isNullHere(x + 1, y + 1) && currentBoard.isEnemyHere(this, x + 1, y + 1)) {  //kill to the right
				eligiblePositions.add(new Position(x + 1, y + 1));
			}
		}
		else {  //if the piece is black
			if(y == 0) return false;
			if(y == currentBoard.getRows() - 2 && currentBoard.isNullHere(x, y - 1) && currentBoard.isNullHere(x, y - 2)) {  //advance
				eligiblePositions.add(new Position(x, y - 2));
			}
			if(currentBoard.isNullHere(x, y - 1)) {  //base move
				eligiblePositions.add(new Position(x, y - 1));
			}
			if(x > 0 && !currentBoard.isNullHere(x - 1, y - 1) && currentBoard.isEnemyHere(this, x - 1, y - 1)) {  //kill to the left
				eligiblePositions.add(new Position(x - 1, y - 1));
			}
			if(x < currentBoard.getColumns() - 1 && !currentBoard.isNullHere(x + 1, y - 1) && currentBoard.isEnemyHere(this, x + 1, y - 1)) {  //kill to the right
				eligiblePositions.add(new Position(x + 1, y - 1));
			}
		}

		if(eligiblePositions.size() == 0) return false;
		else {
			int randInt = (int) (Math.random() * eligiblePositions.size());	//get a random int from the range of eligible positions
			currentBoard.setPositionOnBoard(this, eligiblePositions.get(randInt));
			return true;
		}
				
	}
	
}