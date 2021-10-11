
class Queen extends Piece {
	
	Queen(Board board, Color color, int x, int y) {
		super(board, color, x, y, "q");
	}
	
	public void run() {
		clearEligiblePositions();
		lineCalculating(getX(), getY(), Direction.UP);
		lineCalculating(getX(), getY(), Direction.DOWN);
		lineCalculating(getX(), getY(), Direction.LEFT);
		lineCalculating(getX(), getY(), Direction.RIGHT);
		lineCalculating(getX(), getY(), Direction.UP_LEFT);
		lineCalculating(getX(), getY(), Direction.UP_RIGHT);
		lineCalculating(getX(), getY(), Direction.DOWN_LEFT);
		lineCalculating(getX(), getY(), Direction.DOWN_RIGHT);
	}
	
}