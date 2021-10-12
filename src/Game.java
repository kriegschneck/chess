/*
 * Game
 * 
 * v0.9
 * 
 * 12.10.21
 * 
 * Sergei N
 */

class Game {
	/*
	 * This class contains main method which creates a board and starts an endless gaming cycle.
	 * The cycle lasts until there is no any pieces from one of the sides that have eligible move or
	 * the main thread gets interrupted
	 */
	
	public static void main(String[] args) {
	    int turnNumber = 0;
	    Board board = new Board(); 	//creating a board
	    
	    //playing the game
	    while (true) {
	    	//board.printBoard(turnNumber++);
		    System.out.print("\nTurn " + ++turnNumber + "\n");  
		    try {
		    	board.selectPieceAndMove(turnNumber);
		    } catch (Exception e) {
		    	System.out.println("\n" + e);
		    	board.printBoard(turnNumber);
		        break;
      		}
		
		    try {
		    	Thread.sleep(1); //time between turns
	      	} catch (Exception e) {
	      		System.out.println("The game was interrupted");
		        break;
	      	}
	    }
    
	}
  
}
