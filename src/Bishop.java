
class Bishop extends Queen {
	
	Bishop(Board board, int x, int y) {
		super(board, x, y, "b");
	}
	
	public void run() {
		clearEligiblePositions();
		bishopCalculating(getX(), getY());
	}
	
}