package chess;

class Game {
  public static void main(String[] args) {
    final int rows = 8;     //cannot be less than 8
    final int columns = 8;  //cannot be less than 8
    Board board = new Board(columns, rows); //creating a board

    try { //adding white pieces to the board
      for(int i = 0; i < columns; i++) {            //add 8 pawns to the list of pieces
        board.whites.add(new Pawn(board, i, 1));
      }
      board.whites.add(new Rook(board, 0, 0));
      board.whites.add(new Rook(board, columns - 1, 0));
      board.whites.add(new Knight(board, 1, 0));
      board.whites.add(new Knight(board, columns - 2, 0));
      board.whites.add(new Bishop(board, 2, 0));
      board.whites.add(new Bishop(board, columns - 3, 0));
      board.whites.add(new Queen(board, 3, 0));
      board.whites.add(new King(board, 4, 0));
    } catch (Exception e) {
      board.pw.println("Couldn't create a white piece");
      return;
    }

    try { //adding black pieces to the board
      for(int i = 0; i < columns; i++) {            //add 8 pawns to the list of pieces
        board.blacks.add(new Pawn(board, i, rows - 2));
      }
      board.blacks.add(new Rook(board, 0, rows - 1));
      board.blacks.add(new Rook(board, columns - 1, rows - 1));
      board.blacks.add(new Knight(board, 1, rows - 1));
      board.blacks.add(new Knight(board, columns - 2, rows - 1));
      board.blacks.add(new Bishop(board, 2, rows - 1));
      board.blacks.add(new Bishop(board, columns - 3, rows - 1));
      board.blacks.add(new Queen(board, 3, rows - 1));
      board.blacks.add(new King(board, 4, rows - 1));
    } catch (Exception e) {
      board.pw.println("Couldn't create a black piece");
      return;
    }

    int turn = 0; //for counting turns
    while(true) { //playing the game
      board.printBoard(turn++);
      try {
        if(turn % 2 == 0) board.pieceSelectionAndMove(board.blacks);
        else board.pieceSelectionAndMove(board.whites);
      } catch (Exception e) {
        board.pw.println(e);
        break;
      }

      try {
        Thread.sleep(200); //time between moves
      } catch (Exception e) {
        board.pw.println("The game was interrupted");
        break;
      }
    }
    //board.pw.println(board.whites);
    //board.pw.println(board.blacks);

  }
}
