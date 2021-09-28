package chess;

abstract class piece {
  boolean isAlive = true;
  private int x, y; //position of a figure

  void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  int getX() {
    return x;
  }

  int getY() {
    return y;
  }

  abstract void move();
}


class pawn extends piece {
  pawn(int x, int y) {
    setPosition(x, y);
  }

  public String toString() {
    return "p";
  }

  void move() {
    int x = getX();
    int y = getY();
    setPosition(x,++y);
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
