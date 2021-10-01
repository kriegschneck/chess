package chess;

import java.util.*;

abstract class Piece {
  private boolean color;    //true = white, false = black
  private String letter;    //a letter to print
  private int x, y;         //a position of a figure

  Piece(boolean color, String letter) {
    this.color = color;                   //set the color of the piece
    if(color) this.letter = letter;       //set the letter. plack pieces go with upper case letters
    else this.letter = letter.toUpperCase();
  }

  void setPosition(int x, int y) { //setting a new position of a piece
    if(Game.board[x][y] != null) {
      if(isWhite()) Game.blacks.remove(Game.board[x][y]);
      else Game.whites.remove(Game.board[x][y]);
    }
    this.x = x;
    this.y = y;
    Game.board[x][y] = this;
  }

  void eraseCurrentPosition() {Game.board[x][y] = null;}  //erase the current position

  int getX() {return x;}
  int getY() {return y;}
  boolean isWhite() {return color;}

  public String toString() {return letter;}

  abstract boolean move();
}


class Pawn extends Piece {
  Pawn(int x, int y, boolean color) {
    super(color, "a");
    setPosition(x, y);
  }

  boolean move() {
    Random rn = new Random();
    ArrayList<int[]> eligiblePositions = new ArrayList<>();
    int x = getX();
    int y = getY();
    if (isWhite()) { //calculating eligible moves if the piece is white
      if(y == 7) return false;
      if(y == 1 && Game.board[x][y + 1] == null && Game.board[x][y + 2] == null) {  //advance
        eligiblePositions.add(new int[]{x, y + 2});
      }
      if(Game.board[x][y + 1] == null) {  //base move
        eligiblePositions.add(new int[]{x, y + 1});
      }
      if(x > 1 && Game.board[x - 1][y + 1] != null && !Game.board[x - 1][y + 1].isWhite()) {  //kill to the left
        eligiblePositions.add(new int[]{x - 1, y + 1});
      }
      if(x < 7 && Game.board[x + 1][y + 1] != null && !Game.board[x + 1][y + 1].isWhite()) {  //kill to the right
        eligiblePositions.add(new int[]{x + 1, y + 1});
      }
    }
      else {  //if the piece is black
        if(y == 0) return false;
        if(y == 6 && Game.board[x][y - 1] == null && Game.board[x][y - 2] == null) {  //advance
          eligiblePositions.add(new int[]{x, y - 2});
        }
        if(Game.board[x][y - 1] == null) {  //base move
          eligiblePositions.add(new int[]{x, y - 1});
        }
        if(x > 1 && Game.board[x - 1][y - 1] != null && Game.board[x - 1][y - 1].isWhite()) {  //kill to the left
          eligiblePositions.add(new int[]{x - 1, y - 1});
        }
        if(x < 7 && Game.board[x + 1][y - 1] != null && Game.board[x + 1][y - 1].isWhite()) {  //kill to the right
          eligiblePositions.add(new int[]{x + 1, y - 1});
        }
      }

      if(eligiblePositions.size() == 0) return false;
      int[] temp = eligiblePositions.get(rn.nextInt(eligiblePositions.size()));
      eraseCurrentPosition();
      setPosition(temp[0], temp[1]);
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

class Rook extends Piece {
  Rook(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "r";
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
