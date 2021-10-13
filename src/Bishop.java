/*
 * Piece
 * 
 * v0.9
 * 
 * 12.10.21
 * 
 * Sergei N
 */

class Bishop extends Piece {
	/*
	 * Bishop
	 */
	
	Bishop(Board board, Color color, int x, int y) {
		super(board, color, x, y, "b");
	}
	
	public void run() {
		clearEligiblePositions();
		clearAttackedPositions();
		lineCalculating(getX(), getY(), Direction.UP_LEFT);
		lineCalculating(getX(), getY(), Direction.UP_RIGHT);
		lineCalculating(getX(), getY(), Direction.DOWN_LEFT);
		lineCalculating(getX(), getY(), Direction.DOWN_RIGHT);
	}
	
}