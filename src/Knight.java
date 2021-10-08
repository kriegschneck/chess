import java.util.ArrayList;

class Knight extends Piece {
  Knight(Board board, int x, int y) {
    super(board, x, y, "h");
  }

  boolean move() {
    eligiblePositions = new ArrayList<>();
    int x = piecesPosition.getX();
	int y = piecesPosition.getY();

    int newX = x + 1;
    int newY = y - 2;
    if(newX < currentBoard.getColumns() - 1 && newY > 1) {
      if(currentBoard.cells[newX][newY] == null || (isWhite() ^ currentBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new Position(newX, newY));
      }
    }
    newX = x + 1;
    newY = y + 2;
    if(newX < currentBoard.getColumns() - 1 && newY < currentBoard.getRows() - 2) {
      if(currentBoard.cells[newX][newY] == null || (isWhite() ^ currentBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new Position(newX, newY));
      }
    }
    newX = x - 1;
    newY = y - 2;
    if(newX > 0 && newY > 1) {
      if(currentBoard.cells[newX][newY] == null || (isWhite() ^ currentBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new Position(newX, newY));
      }
    }
    newX = x - 1;
    newY = y + 2;
    if(newX > 0 && newY < currentBoard.getRows() - 2) {
      if(currentBoard.cells[newX][newY] == null || (isWhite() ^ currentBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new Position(newX, newY));
      }
    }
    newX = x + 2;
    newY = y - 1;
    if(newX < currentBoard.getColumns() - 2 && newY > 0) {
      if(currentBoard.cells[newX][newY] == null || (isWhite() ^ currentBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new Position(newX, newY));
      }
    }
    newX = x + 2;
    newY = y + 1;
    if(newX < currentBoard.getColumns() - 2 && newY < currentBoard.getRows() - 1) {
      if(currentBoard.cells[newX][newY] == null || (isWhite() ^ currentBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new Position(newX, newY));
      }
    }
    newX = x - 2;
    newY = y - 1;
    if(newX > 1 && newY > 0) {
      if(currentBoard.cells[newX][newY] == null || (isWhite() ^ currentBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new Position(newX, newY));
      }
    }
    newX = x - 2;
    newY = y + 1;
    if(newX > 1 && newY < currentBoard.getRows() - 1) {
      if(currentBoard.cells[newX][newY] == null || (isWhite() ^ currentBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new Position(newX, newY));
      }
    }

    if(eligiblePositions.size() == 0) return false;
	else {
		int randInt = (int) Math.random() * eligiblePositions.size();	//get a random int from the range of eligible positions
		setPosition(eligiblePositions.get(randInt));
		eligiblePositions.clear();
		return true;
	}
    
  }
  
}