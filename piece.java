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
  pawn (int x, int y) {
    setPosition(x, y);
  }


  void move() {
    int x = getX();
    int y = getY();
    setPosition(++x,++y);
  }
}
