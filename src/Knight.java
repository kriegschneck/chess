/*
 * Piece
 * 
 * v0.9
 * 
 * 12.10.21
 * 
 * Sergei N
 */

class Knight extends Piece {
	/*
	 * Knight
	 */
	
	Knight(Board board, Color color, int x, int y) {
		super(board, color, x, y, "h");
	}

	public void run() {
		clearEligiblePositions();
		clearAttackedPositions();
		knightCalculating(getX(), getY());
	}
	
}