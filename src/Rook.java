import java.util.ArrayList;

class Rook extends Piece {
	Rook(Board board, int x, int y) {
		super(board, x, y, "r");
	}

	boolean move() {
		ArrayList<Position> eligiblePositions = new ArrayList<>();
		int x = piecesPosition.getX();
		int y = piecesPosition.getY();
		
		for(int i = x + 1; i < currentBoard.getColumns(); i++) {
			if(currentBoard.whatInCell[i][y] == null) {
				eligiblePositions.add(new Position(i, y));
				continue;
			}
			if(isWhite() ^ currentBoard.whatInCell[i][y].isWhite()) {
				eligiblePositions.add(new Position(i, y));
				break;
			}
			else break;
    		}
    	for(int i = x - 1; i >= 0; i--) {
			if(currentBoard.whatInCell[i][y] == null) {
				eligiblePositions.add(new Position(i, y));
				continue;
			}
			if(isWhite() ^ currentBoard.cells[i][y].isWhite()) {
				eligiblePositions.add(new Position(i, y));
				break;
			}
			else break;
		}
    	for(int i = y + 1; i < currentBoard.getRows(); i++) {
			if(currentBoard.whatInCell[x][i] == null) {
				eligiblePositions.add(new Position(x, i));
				continue;
			}
			if(isWhite() ^ currentBoard.whatInCell[x][i].isWhite()) {
				eligiblePositions.add(new Position(x, i));
				break;
			}
			else break;
		}
    	for(int i = y - 1; i >= 0; i--) {
			if(currentBoard.whatInCell[x][i] == null) {
				eligiblePositions.add(new Position(x, i));
				continue;
			}
			if(isWhite() ^ currentBoard.whatInCell[x][i].isWhite()) {
				eligiblePositions.add(new Position(x, i));
				break;
			}
			else break;
		}
    		
		if(eligiblePositions.size() == 0) return false;
		else {
			int randInt = (int) Math.random() * eligiblePositions.size();	//get a random int from the range of eligible positions
			currentBoard.setPositionOnBoard(this, piecesPosition);
			return true;
		}
    
	}
	
}