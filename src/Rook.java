
class Rook extends Queen {
	Rook(Board board, int x, int y) {
		super(board, x, y, "r");
	}
	
	public void run() {
		clearEligiblePositions();
		rookCalculating(getX(), getY());
	}
	
}