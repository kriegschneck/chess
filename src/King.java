
class King extends Piece {
	
	King(Board board, Color color, int x, int y) {
		super(board, color, x, y, "k");
	}

	public void run() {
		clearEligiblePositions();
		kingCalculating(getX(), getY());
	}
	
	void kingCalculating(int x, int y) {

		King anotherKing = currentBoard.linkToAnotherKing(this);
		int anotherKingsX = anotherKing.getX();
		int anotherKingsY = anotherKing.getY();
		
		if(Math.abs(y - anotherKingsY) >= 3 || Math.abs(x - anotherKingsX) >= 3) { //if another king is too far
			for(int i = x - 1; i <= x + 1; i++) {
				if(i < 0 || i >= currentBoard.getColumns()) continue;
				for(int j = y - 1; j <= y + 1; j++) {
					if(j < 0 || j >= currentBoard.getRows()) continue;
					if(i == x && j == y) continue;
					CheckPosition(i, j);
				}
			}
		}
		else {	//if vertical and horizontal distance between kings are equal or less than 2 
			int dx, dy;
			for(int i = x - 1; i <= x + 1; i++) {
				if(i < 0 || i >= currentBoard.getColumns()) continue;
				dx = Math.abs(i - anotherKingsX);
				
				for(int j = y - 1; j <= y + 1; j++) {
					if(j < 0 || j >= currentBoard.getRows()) continue;
					if(i == x && j == y) continue;
					dy = Math.abs(j - anotherKingsY);
					if(dx <= 1 && dy <= 1) continue;
					
					CheckPosition(i, j);
				}
			}
		}
		
	}
}