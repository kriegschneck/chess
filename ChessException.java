package chess;

class ChessException extends Exception {
  private String msg;
  ChessException(String msg) {this.msg = msg;}
  public String toString() {return msg;}
}
