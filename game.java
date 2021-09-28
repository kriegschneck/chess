package chess;

import java.util.*;
import java.io.*;

class game {
  static int turn;
  static PrintWriter pw = new PrintWriter(System.out, true);
  static ArrayList<piece> whites = new ArrayList<>(16);
  //static ArrayList<piece> blacks = new ArrayList<>(16);
  static piece[][] board = new piece[8][8]; //board with pieces

  public static void main(String[] args) {
    for(int i = 0; i < 8; i++) {    //add 8 pawns
      board[i][1] = new pawn(i,1);  //place pawns onto the board
      whites.add(board[i][1]);      //add them to the list of pieces
    }

    Random rn = new Random();
    //playing the game
    while(true) {
      turn++;
      printBoard();
      //whites[rn.nextInt(15)].move();


      try {
        Thread.sleep(1000);
      } catch (Exception e) {
        pw.println("interrupted");
      }
    }

  }

  //printing the board with the pieces
  static void printBoard() {
    pw.print("\033[H\033[2J"); //clear terminal
    //board.flush();

    for(int i = 7; i >=0; i--) {
      pw.print((i + 1) + " "); // row's number
      for(int j = 0; j < 8; j++) {
        pw.print("[");
        if(board[j][i] == null) pw.print(" ");
        else pw.print(board[j][i]);
        pw.print("]");
      }
      pw.println();
    }
    pw.println("   a  b  c  d  e  f  g  h\n\nTurn: " + turn);
  }

}
