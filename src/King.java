/*
 * King
 * 
 * v0.9
 * 
 * 12.10.21
 * 
 * Sergei N
 */

class King extends Piece {
	/*
	 * King
	 */
	
	King(Board board, Color color, int x, int y) {
		super(board, color, x, y, "k");
	}

	public void run() {
		kingCalculating(getX(), getY());
	}
		
}