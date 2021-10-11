
class Pawn extends Piece {
	
	Pawn(Board board, Color color, int x, int y) {
		super(board, color, x, y, "p");
	}
	
	public void run() {
		clearEligiblePositions();
		pawnCalculating(getX(), getY());
	}
	
}