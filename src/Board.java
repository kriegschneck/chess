/*
 * Board
 * 
 * v0.9
 * 
 * 12.10.21
 * 
 * Sergei N
 */

import java.util.ArrayList;
import java.util.Iterator;

class Board {
	/*
	 * Class board contains two lists of pieces and a two-dimensional array which contains links to pieces or null
	 * if no pieces have position equals to indexes of the array
	 * 
	 * the class also contain methods to control pieces' positions
	 */
	
	static final int ROWS = 8;     			//must be 8
    static final int COLUMNS = 8;  			//must be 8
    
	private Piece[][] positionOnBoard;		//cells contain either null or a link to a piece
	private ArrayList<Piece> whites;  		//list of white pieces
	private ArrayList<Piece> blacks;  		//list of black pieces
	private ArrayList<Piece> setOfPieces;	//list of pieces used as a buffer
	private King whiteKing;
	private King blackKing;
	
	Board() {
		positionOnBoard = new Piece[COLUMNS][ROWS];
		whites = new ArrayList<>();
		blacks  = new ArrayList<>();
		
		for (int i = 0; i < 8; i++) {
			whites.add(new Pawn(this, Piece.Color.WHITE, i, 1));
		}
		whites.add(new Rook(this, Piece.Color.WHITE, 0, 0));
		whites.add(new Rook(this, Piece.Color.WHITE, 7, 0));
		whites.add(new Knight(this, Piece.Color.WHITE, 1, 0));
		whites.add(new Knight(this, Piece.Color.WHITE, 6, 0));
		whites.add(new Bishop(this, Piece.Color.WHITE, 2, 0));
		whites.add(new Bishop(this, Piece.Color.WHITE, 5, 0));
		whites.add(new Queen(this, Piece.Color.WHITE, 3, 0));
		whiteKing = new King(this, Piece.Color.WHITE, 4, 0);
		whites.add(whiteKing);
		
		for (int i = 0; i < 8; i++) {
			blacks.add(new Pawn(this, Piece.Color.BLACK, i, ROWS - 2));
		}
    	blacks.add(new Rook(this, Piece.Color.BLACK, 0, ROWS - 1));
    	blacks.add(new Rook(this, Piece.Color.BLACK, 7, ROWS - 1));
    	blacks.add(new Knight(this, Piece.Color.BLACK, 1, ROWS - 1));
    	blacks.add(new Knight(this, Piece.Color.BLACK, 6, ROWS - 1));
    	blacks.add(new Bishop(this, Piece.Color.BLACK, 2, ROWS - 1));
    	blacks.add(new Bishop(this, Piece.Color.BLACK, 5, ROWS - 1));
    	blacks.add(new Queen(this, Piece.Color.BLACK, 3, ROWS - 1));
    	blackKing = new King(this, Piece.Color.BLACK, 4, ROWS - 1);
    	blacks.add(blackKing);
	}

	//choosing a piece to make a move
	void selectPieceAndMove(int turnNumber) throws Exception {
		int randInt;
		ArrayList<Integer> excludedNumbers = new ArrayList<>();
		
		if (turnNumber % 2 == 0) {
			setOfPieces = blacks;
		} else {
			setOfPieces = whites;
		}
	
		for (int i = 0; i < setOfPieces.size(); i++) {
			setOfPieces.get(i).calculateEligiblePosition();
		}
		for (int i = 0; i < setOfPieces.size(); i++) {
			setOfPieces.get(i).thread.join();
		}
		
		//choosing a piece to make a move
		for (int i = 0; i < setOfPieces.size(); i++) {					//amount of tries to make a move equals to amount of pieces in the set
			do {
				randInt = (int) (Math.random() * (setOfPieces.size())); //get a random number excluding ones that have no eligible moves
			} while(excludedNumbers.contains(randInt));
			
			System.out.print(setOfPieces.get(randInt) + " " + setOfPieces.get(randInt).printPosition());
			
			if (!setOfPieces.get(randInt).noEligiblePositions()) {		//if the chosen piece has eligible moves
				moveToRandomEligiblePosition(setOfPieces.get(randInt));	//the piece makes a move
				System.out.println(" - " + setOfPieces.get(randInt).printPosition());	
				
				/*if (setOfPieces.get(randInt) instanceof King) {
					printBoard(turnNumber);
				}*/
				
				return;	
			} else {                                        			//if the piece didn't have eligible moves 
				excludedNumbers.add(randInt);							//exclude the number of the piece from the next random selection
				System.out.println(" - couldn't make a move");
			}
		}
		throw new Exception("No piece can make a move.\n"); 			//if there wasn't any successful moves at all throw exception and end the game
	}
	
	void moveToRandomEligiblePosition(Piece piece) { //setting a new position of a piece
		Piece.Position bufferPosition = piece.getRandomEligiblePosition();
		int x = bufferPosition.getX();
		int y = bufferPosition.getY();
		
		if (piece instanceof King && piece.onInitialPosition) {
			if (x == 2 && y == 0) {
				setPiecesPositionOnBoard(positionOnBoard[0][0], positionOnBoard[0][0].new Position(3, 0));
				positionOnBoard[0][0] = null;
			} else if (x == 6 && y == 0) {
				setPiecesPositionOnBoard(positionOnBoard[7][0], positionOnBoard[7][0].new Position(5, 0));
				positionOnBoard[7][0] = null;
			} else if (x == 2 && y == 7) {
				setPiecesPositionOnBoard(positionOnBoard[0][7], positionOnBoard[0][7].new Position(3, 7));
				positionOnBoard[0][7] = null;
			} else if (x == 6 && y == 0) {
				setPiecesPositionOnBoard(positionOnBoard[7][7], positionOnBoard[7][7].new Position(5, 7));
				positionOnBoard[7][7] = null;
			}
		}

		if(!isNullHere(x, y)) {
			if(piece.isWhite()) {
				blacks.remove(positionOnBoard[x][y]);			//kill a piece of different color on a new position
			} else {
				whites.remove(positionOnBoard[x][y]);
			}
		}
		positionOnBoard[piece.getX()][piece.getY()] = null;		//erase current position on board
		setPiecesPositionOnBoard(piece, bufferPosition);
	}
	
	void setPiecesPositionOnBoard(Piece piece, Piece.Position position) {
		piece.setPosition(position);							//set a new position of a piece
		positionOnBoard[piece.getX()][piece.getY()] = piece;	//put the piece on the board at the same coordinates
	}
	
	boolean isNullHere(int x, int y) {
		return (positionOnBoard[x][y] == null);
	}
	
	boolean isEnemyHere(Piece piece, int x, int y) {	
		return (piece.isWhite() ^ positionOnBoard[x][y].isWhite());
	}
	
	Piece getPieceByPosition(int x, int y) {
		return positionOnBoard[x][y];
	}
	
	boolean isPositionUnderAttackByAnotherColor(Piece.Color myColor, Piece.Position position) {
		Iterator<Piece> iterator;
		
		if (myColor.isWhite()) {
			iterator = blacks.iterator();
		} else {
			iterator = whites.iterator();
		}
		
		while (iterator.hasNext()) {
			if (iterator.next().findPositionAmongEligible(position)) {
				return true;
			}
		}
		return false;
	}
	
	void printBoard(int turn) {
		for (int i = ROWS - 1; i >=0; i--) {
			System.out.print((i + 1) + " ");
			for (int j = 0; j < COLUMNS; j++) {
				System.out.print("[");
				if (positionOnBoard[j][i] == null) {
					System.out.print(" ");
				} else {
					System.out.print(positionOnBoard[j][i]);
				}
				System.out.print("]");
			}
			System.out.println();
		}
		System.out.println("   a  b  c  d  e  f  g  h\n\nTurn: " + turn);
	}
	
}
