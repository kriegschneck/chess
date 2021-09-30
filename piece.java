package chess;

abstract class piece {
  private boolean color;    //true = white, false = black
  private String letter;    //a letter to print
  private int x, y;         //a position of a figure

  piece(boolean color, String letter) {
    this.color = color;                   //set the color of the piece
    if(color) this.letter = letter;       //set the letter. plack pieces go with upper case letters
    else this.letter = letter.toUpperCase();
  }

  void setPosition(int x, int y) { //setting a new position of a piece
    this.x = x;
    this.y = y;
    game.board[x][y] = this;
  }

  void erasePosition() { //erase the current position
    game.board[x][y] = null;
  }

  int getX() {return x;}
  int getY() {return y;}
  boolean isWhite() {return color;}

  public String toString() {return letter;}

  abstract boolean move();
}


class pawn extends piece {
  pawn(int x, int y, boolean color) {
    super(color, "a");
    setPosition(x, y);
  }

  boolean move() {
    int x = getX();
    int y = getY();
    if (isWhite()) {
      if(y == 7) return false;
      if(game.board[x][y + 1] == null) {
        erasePosition();
        setPosition(x, ++y);
        return true;
      }
      if(x > 1 && game.board[x - 1][y + 1] != null && !game.board[x - 1][y + 1].isWhite()) {
        game.blacks.remove(game.board[x - 1][y + 1]);
        erasePosition();
        setPosition(--x, ++y);
        return true;
      }
      if(x < 7 && game.board[x + 1][y + 1] != null && !game.board[x + 1][y + 1].isWhite()) {
        game.blacks.remove(game.board[x + 1][y + 1]);
        erasePosition();
        setPosition(++x, ++y);
        return true;
      }
      return false;
    }
    return true;
  }
}

/*class king extends piece {
  king(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "k";
  }

}

class queen extends piece {
  queen(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "q";
  }
}

class rook extends piece {
  rook(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "r";
  }
}

class bishop extends piece {
  bishop(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "b";
  }
}

class knight extends piece {
  knight(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "k";
  }
}*/
