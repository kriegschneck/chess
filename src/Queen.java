/*
 * Queen
 * 
 * v0.9
 * 
 * 12.10.21
 * 
 * Sergei N
 */

class Queen extends Piece {
	/*
	 * Queen
	 */
	
	Queen(Board board, Color color, int x, int y) {
		super(board, color, x, y, "q");
	}
	
	public void run() {
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