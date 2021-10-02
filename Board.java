package chess;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

class Board {
  PrintWriter pw = new PrintWriter(System.out, true); //to print stuff
  private int rows, columns;
  //cells contain either null or a link to a piece if
  //the cells' indexes equal to the position of the piece
  Piece[][] cells;
  ArrayList<Piece> whites;  //list of white pieces
  ArrayList<Piece> blacks;  //list of black pieces

  Board(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;

    cells = new Piece[rows][columns];
    whites = new ArrayList<>(columns);
    blacks = new ArrayList<>(columns);
  }

  int getRows() {return rows;}
  int getColumns() {return columns;}

  //choosing a piece to make a move
  void pieceSelectionAndMove(ArrayList<Piece> setOfPieces) throws ChessException {
    int i = 0;      //number of tries to make a move
    int randInt;
    Random rn = new Random();
    while(true) {   //random white piece makes a move
      try {
        randInt = rn.nextInt(setOfPieces.size() - i); //get a random number
        pw.println(randInt);
      } catch (Exception e) {
        throw new ChessException("No pieces can make a move");
      }
      if(setOfPieces.get(randInt).move()) return;   //random piece makes a move. if a move was successful go to next turn
      else {                                        //if move didn't succeed
        setOfPieces.add(setOfPieces.get(randInt));  //set the piece that couldn't move at the last position
        setOfPieces.remove(randInt);                //exclude this piece from the next random selection
        i++;
      }
    }
  }

  //printing the board with the pieces
  void printBoard(int turn) {
    pw.print("\033[H\033[2J"); //clear terminal
    pw.flush();

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
