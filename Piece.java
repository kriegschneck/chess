package chess;

import java.util.Random;
import java.util.ArrayList;

abstract class Piece {
  private boolean color;    //true = white, false = black
  private String letter;    //a letter to print
  private int x, y;         //a position of a figure
  Board cBoard;             //a link to the current board to play on

  //technical variables
  Random rn;
  ArrayList<int[]> eligiblePositions;

  Piece(Board board, int x, int y, String letter) {
    cBoard = board;
    this.x = x;
    this.y = y;
    cBoard.cells[x][y] = this;  //set position on the board
    if(y <= 2) {
      color = true; //set the color of the piece. true = white
      this.letter = letter; //set the letter. black pieces go with upper case letters
    }
    else {
      color = false;
      this.letter = letter.toUpperCase();
    }
  }

  void setPosition(int[] newPosition) throws NullPointerException { //setting a new position of a piece
    if(cBoard.cells[x][y] != null) {
      if(isWhite()) cBoard.blacks.remove(cBoard.cells[newPosition[0]][newPosition[1]]); //kill a piece of different color on a new position
      else cBoard.whites.remove(cBoard.cells[newPosition[0]][newPosition[1]]);
    }
    cBoard.cells[x][y] = null;  //erase the current position of a piece
    x = newPosition[0];         //set a new coordinates
    y = newPosition[1];
    cBoard.cells[x][y] = this;  //move a piece to a new position
    cBoard.pw.println(" - [" + x + ", " + y + "]");
  }

  int getX() {return x;}
  int getY() {return y;}
  boolean isWhite() {return color;}

  public String toString() {return letter;}

  abstract boolean move() throws NullPointerException;
}


class Pawn extends Piece {
  Pawn(Board board, int x, int y) {
    super(board, x, y, "a");
  }

  boolean move() throws NullPointerException {
    rn = new Random();
    eligiblePositions = new ArrayList<>();
    int x = getX();
    int y = getY();

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
      setPosition(eligiblePositions.get(rn.nextInt(eligiblePositions.size())));
      return true;

  }
}

class Rook extends Piece {
  Rook(Board board, int x, int y) {
    super(board, x, y, "r");
  }

  boolean move() throws NullPointerException {
    rn = new Random();
    eligiblePositions = new ArrayList<>();
    int x = getX();
    int y = getY();

    for(int i = x + 1; i < cBoard.getColumns(); i++) {
      if(cBoard.cells[i][y] == null) {
        eligiblePositions.add(new int[]{i, y});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][y].isWhite()) {
        eligiblePositions.add(new int[]{i, y});
        break;
      }
      else break;
    }
    for(int i = x - 1; i >= 0; i--) {
      if(cBoard.cells[i][y] == null) {
        eligiblePositions.add(new int[]{i, y});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][y].isWhite()) {
        eligiblePositions.add(new int[]{i, y});
        break;
      }
      else break;
    }
    for(int i = y + 1; i < cBoard.getRows(); i++) {
      if(cBoard.cells[x][i] == null) {
        eligiblePositions.add(new int[]{x, i});
        continue;
      }
      if(isWhite() ^ cBoard.cells[x][i].isWhite()) {
        eligiblePositions.add(new int[]{x, i});
        break;
      }
      else break;
    }
    for(int i = y - 1; i >= 0; i--) {
      if(cBoard.cells[x][i] == null) {
        eligiblePositions.add(new int[]{x, i});
        continue;
      }
      if(isWhite() ^ cBoard.cells[x][i].isWhite()) {
        eligiblePositions.add(new int[]{x, i});
        break;
      }
      else break;
    }

    if(eligiblePositions.size() == 0) return false;
    setPosition(eligiblePositions.get(rn.nextInt(eligiblePositions.size())));
    return true;
  }
}

class Knight extends Piece {
  Knight(Board board, int x, int y) {
    super(board, x, y, "h");
  }

  boolean move() throws NullPointerException {
    rn = new Random();
    eligiblePositions = new ArrayList<>();
    int x = getX();
    int y = getY();

    int newX = x + 1;
    int newY = y - 2;
    if(newX < cBoard.getColumns() - 1 && newY > 1) {
      if(cBoard.cells[newX][newY] == null || (isWhite() ^ cBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new int[]{newX, newY});
      }
    }
    newX = x + 1;
    newY = y + 2;
    if(newX < cBoard.getColumns() - 1 && newY < cBoard.getRows() - 2) {
      if(cBoard.cells[newX][newY] == null || (isWhite() ^ cBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new int[]{newX, newY});
      }
    }
    newX = x - 1;
    newY = y - 2;
    if(newX > 0 && newY > 1) {
      if(cBoard.cells[newX][newY] == null || (isWhite() ^ cBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new int[]{newX, newY});
      }
    }
    newX = x - 1;
    newY = y + 2;
    if(newX > 0 && newY < cBoard.getRows() - 2) {
      if(cBoard.cells[newX][newY] == null || (isWhite() ^ cBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new int[]{newX, newY});
      }
    }
    newX = x + 2;
    newY = y - 1;
    if(newX < cBoard.getColumns() - 2 && newY > 0) {
      if(cBoard.cells[newX][newY] == null || (isWhite() ^ cBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new int[]{newX, newY});
      }
    }
    newX = x + 2;
    newY = y + 1;
    if(newX < cBoard.getColumns() - 2 && newY < cBoard.getRows() - 1) {
      if(cBoard.cells[newX][newY] == null || (isWhite() ^ cBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new int[]{newX, newY});
      }
    }
    newX = x - 2;
    newY = y - 1;
    if(newX > 1 && newY > 0) {
      if(cBoard.cells[newX][newY] == null || (isWhite() ^ cBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new int[]{newX, newY});
      }
    }
    newX = x - 2;
    newY = y + 1;
    if(newX > 1 && newY < cBoard.getRows() - 1) {
      if(cBoard.cells[newX][newY] == null || (isWhite() ^ cBoard.cells[newX][newY].isWhite())) {  //if the new cell is empty or contains a piece of different color
      eligiblePositions.add(new int[]{newX, newY});
      }
    }

    if(eligiblePositions.size() == 0) return false;
    setPosition(eligiblePositions.get(rn.nextInt(eligiblePositions.size())));
    return true;
  }
}

class Bishop extends Piece {
  Bishop(Board board, int x, int y) {
    super(board, x, y, "b");
  }

  boolean move() throws NullPointerException {
    rn = new Random();
    eligiblePositions = new ArrayList<>();
    int x = getX();
    int y = getY();

    for(int i = x + 1, j = y + 1; i < cBoard.getRows() && j < cBoard.getColumns(); i++, j++) {
      if(cBoard.cells[i][j] == null) {
        eligiblePositions.add(new int[]{i, j});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][j].isWhite()) {
        eligiblePositions.add(new int[]{i, j});
        break;
      }
      else break;
    }
    for(int i = x + 1, j = y - 1; i < cBoard.getRows() && j >= 0; i++, j--) {
      if(cBoard.cells[i][j] == null) {
        eligiblePositions.add(new int[]{i, j});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][j].isWhite()) {
        eligiblePositions.add(new int[]{i, j});
        break;
      }
      else break;
    }
    for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
      if(cBoard.cells[i][j] == null) {
        eligiblePositions.add(new int[]{i, j});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][j].isWhite()) {
        eligiblePositions.add(new int[]{i, j});
        break;
      }
      else break;
    }
    for(int i = x - 1, j = y + 1; i >= 0 && j < cBoard.getColumns(); i--, j++) {
      if(cBoard.cells[i][j] == null) {
        eligiblePositions.add(new int[]{i, j});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][j].isWhite()) {
        eligiblePositions.add(new int[]{i, j});
        break;
      }
      else break;
    }

    if(eligiblePositions.size() == 0) return false;
    setPosition(eligiblePositions.get(rn.nextInt(eligiblePositions.size())));
    return true;
  }
}

class Queen extends Piece {
  Queen(Board board, int x, int y) {
    super(board, x, y, "q");
  }

  boolean move() throws NullPointerException {
    rn = new Random();
    eligiblePositions = new ArrayList<>();
    int x = getX();
    int y = getY();

    for(int i = x + 1; i < cBoard.getColumns(); i++) {
      if(cBoard.cells[i][y] == null) {
        eligiblePositions.add(new int[]{i, y});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][y].isWhite()) {
        eligiblePositions.add(new int[]{i, y});
        break;
      }
      else break;
    }
    for(int i = x - 1; i >= 0; i--) {
      if(cBoard.cells[i][y] == null) {
        eligiblePositions.add(new int[]{i, y});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][y].isWhite()) {
        eligiblePositions.add(new int[]{i, y});
        break;
      }
      else break;
    }
    for(int i = y + 1; i < cBoard.getRows(); i++) {
      if(cBoard.cells[x][i] == null) {
        eligiblePositions.add(new int[]{x, i});
        continue;
      }
      if(isWhite() ^ cBoard.cells[x][i].isWhite()) {
        eligiblePositions.add(new int[]{x, i});
        break;
      }
      else break;
    }
    for(int i = y - 1; i >= 0; i--) {
      if(cBoard.cells[x][i] == null) {
        eligiblePositions.add(new int[]{x, i});
        continue;
      }
      if(isWhite() ^ cBoard.cells[x][i].isWhite()) {
        eligiblePositions.add(new int[]{x, i});
        break;
      }
      else break;
    }
    for(int i = x + 1, j = y + 1; i < cBoard.getRows() && j < cBoard.getColumns(); i++, j++) {
      if(cBoard.cells[i][j] == null) {
        eligiblePositions.add(new int[]{i, j});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][j].isWhite()) {
        eligiblePositions.add(new int[]{i, j});
        break;
      }
      else break;
    }
    for(int i = x + 1, j = y - 1; i < cBoard.getRows() && j >= 0; i++, j--) {
      if(cBoard.cells[i][j] == null) {
        eligiblePositions.add(new int[]{i, j});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][j].isWhite()) {
        eligiblePositions.add(new int[]{i, j});
        break;
      }
      else break;
    }
    for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
      if(cBoard.cells[i][j] == null) {
        eligiblePositions.add(new int[]{i, j});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][j].isWhite()) {
        eligiblePositions.add(new int[]{i, j});
        break;
      }
      else break;
    }
    for(int i = x - 1, j = y + 1; i >= 0 && j < cBoard.getColumns(); i--, j++) {
      if(cBoard.cells[i][j] == null) {
        eligiblePositions.add(new int[]{i, j});
        continue;
      }
      if(isWhite() ^ cBoard.cells[i][j].isWhite()) {
        eligiblePositions.add(new int[]{i, j});
        break;
      }
      else break;
    }

    if(eligiblePositions.size() == 0) return false;
    setPosition(eligiblePositions.get(rn.nextInt(eligiblePositions.size())));
    return true;
  }
}

class King extends Piece {
  King(Board board, int x, int y) {
    super(board, x, y, "k");
  }

  boolean move() throws NullPointerException {
    rn = new Random();
    eligiblePositions = new ArrayList<>();
    int x = getX();
    int y = getY();

    for(int i = x - 1; i <= x + 1; i++) {
      if(i < 0 || i >= cBoard.getColumns()) continue;
      for(int j = y - 1; j <= y + 1; j++) {
        if(j < 0 || j >= cBoard.getRows()) continue;
        if(i == x && j == y) continue;
        if(cBoard.cells[i][j] == null || isWhite() ^ cBoard.cells[i][j].isWhite()) {
          eligiblePositions.add(new int[]{i, j});
        }
      }
    }

    if(eligiblePositions.size() == 0) return false;
    setPosition(eligiblePositions.get(rn.nextInt(eligiblePositions.size())));
    return true;
  }
}
