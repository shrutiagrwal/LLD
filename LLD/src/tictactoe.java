import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
enum Piece {
    X, O
}

class Player {
    String name;
    Piece piece;

    Player(String name,Piece p)
    {
        this.name=name;
        this.piece=p;
    }

    public Piece getPlayerPiece() {
        return this.piece;
    }
    public String getName()
    {
        return this.name;
    }
}

class Board {
    int size;
    Piece[][] board;

    public Board(int size)
    {
        this.size=size;
        board=new Piece[size][size];
    }

    public boolean checkIfBoardIsEmpty() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidMove(int x, int y){
        if (x > 0 && x < size && y > 0 && y < size && board[x][y] == null)
            return true;
        return false;
    }

    public boolean setState(int x, int y, Piece p) {
        if (board[x][y] != null) {
            board[x][y] = p;
            return true;
        }
        return false;
    }

    public boolean checkWinner(Piece playerSymbol) {
        // Check rows
        for (int i = 0; i < size; i++) {
            boolean isWinningRow = true;
            for (int j = 0; j < size; j++) {
                if (board[i][j] != playerSymbol) {
                    isWinningRow = false;
                    break;
                }
            }
            if (isWinningRow) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < size; j++) {
            boolean isWinningColumn = true;
            for (int i = 0; i < size; i++) {
                if (board[i][j] != playerSymbol) {
                    isWinningColumn = false;
                    break;
                }
            }
            if (isWinningColumn) {
                return true;
            }
        }

        // Check diagonals
        boolean isWinningDiagonal1 = true;
        boolean isWinningDiagonal2 = true;
        for (int i = 0; i < size; i++) {
            if (board[i][i] != playerSymbol) {
                isWinningDiagonal1 = false;
            }
            if (board[i][size - 1 - i] != playerSymbol) {
                isWinningDiagonal2 = false;
            }
        }
        if (isWinningDiagonal1 || isWinningDiagonal2) {
            return true;
        }
        // No winner found
        return false;
    }

    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null)
                    System.out.print(board[i][j]);
                else
                    System.out.print("-");
            }
            System.out.println();
        }
    }
}

class Game {
    Deque<Player> players;
    Board board;

    public void initializeGame() {
        board = new Board(3);
        Player p1 = new Player("p1", Piece.O);
        Player p2 = new Player("p2", Piece.X);
        players.add(p1);
        players.add(p2);
    }

    public String startGame() {
        boolean isWinner = false;
        while (!isWinner) {
            if(!board.checkIfBoardIsEmpty())
                {
                    isWinner=true;
                    return "tie";
                }
            board.displayBoard();
            Player curr_player = players.pop();
            System.out.print(
                    "Player " + curr_player.getPlayerPiece()
                            + ", enter your move (row and column): ");
//            int row = scanner.nextInt();
//            int column = scanner.nextInt();
            int row=0;
            int column=0;

            if(board.isValidMove(row, column))
            {
                board.setState(row,column, curr_player.getPlayerPiece());
                if(board.checkWinner(curr_player.getPlayerPiece()))
                    {
                        isWinner=true;
                        return curr_player.getName();
                    }
                players.addLast(curr_player);
            }
            else{
                 System.out.println("Incorredt possition chosen, try again");
                players.addFirst(curr_player);
            }

        }
        return "";
    }
}

class tictactoe{
    public static void main(String args[]){
        Game tictactoe=new Game();
        tictactoe.initializeGame();
        tictactoe.startGame();
    }
}