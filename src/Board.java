
import java.io.PrintWriter;
import java.util.ArrayList;

class Board {
	PrintWriter pw = new PrintWriter(System.out, true); //to print stuff
	private int rows, columns;
	//cells contain either null or a link to a piece if
	//the cells' indexes equal to the position of the piece
	Piece[][] cells;
	ArrayList<Piece> whites;  //list of white pieces
	ArrayList<Piece> blacks;  //list of black pieces

	Board(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;

		cells = new Piece[columns][rows];
		whites = new ArrayList<>();
		blacks = new ArrayList<>();
	}

	int getRows() {return rows;}
	int getColumns() {return columns;}

	//choosing a piece to make a move
	void pieceSelectionAndMove(ArrayList<Piece> setOfPieces) throws Exception {
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
