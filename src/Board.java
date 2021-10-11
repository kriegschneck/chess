
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

class Board {
	
	PrintWriter pw = new PrintWriter(System.out, true); //to print stuff
	private int rows, columns;
	//cells contain either null or a link to a piece if
	//the cells' indexes are equal to the position of the piece
	private Piece[][] positionOnBoard;
	private ArrayList<Piece> whites;  //list of white pieces
	private ArrayList<Piece> blacks;  //list of black pieces
	private ArrayList<Piece> setOfPieces;	//buffer list

	Board(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;

		positionOnBoard = new Piece[columns][rows];
		
		whites = new ArrayList<>();
		for(int i = 0; i < 8; i++) {	//add 8 pawns to the list of pieces
		    whites.add(new Pawn(this, i, 1));
			}
		whites.add(new Rook(this, 0, 0));
		whites.add(new Rook(this, 7, 0));
		whites.add(new Knight(this, 1, 0));
		whites.add(new Knight(this, 6, 0));
		whites.add(new Bishop(this, 2, 0));
		whites.add(new Bishop(this, 5, 0));
		whites.add(new Queen(this, 3, 0));
		whites.add(new King(this, 4, 0));
		
		blacks  = new ArrayList<>();
		for(int i = 0; i < 8; i++) {	//add 8 pawns to the list of pieces
    		blacks.add(new Pawn(this, i, rows - 2));
    	}
    	blacks.add(new Rook(this, 0, rows - 1));
    	blacks.add(new Rook(this, 7, rows - 1));
    	blacks.add(new Knight(this, 1, rows - 1));
    	blacks.add(new Knight(this, 6, rows - 1));
    	blacks.add(new Bishop(this, 2, rows - 1));
    	blacks.add(new Bishop(this, 5, rows - 1));
    	blacks.add(new Queen(this, 3, rows - 1));
    	blacks.add(new King(this, 4, rows - 1));
	}

	int getRows() {return rows;}
	int getColumns() {return columns;}

	//choosing a piece to make a move
	void selectPieceAndMove(int turnNumber) throws Exception {
		if(turnNumber % 2 == 0) setOfPieces = blacks;
		else setOfPieces = whites;
		
		Iterator<Piece> iterator = setOfPieces.iterator();
		while(iterator.hasNext()) iterator.next().findEligiblePosition();	//calculate all the pieces in multiple threads
		while(iterator.hasNext()) iterator.next().thread.join();			//waiting threads to finish
		
		//random piece makes a move
		int randInt;
		ArrayList<Integer> excludedNumbers = new ArrayList<>();
		for(int i = 0; i < setOfPieces.size(); i++) {	//amount of tries to make a move equals to amount of pieces in the set
			do {
				randInt = (int) (Math.random() * (setOfPieces.size())); //get a random number excluding ones that have no eligible moves
			} while(/*!excludedNumbers.isEmpty() && */excludedNumbers.contains(randInt));
			 
			pw.print(setOfPieces.get(randInt) + " " + setOfPieces.get(randInt).printPosition());
			
			if(!setOfPieces.get(randInt).noEligiblePositions()) {
				moveToRandomEligiblePosition(setOfPieces.get(randInt));
				return;	//random piece makes a move. if a move was successful go to next turn
			}
			else {                                        	//if move didn't succeed
				excludedNumbers.add(randInt);
				pw.println(" - couldn't make a move");
			}
		}
		throw new Exception("No piece can make a move.\n"); //is there wasn't any successful moves
	}
	
	void moveToRandomEligiblePosition(Piece piece) { //setting a new position of a piece
		Piece.Position bufferPosition = piece.getRandomEligiblePosition();
		int x = bufferPosition.getX();
		int y = bufferPosition.getY();
		
		if(!isNullHere(x, y)) {
			if(piece.isWhite()) blacks.remove(positionOnBoard[x][y]); //kill a piece of different color on a new position
			else whites.remove(positionOnBoard[x][y]);
		}
		positionOnBoard[piece.getX()][piece.getX()] = null;	//erase current position on board
		setPiecesPositionOnBoard(piece, bufferPosition);
		pw.println(" - " + piece.printPosition());
	}
	
	void setPiecesPositionOnBoard(Piece piece, Piece.Position position) {
		piece.setPosition(position);							//set a new position of a piece
		positionOnBoard[piece.getX()][piece.getY()] = piece;	//put the piece on the board at the same coordinates
	}
	
	boolean isNullHere(int x, int y) {
		if(positionOnBoard[x][y] == null) return true;
		else return false;
	}
	
	boolean isEnemyHere(Piece piece, int x, int y) {	
		if(piece.isWhite() ^ positionOnBoard[x][y].isWhite()) {
			return true;
		}
		else return false;
	}
	
	//printing the board with the pieces
	void printBoard(int turn) {
		//pw.print("\033[H\033[2J"); //clear terminal
		//pw.flush();

		for(int i = rows - 1; i >=0; i--) {
			pw.print((i + 1) + " "); // row's number
			for(int j = 0; j < columns; j++) {
				pw.print("[");
				if(positionOnBoard[j][i] == null) pw.print(" "); //if board's cell isnt empty, print piece's letter
				else pw.print(positionOnBoard[j][i]);
				pw.print("]");
			}
			pw.println();
		}
		pw.println("   a  b  c  d  e  f  g  h\n\nTurn: " + turn); // column's letters
	}
}
