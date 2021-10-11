
class Knight extends Piece {
	
	Knight(Board board, Color color, int x, int y) {
		super(board, color, x, y, "h");
	}

	public void run() {
		clearEligiblePositions();
		knightCalculating(getX(), getY());
	}
	
}