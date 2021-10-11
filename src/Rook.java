
class Rook extends Queen {
	Rook(Board board, int x, int y) {
		super(board, x, y, "r");
	}

	boolean findEligiblePosition() {
		clearEligiblePosition();
		rookCalculating(getX(), getY());
		if(noEligiblePositions()) return false;
		else return true;
	}
	
}