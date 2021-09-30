package chess;

import java.util.*;
import java.io.*;

class Game {
  static PrintWriter pw = new PrintWriter(System.out, true); //to print stuff

  static ArrayList<Piece> whites = new ArrayList<>(8); //list of white pieces
  static ArrayList<Piece> blacks = new ArrayList<>(8); //list of black pieces
  //board with pieces. a cell contains either null or a link to a piece if
  //the cell's indexes equal to the position of the piece
  static Piece[][] board = new Piece[8][8];

  /***START HERE***/
  public static void main(String[] args) {
    for(int i = 0; i < 8; i++) {  //add 8 pawns to the list of pieces
      whites.add(new Pawn(i, 1, true));
    }

    for(int i = 0; i < 8; i++) {  //add 8 pawns to the list of pieces
      blacks.add(new Pawn(i, 6, false));
    }

    int turn = 0; //counting turns
    while(true) { //playing the game
      printBoard(++turn);
      if(turn % 2 != 0) SelectionAndMove(whites);
      else SelectionAndMove(blacks);

      try {
        Thread.sleep(500);
      } catch (Exception e) {
        pw.println("Interrupted");
      }
    }

  }


  static void SelectionAndMove(ArrayList<Piece> setOfPieces) { //choosing a piece to make a move
    int i = 0;      //number of tries to make a move
    int randInt = 0;
    Random rn = new Random();
    while(true) {   //random white piece makes a move
      try {
        randInt = rn.nextInt(setOfPieces.size() - i); //get a random number
      } catch (Exception e) {
        pw.println("\nIs it a draw?");
        return;
      }
      if(setOfPieces.get(randInt).move()) return;   //random piece makes a move. if a move was successful go to next turn
      else {                                        //if move didn't succeed
        setOfPieces.add(setOfPieces.get(randInt));
        setOfPieces.remove(randInt);                //set the piece that couldn't move at the last position
        i++;                                        //exclude this piece from the next random selection
      }
    }
  }


  //printing the board with the pieces
  static void printBoard(int turn) {
    pw.print("\033[H\033[2J"); //clear terminal
    pw.flush();

    for(int i = 7; i >=0; i--) {
      pw.print((i + 1) + " "); // row's number
      for(int j = 0; j < 8; j++) {
        pw.print("[");
        if(board[j][i] == null) pw.print(" "); //if board's cell isnt empty, print piece's letter
        else pw.print(board[j][i]);
        pw.print("]");
      }
      pw.println();
    }
    pw.println("   a  b  c  d  e  f  g  h\n\nTurn: " + turn); // column's letters
  }

}
