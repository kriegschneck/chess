/*
 * Pawn
 * 
 * v0.9
 * 
 * 12.10.21
 * 
 * Sergei N
 */

class Pawn extends Piece {
	/*
	 * Pawn
	 */
	
	Pawn(Board board, Color color, int x, int y) {
		super(board, color, x, y, "p");
	}
	
	public void run() {
		pawnCalculating(getX(), getY());
	}
	
}