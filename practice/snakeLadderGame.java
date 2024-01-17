import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

class Jump {
    int start;
    int end;
}

class Cell {
    Jump jump;
}

class Dice {
    int diceCount;
    int min;
    int max;

    Dice(int diceCount) {
        this.diceCount = diceCount;
        this.min = 1;
        this.max = 6;
    }

    public int rollDice() {
        int diceUsed = 0, sum = 0;
        while (diceUsed < diceCount) {
            sum += ThreadLocalRandom.current().nextInt(this.min, this.max + 1);
            diceUsed++;
        }
        return sum;
    }
}

class Player {
    int id;
    int currPos;

    Player(int id, int pos) {
        this.id = id;
        this.currPos = pos;
    }
}

class Board {
    Cell[][] cells;

    void initialseCell(int boardSize) {
        cells = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void initialiseBoard() {
        int snakes = 0;
        int ladders = 0;
        int boardSize = 10;
        initialseCell(boardSize);
        addSnakesAndLadders(cells, snakes, ladders, boardSize);
    }

    void addSnakesAndLadders(Cell[][] cells, int snakes, int ladders, int boardSize) {
        while (snakes > 0) {
            int head = ThreadLocalRandom.current().nextInt(1, boardSize * boardSize - 1);
            int tail = ThreadLocalRandom.current().nextInt(1, boardSize * boardSize - 1);
            if (head <= tail) {
                continue;
            } else {
                int row = head % 10;
                int col = head / 10;
                Jump snake = new Jump();
                snake.start = head;
                snake.end = tail;
                Cell cell = cells[row][col];
                cell.jump = new Jump();  // Instantiate a new Jump object
                cell.jump.start = head;
                cell.jump.end = tail;
                snakes--;
            }
        }
        while (ladders > 0) {
            int head = ThreadLocalRandom.current().nextInt(1, boardSize * boardSize - 1);
            int tail = ThreadLocalRandom.current().nextInt(1, boardSize * boardSize - 1);
            if (head >= tail)
                continue;
            else {
                int row = head % 10;
                int col = head / 10;
                Jump ladder = new Jump();
                ladder.start = head;
                ladder.end = tail;
                Cell cell = cells[row][col];
                cell.jump = ladder;
                ladders--;
            }
        }
    }

    Cell getCell(int row, int col) {
        return this.cells[row][col];
    }
}

class Game {
    Board board;
    Deque<Player> players;
    Dice dice;

    public void initialiseGame() {
        board = new Board();
        board.initialiseBoard();
        Player p1 = new Player(1, 0);
        Player p2 = new Player(2, 0);
        dice = new Dice(1);
        players = new LinkedList<>();
        players.add(p1);
        players.add(p2);

    }

    public void playGame() {
        Player winner = null;
        while (winner == null) {
            Player currP = players.removeFirst();
            players.addLast(currP);
            int newPos = currP.currPos+dice.rollDice();
            System.out.println("curr player is " + currP.id + " curr player postion " + currP.currPos + " dice position" + newPos);
            newPos = checkIfThereIsJump(newPos);
            currP.currPos = newPos;
            System.out.println("curr player is " + currP.id + "curr player postion " + currP.currPos);
            if (currP.currPos >= 99)
                winner = currP;
        }
        System.out.println(winner.id);
    }

    private int checkIfThereIsJump(int pos) {
        if (pos > board.cells.length * board.cells.length - 1) {
            return pos;
        }
        int row = pos / 10;
        int col = pos % 10;
        Cell cell = board.getCell(row, col);
        if (cell.jump != null &&  cell.jump.start == pos) {
            return cell.jump.end;
        }
        return pos;
    }
}

public class snakeLadderGame {
    public static void main(String args[]) {
        Game game = new Game();
        game.initialiseGame();
        game.playGame();
    }
}
