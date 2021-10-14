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
	static int turnNumber = 0;
	
	public static void main(String[] args) {
	    Board board = new Board(); 	
	    
	    //playing the game
	    while (true) {
		    System.out.print("\nTurn " + ++turnNumber + "\n");  
		    try {
		    	board.chooseActiveSetOfPieces();
		    	board.selectPiece();
		    	board.movePiece();
		    	Thread.sleep(1);
		    } catch (InterruptedException e) {
		    	System.out.println("The game was interrupted\n" + e);
		        break;
		    } catch (Exception e) {
		    	System.out.println("\n" + e);
		    	board.printBoard();
		        break;
      		}
	    }
    
	}
  
}
