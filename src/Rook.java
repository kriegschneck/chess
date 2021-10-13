/*
 * Piece
 * 
 * v0.9
 * 
 * 12.10.21
 * 
 * Sergei N
 */

class Rook extends Piece {
	/*
	 * Rook
	 */
	
	Rook(Board board, Color color, int x, int y) {
		super(board, color, x, y, "r");
	}
	
	public void run() {
		clearEligiblePositions();
		clearAttackedPositions();
		lineCalculating(getX(), getY(), Direction.UP);
		lineCalculating(getX(), getY(), Direction.DOWN);
		lineCalculating(getX(), getY(), Direction.LEFT);
		lineCalculating(getX(), getY(), Direction.RIGHT);
	}
	
}