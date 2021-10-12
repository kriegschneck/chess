
class King extends Piece {
	
	King(Board board, Color color, int x, int y) {
		super(board, color, x, y, "k");
	}

	public void run() {
		clearEligiblePositions();
		kingCalculating(getX(), getY());
	}
	
}