package practice;

import java.util.Deque;
import java.util.Scanner;

enum PlayingPiece {
    X, O
}

class Player {
    String name;
    String id;
    PlayingPiece playerPiece;

    Player(String name, String id, PlayingPiece playerPiece) {
        this.name = name;
        this.id = id;
        this.playerPiece = playerPiece;
    }

    PlayingPiece getPiece() {
        return this.playerPiece;
    }

    String getName() {
        return this.name;
    }

}

class Board {
    int size;
    PlayingPiece[][] board;

    Board(int size) {
        this.size = size;
        board = new PlayingPiece[size][size];
    }

    boolean checkIfMoveIsValid(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size && board[x][y] == null)
            return true;
        return false;
    }

    boolean setState(PlayingPiece p, int x, int y) {
        if (checkIfMoveIsValid(x, y)) {
            board[x][y] = p;
            return true;
        }
        return false;
    }

    boolean checkIfBoardIsEmpty() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (board[i][j] == null)
                    return true;
        }
        return false;
    }

    boolean checkIfThereIsWinner(PlayingPiece p) {
        //check for rows
        for (int i = 0; i < size; i++) {
            boolean isWinning = true;
            for (int j = 0; j < size; j++) {
                if (board[i][j] != p) {
                    isWinning = false;
                    break;
                }
            }
            if (isWinning)
                return true;
        }
        //check for columns
        for (int i = 0; i < size; i++) {
            boolean isWinning = true;
            for (int j = 0; j < size; j++) {
                if (board[j][i] != p) {
                    isWinning = false;
                    break;
                }
                if (isWinning)
                    return true;

            }
        }
        // check for diagnols
        boolean isd1 = true;
        for (int i = 0; i < size; i++) {
            if (board[i][i] != p) {
                isd1 = false;
                break;
            }
        }
        if (isd1)
            return true;
        boolean isd2 = true;
        for (int i = 0; i < size; i++) {
            if (board[i][size - i - 1] != p) {
                isd2 = false;
                break;
            }
        }
        return isd2;
    }

    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null)
                    System.out.print(board[i][j] + " ");
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

    public void initialiseGame() {
        board = new Board(3);
        Player p1 = new Player("a", "23", PlayingPiece.O);
        Player p2 = new Player("b", "3434", PlayingPiece.X);
        players.add(p1);
        players.add(p2);
    }

    Scanner scanner = new Scanner(System.in);

    public String playGame() {
        String state = "DRAW";
        while (true) {
            if (!board.checkIfBoardIsEmpty()) {
                break;
            }
            Player curr_player = players.pop();
            System.out.println("player-" + curr_player.getName() + " Playing piece " + curr_player.getPiece() + " enter your move row and column");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            board.displayBoard();
            if (board.checkIfMoveIsValid(x, y)) {
                boolean setState = board.setState(curr_player.getPiece(), x, y);
                if (setState) {
                    if (board.checkIfThereIsWinner(curr_player.getPiece())) {
                        state = "player" + curr_player.getName() + " wins";
                        break;
                    } else {
                        players.addLast(curr_player);
                    }
                } else {
                    System.out.println("Incorrect position chosen, try again");
                    players.addFirst(curr_player);
                }

            }
        }
        return state;
    }
}

public class tictactoe {
    public static void main(String[] args) {
        Game game = new Game();
        game.initialiseGame();

        game.playGame();
    }
}