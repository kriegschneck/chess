package chess;

import java.util.*;
import java.io.*;

class game {
  public static void main(String[] args) {
    int turn;
    //ListArray<T extends figure> whites;
    //ListArray<T extends figure> blacks;
    pawn a = new pawn(1,1);
    while(true) {
      printBoard();
      a.move();
      try {
        Thread.sleep(1000);
      } catch (Exception e) {
        System.out.println("interrupted");
      }
    }


  }


  static void printBoard() {
    PrintWriter board = new PrintWriter(System.out, true);
    board.print("\033[H\033[2J"); //clear terminal
    //board.flush();

    //printing the board
    for(int i = 8; i > 0; i--) {
      board.print(i + " ");
      for(int j = 8; j > 0; j--) {
        board.print("[");
        board.print(" ");
        board.print("]");
      }
      board.println();
    }
    board.println("   a  b  c  d  e  f  g  h");
  }

}
