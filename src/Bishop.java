
class Bishop extends Piece {
	
	Bishop(Board board, Color color, int x, int y) {
		super(board, color, x, y, "b");
	}
	
	public void run() {
		clearEligiblePositions();
		lineCalculating(getX(), getY(), Direction.UP_LEFT);
		lineCalculating(getX(), getY(), Direction.UP_RIGHT);
		lineCalculating(getX(), getY(), Direction.DOWN_LEFT);
		lineCalculating(getX(), getY(), Direction.DOWN_RIGHT);
	}
	
}