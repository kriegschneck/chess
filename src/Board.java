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
    
	private Piece[][] squares;		//cells contain either null or a link to a piece
	private ArrayList<Piece> whites;  		//list of white pieces
	private ArrayList<Piece> blacks;  		//list of black pieces
	private ArrayList<Piece> pieces;		//list of pieces used as a buffer
	private ArrayList<Piece> enemyPieces;
	private Piece activePiece;				//link to a piece about to move
	
	Board() {
		squares = new Piece[COLUMNS][ROWS];
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
			if (getPieceWithEligiblePositions(excludedNumbers)) {
				return;
			}
		}
		throw new Exception("No piece can make a move.\n"); 			//if there wasn't any successful moves at all throw exception and end the game
	}


	boolean getPieceWithEligiblePositions(ArrayList<Integer> excludedNumbers) {
		int randomInt;
		
		do {
			randomInt = (int) (Math.random() * pieces.size());
		} while(excludedNumbers.contains(randomInt));
		
		if (!pieces.get(randomInt).hasNoEligiblePositions()) {		//if the chosen piece has eligible positions
			activePiece = pieces.get(randomInt);					//it becomes the active piece
			return true;	
		} else {                                        			//if the piece didn't have eligible positions 
			excludedNumbers.add(randomInt);	
			return false;											//exclude the number of the piece from the next random selection
		}
	}
	
	void movePiece() {
		/*
		 * activePiece makes a move onto one of its eligible positions
		 */
		
		Piece.Position newPosition = activePiece.getRandomEligiblePosition();
		int x = newPosition.getX();
		int y = newPosition.getY();

		if (activePiece instanceof King && activePiece.isOnInitialPosition()) {
			roque(x, y);
		}
		
		killPieceOnNewPosition(x, y);
		
		System.out.print(activePiece + " " + activePiece.printPosition());
		
		squares[activePiece.getX()][activePiece.getY()] = null;		//erase current position on board
		setPiecesPositionOnBoard(activePiece, newPosition);
		
		System.out.println("-" + activePiece.printPosition());
	}

	void roque(int x, int y) {
		/*
		 * moving a rook if roque is the chosen move
		 */
		
		if (activePiece.isWhite()) {
			if (x == 2 && y == 0) {
				setPiecesPositionOnBoard(squares[0][0], squares[0][0].new Position(3, 0));
				squares[0][0] = null;
			} else if (x == 6 && y == 0) {
				setPiecesPositionOnBoard(squares[7][0], squares[7][0].new Position(5, 0));
				squares[7][0] = null;
			}
		} else {
			if (x == 2 && y == 7) {
				setPiecesPositionOnBoard(squares[0][7], squares[0][7].new Position(3, 7));
				squares[0][7] = null;
			} else if (x == 6 && y == 7) {
				setPiecesPositionOnBoard(squares[7][7], squares[7][7].new Position(5, 7));
				squares[7][7] = null;
			}
		}
	}
	
	void killPieceOnNewPosition(int x, int y) {
		if (!isNullHere(x, y)) {
			if (activePiece.isWhite()) {
				blacks.remove(squares[x][y]);
			} else {
				whites.remove(squares[x][y]);
			}
		}
	}
	
	void setPiecesPositionOnBoard(Piece piece, Piece.Position position) {
		piece.setPosition(position);							//set a new position of a piece
		squares[piece.getX()][piece.getY()] = piece;	//put the piece on the board at the same coordinates
	}
	
	boolean isNullHere(int x, int y) {
		return (squares[x][y] == null);
	}
	
	
	Piece getPieceByPosition(int x, int y) {
		return squares[x][y];
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
				if (squares[j][i] == null) {
					System.out.print(" ");
				} else {
					System.out.print(squares[j][i]);
				}
				System.out.print("]");
			}
			System.out.println();
		}
		System.out.println("   a  b  c  d  e  f  g  h\n\nTurn: " + Game.turnNumber);
	}
	
}
