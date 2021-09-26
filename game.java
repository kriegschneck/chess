package chess;

import java.util.*;
import java.io.*;

class game {
  //static ArrayList<piece> whites = new ArrayList<>(16);
  //static ArrayList<piece> whites = new ArrayList<>(16);
  static piece[][] board = new piece[8][8]; //actual board

  public static void main(String[] args) {
    int turn;

    for(int i = 0; i < 8; i++) { //add 8 pawns
      //whites.add(new pawn(i,1));
      board[i][1] = new pawn(i,1);
    }

    //playing the game
    while(true) {
      printBoard();
      try {
        Thread.sleep(1000);
      } catch (Exception e) {
        System.out.println("interrupted");
      }
    }


  }

  //printing the board with the pieces
  static void printBoard() {
    PrintWriter pw = new PrintWriter(System.out, true);
    pw.print("\033[H\033[2J"); //clear terminal
    //board.flush();

    for(int i = 7; i >= 0; i--) {
      pw.print((i+1) + " ");
      for(int j = 0; j < 8; j++) {
        pw.print("[");
        if(board[j][i] == null) pw.print(" ");
        else pw.print(board[j][i]);
        pw.print("]");
      }
      pw.println();
    }
    pw.println("   a  b  c  d  e  f  g  h\n\n");
  }

}
