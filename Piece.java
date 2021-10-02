package chess;

import java.util.Random;
import java.util.ArrayList;

abstract class Piece {
  private boolean color;    //true = white, false = black
  private String letter;    //a letter to print
  private int x, y;         //a position of a figure
  Board cBoard;      //a link to the current board to play on

  //technical variables
  Random rn;
  ArrayList<int[]> eligiblePositions;

  Piece(Board board, int y, String letter) {
    cBoard = board;
    if(y <= 2) {
      color = true; //set the color of the piece. true = white
      this.letter = letter; //set the letter. plack pieces go with upper case letters
    }
    else {
      color = false;
      this.letter = letter.toUpperCase();
    }
  }

  void setPosition(int x, int y) throws NullPointerException { //setting a new position of a piece
    if(cBoard.cells[x][y] != null) {
      if(isWhite()) cBoard.blacks.remove(cBoard.cells[x][y]); //kill a piece of different color
      else cBoard.whites.remove(cBoard.cells[x][y]);
    }

    this.x = x;
    this.y = y;
    cBoard.cells[x][y] = this;
  }

  void eraseCurrentPosition() {cBoard.cells[x][y] = null;}  //erase the current position

  int getX() {return x;}
  int getY() {return y;}
  boolean isWhite() {return color;}

  public String toString() {return letter;}

  abstract boolean move() throws NullPointerException;
}


class Pawn extends Piece {
  Pawn(Board board, int x, int y) throws NullPointerException{
    super(board, y, "a");
    setPosition(x, y);
  }

  boolean move() throws NullPointerException {
    rn = new Random();
    eligiblePositions = new ArrayList<>();
    int x = getX();
    int y = getY();

    cBoard.pw.print("\t" + this + " [" + x + ", " + y + "]");

    if (isWhite()) { //calculating eligible moves if the piece is white
      if(y == cBoard.getRows() - 1) return false;
      if(y == 1 && cBoard.cells[x][y + 1] == null && cBoard.cells[x][y + 2] == null) {  //advance
        eligiblePositions.add(new int[]{x, y + 2});
      }
      if(cBoard.cells[x][y + 1] == null) {  //base move
        eligiblePositions.add(new int[]{x, y + 1});
      }
      if(x > 1 && cBoard.cells[x - 1][y + 1] != null && !cBoard.cells[x - 1][y + 1].isWhite()) {  //kill to the left
        eligiblePositions.add(new int[]{x - 1, y + 1});
      }
      if(x < cBoard.getColumns() - 1 && cBoard.cells[x + 1][y + 1] != null && !cBoard.cells[x + 1][y + 1].isWhite()) {  //kill to the right
        eligiblePositions.add(new int[]{x + 1, y + 1});
      }
    }
      else {  //if the piece is black
        if(y == 0) return false;
        if(y == cBoard.getRows() - 2 && cBoard.cells[x][y - 1] == null && cBoard.cells[x][y - 2] == null) {  //advance
          eligiblePositions.add(new int[]{x, y - 2});
        }
        if(cBoard.cells[x][y - 1] == null) {  //base move
          eligiblePositions.add(new int[]{x, y - 1});
        }
        if(x > 1 && cBoard.cells[x - 1][y - 1] != null && cBoard.cells[x - 1][y - 1].isWhite()) {  //kill to the left
          eligiblePositions.add(new int[]{x - 1, y - 1});
        }
        if(x < cBoard.getColumns() - 1 && cBoard.cells[x + 1][y - 1] != null && cBoard.cells[x + 1][y - 1].isWhite()) {  //kill to the right
          eligiblePositions.add(new int[]{x + 1, y - 1});
        }
      }

      if(eligiblePositions.size() == 0) return false;
      int[] temp = eligiblePositions.get(rn.nextInt(eligiblePositions.size()));
      eraseCurrentPosition();
      setPosition(temp[0], temp[1]);
      cBoard.pw.println(" - [" + getX() + ", " + getY() + "]");
      return true;

  }
}

class Rook extends Piece {
  Rook(Board board, int x, int y) throws NullPointerException {
    super(board, y, "r");
    setPosition(x, y);
  }

  boolean move() throws NullPointerException {
    int x = getX();
    int y = getY();
    cBoard.pw.print("\t" + this + " [" + x + ", " + y + "]");
    /*for(int i = x; i < cBoard.getColumns(); i++) {
      if(cBoard.cells[i][y] == null || )
    }*/

    cBoard.pw.println(" - [" + getX() + ", " + getY() + "]");
    return true;
  }
}

/*class King extends Piece {
  King(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "k";
  }

}

class Queen extends Piece {
  Queen(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "q";
  }
}

class Bishop extends Piece {
  Bishop(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "b";
  }
}

class Knight extends Piece {
  Knight(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "k";
  }
}*/
