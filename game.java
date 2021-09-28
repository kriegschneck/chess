package chess;

import java.util.*;
import java.io.*;

class game {
  static PrintWriter pw = new PrintWriter(System.out, true); //for printing

  static ArrayList<piece> whites = new ArrayList<>(16); //list of white pieces
  static ArrayList<piece> blacks = new ArrayList<>(16); //list of black pieces
  //board with pieces. a cell contains either null or a link to a piece if
  //the cell's indexes equal to the position of the piece
  static piece[][] board = new piece[8][8];

  public static void main(String[] args) {
    for(int i = 0; i < 8; i++) {  //add 8 pawns to the list of pieces
      whites.add(new pawn(i,1));
    }


    int turn = 0; //for counting turns
    Random rn = new Random();
    //playing the game
    while(true) {
      printBoard(++turn);
      whites.get(rn.nextInt(7)).move(); //random white piece makes a move


      try {
        Thread.sleep(1000);
      } catch (Exception e) {
        pw.println("interrupted");
      }
    }

  }

  //printing the board with the pieces
  static void printBoard(int turn) {
    pw.print("\033[H\033[2J"); //clear terminal
    //board.flush();

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
