
class Bishop extends Queen {
	Bishop(Board board, int x, int y) {
		super(board, x, y, "b");
	}

	boolean findEligiblePosition() {
		clearEligiblePosition();
		bishopCalculating(getX(), getY());
		if(noEligiblePositions()) return false;
		else return true;
	}
	
}