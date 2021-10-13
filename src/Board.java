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
	private ArrayList<Piece> pieces;		//list of pieces used as a buffer
	private ArrayList<Piece> enemyPieces;
	private Piece activePiece;				//link to a piece about to move
	
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
		whites.add(new King(this, Piece.Color.WHITE, 4, 0));
		
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
    	blacks.add(new King(this, Piece.Color.BLACK, 4, ROWS - 1));
	}

	
	void selectPiece() throws Exception {
		/*
		 * selecting a piece that has eligible positions and write a link to it into activePiece
		 */
		
		int randInt;
		ArrayList<Integer> excludedNumbers = new ArrayList<>();

		if (Game.turnNumber % 2 == 0) {
			pieces = blacks;
			enemyPieces = whites;
		} else {
			pieces = whites;
			enemyPieces = blacks;
		}
		
		for (int i = 0; i < enemyPieces.size(); i++) {
			enemyPieces.get(i).calculatePositions();
		}
		for (int i = 0; i < enemyPieces.size(); i++) {
			enemyPieces.get(i).thread.join();
		}
		for (int i = 0; i < pieces.size(); i++) {
			pieces.get(i).calculatePositions();
		}
		for (int i = 0; i < pieces.size(); i++) {
			pieces.get(i).thread.join();
		}
		
		for (int i = 0; i < pieces.size(); i++) {						//amount of tries to make a move equals to amount of pieces in the set
			do {
				randInt = (int) (Math.random() * (pieces.size()));
			} while(excludedNumbers.contains(randInt));
			
			if (!pieces.get(randInt).noEligiblePositions()) {			//if the chosen piece has eligible positions
				activePiece = pieces.get(randInt);						//it becomes the active piece
				return;	
			} else {                                        			//if the piece didn't have eligible positions 
				excludedNumbers.add(randInt);							//exclude the number of the piece from the next random selection
			}
		}
		throw new Exception("No piece can make a move.\n"); 			//if there wasn't any successful moves at all throw exception and end the game
	}
	
	void movePiece() {
		/*
		 * activePiece makes a move into one of its eligible positions
		 */
		
		Piece.Position newPosition = activePiece.getRandomEligiblePosition();
		int x = newPosition.getX();
		int y = newPosition.getY();
		
		System.out.print(activePiece + " " + activePiece.printPosition());
		
		//if the chosen piece is the king and its move is roque
		if (activePiece instanceof King && activePiece.isOnInitialPosition()) {
			roque(x, y);
		}
		
		//kill a piece of different color on a new position
		if (!isNullHere(x, y)) {
			if (activePiece.isWhite()) {
				blacks.remove(positionOnBoard[x][y]);
			} else {
				whites.remove(positionOnBoard[x][y]);
			}
		}
		
		positionOnBoard[activePiece.getX()][activePiece.getY()] = null;		//erase current position on board
		setPiecesPositionOnBoard(activePiece, newPosition);
		System.out.println("-" + activePiece.printPosition());
	}
	
	void setPiecesPositionOnBoard(Piece piece, Piece.Position position) {
		piece.setPosition(position);							//set a new position of a piece
		positionOnBoard[piece.getX()][piece.getY()] = piece;	//put the piece on the board at the same coordinates
	}
	
	void roque(int x, int y) {
		/*
		 * moving a rook if roque was the chosen move
		 */
		
		if (activePiece.isWhite()) {
			if (x == 2 && y == 0) {
				setPiecesPositionOnBoard(positionOnBoard[0][0], positionOnBoard[0][0].new Position(3, 0));
				positionOnBoard[0][0] = null;
			} else if (x == 6 && y == 0) {
				setPiecesPositionOnBoard(positionOnBoard[7][0], positionOnBoard[7][0].new Position(5, 0));
				positionOnBoard[7][0] = null;
			}
		} else {
			if (x == 2 && y == 7) {
				setPiecesPositionOnBoard(positionOnBoard[0][7], positionOnBoard[0][7].new Position(3, 7));
				positionOnBoard[0][7] = null;
			} else if (x == 6 && y == 7) {
				setPiecesPositionOnBoard(positionOnBoard[7][7], positionOnBoard[7][7].new Position(5, 7));
				positionOnBoard[7][7] = null;
			}
		}
	}
	
	boolean isNullHere(int x, int y) {
		return (positionOnBoard[x][y] == null);
	}
	
	boolean isEnemyHere(Piece.Color myColor, int x, int y) {	
		return (myColor.isWhite() ^ positionOnBoard[x][y].isWhite());
	}
	
	Piece getPieceByPosition(int x, int y) {
		return positionOnBoard[x][y];
	}
	
	boolean isPositionUnderAttack(Piece.Color myColor, Piece.Position position) {
		/*
		 * method receives piece's color and one of its possible positions
		 * check if this position is under attack by pieces of different color
		 */
		
		Iterator<Piece> iterator;
		
		if (myColor.isWhite()) {
			iterator = blacks.iterator();
		} else {
			iterator = whites.iterator();
		}
		
		while (iterator.hasNext()) {
			if (iterator.next().findPositionAmongAttacked(position)) {
				return true;
			}	
		}
		return false;
	}
	
	void printBoard() {
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
		System.out.println("   a  b  c  d  e  f  g  h\n\nTurn: " + Game.turnNumber);
	}
	
}
