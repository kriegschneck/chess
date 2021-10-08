
import java.io.PrintWriter;
import java.util.ArrayList;

class Board {
	PrintWriter pw = new PrintWriter(System.out, true); //to print stuff
	private int rows, columns;
	//cells contain either null or a link to a piece if
	//the cells' indexes equal to the position of the piece
	private Piece[][] cells;
	private ArrayList<Piece> whites;  //list of white pieces
	private ArrayList<Piece> blacks;  //list of black pieces
	private ArrayList<Piece> setOfPieces;	//buffer list

	Board(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;

		cells = new Piece[columns][rows];
		
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
	void pieceSelectionAndMove(int turn) throws Exception {
		if(turn % 2 == 0) setOfPieces = blacks;
		else setOfPieces = whites;
		
		int randInt;
		//random piece makes a move
		for(int i = 0; i < setOfPieces.size(); i++) {   //each piece has one try to make an eligible move
			randInt = (int) (Math.random() * (setOfPieces.size() - i)); //get a random number 
			pw.print(setOfPieces.get(randInt) + " " + setOfPieces.get(randInt).piecesPosition);
			if(setOfPieces.get(randInt).move()) {
				return;	//random piece makes a move. if a move was successful go to next turn
			}
			else {                                        	//if move didn't succeed
				setOfPieces.add(setOfPieces.get(randInt));  //set the piece that couldn't move at the last position
				setOfPieces.remove(randInt);                //exclude this piece from the next random selection
				pw.println(" - couldn't make a move");
			}
		}
		throw new Exception("No piece can make a move.\n"); //is there wasn't any successful moves
	}
	
	void setPositionOnBoard(Piece piece, Piece.Position position) { //setting a new position of a piece
		int x = position.getX();
		int y = position.getY();
		
		if(cells[x][y] != null) {
			if(piece.isWhite()) blacks.remove(cells[x][y]); //kill a piece of different color on a new position
			else whites.remove(cells[x][y]);
		}
		cells[piece.piecesPosition.getX()][piece.piecesPosition.getX()] = null;  //erase the current position of a piece
		piece.piecesPosition.setXY(x, y);	//set a new coordinates
		cells[x][y] = piece; 	//move a piece to a new position
		pw.println(" - " + piece.piecesPosition);
	}
	
	boolean isNullHere(int x, int y) {
		if(cells[x][y] == null) return true;
		else return false;
	}
	
	boolean isEnemyHere(Piece piece, int x, int y) {	
		if(piece.isWhite() ^ cells[x][y].isWhite()) {
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
				if(cells[j][i] == null) pw.print(" "); //if board's cell isnt empty, print piece's letter
				else pw.print(cells[j][i]);
				pw.print("]");
			}
			pw.println();
		}
		pw.println("   a  b  c  d  e  f  g  h\n\nTurn: " + turn); // column's letters
	}
}