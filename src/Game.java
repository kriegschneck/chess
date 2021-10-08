
class Game {
	
	public static void main(String[] args) {
	    final int rows = 8;     //must be 8
	    final int columns = 8;  //must be 8
	    
	    Board board = new Board(columns, rows); //creating a board
	
	    //playing the game
	    int turnNumber = 0;
	    while(true) { 
	    	//board.printBoard(turnNumber++);
		    board.pw.print("\nTurn " + ++turnNumber + "\n");  
		    try {
		    	board.pieceSelectionAndMove(turnNumber);
		    } catch (Exception e) {
		    	board.pw.println(e);
		    	board.printBoard(turnNumber);
		        break;
      		}
		
		    try {
		    	Thread.sleep(100); //time between turns
	      	} catch (Exception e) {
	      		board.pw.println("The game was interrupted");
		        break;
	      	}
	    }
    
	}
  
}
