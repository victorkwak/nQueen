import java.util.HashSet;
import java.util.Set;

/**
 * Created by Victor Kwak on 11/9/15.
 */
public class Board {
    private Square[][] board;
    private int heuristicCost;

    public Board(String stringBoard, int n) {
        board = new Square[n][n];
        int k = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (stringBoard.charAt(k) == 'Q') {
                    board[i][j] = new Queen(i, j);
                } else {
                    board[i][j] = new Empty(i, j);
                }
                ++k;
            }
        }
        BoardUtils.shuffle(board);
        heuristicCost = heuristic();
    }

    public Square[][] getBoard() {
        return board;
    }

    public Board(Square[][] board) {
        this.board = board;
        heuristicCost = heuristic();
    }

    private int heuristic() {
        Set<Pair> attackingPairs = new HashSet<>();
        for (Square[] squares : board) {
            for (Square square : squares) {
                if (square instanceof Queen) {
                    checkLeft((Queen) square, attackingPairs);
                    checkUpLeft((Queen) square, attackingPairs);
                    checkUpRight((Queen) square, attackingPairs);
                    checkRight((Queen) square, attackingPairs);
                    checkDownLeft((Queen) square, attackingPairs);
                    checkDownRight((Queen) square, attackingPairs);
                }
            }
        }
        attackingPairs.forEach(System.out::println);
        return attackingPairs.size();
    }

    private void checkLeft(Queen queen, Set<Pair> attackingPairs) {
        if (queen.y == 0) {
            return;
        }
        for (int j = queen.y - 1; j >= 0; j--) {
            if (board[queen.x][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[queen.x][j]));
                return;
            }
        }
    }

    private void checkRight(Queen queen, Set<Pair> attackingPairs) {
        if (queen.y == board[0].length - 1) {
            return;
        }
        for (int j = queen.y + 1; j < board[0].length; j++) {
            if (board[queen.x][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[queen.x][j]));
                return;
            }
        }
    }

    private void checkUpRight(Queen queen, Set<Pair> attackingPairs) {
        if (queen.x == 0 && queen.y == board.length - 1) {
            return;
        }
        int i = queen.x - 1;
        int j = queen.y + 1;
        while (i >= 0 && j < board.length) {
            if (board[i][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][j]));
                return;
            }
            --i;
            ++j;
        }
    }

    private void checkUpLeft(Queen queen, Set<Pair> attackingPairs) {
        if (queen.x == 0 && queen.y == 0) {
            return;
        }
        int i = queen.x - 1;
        int j = queen.y - 1;
        while (i >= 0 && j >= 0) {
            if (board[i][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][j]));
                return;
            }
            --i;
            --j;
        }
    }

    private void checkDownRight(Queen queen, Set<Pair> attackingPairs) {
        if (queen.x == board[0].length - 1 && queen.y == board.length - 1) {
            return;
        }
        int i = queen.x + 1;
        int j = queen.y + 1;
        while (i < board.length && j < board.length) {
            if (board[i][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][j]));
                return;
            }
            ++i;
            ++j;
        }
    }

    private void checkDownLeft(Queen queen, Set<Pair> attackingPairs) {
        if (queen.x == board[0].length - 1 && queen.y == 0) {
            return;
        }
        int i = queen.x + 1;
        int j = queen.y - 1;
        while (i < board.length && j >= 0) {
            if (board[i][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][j]));
                return;
            }
            ++i;
            --j;
        }
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder(64);
        for (Square[] squares : board) {
            for (int i = 0; i < squares.length; i++) {
                toString.append(squares[i]);
                if (i == squares.length - 1) {
                    toString.append("\n");
                } else {
                    toString.append(' ');
                }
            }
        }
        return toString.toString() + "\n" +
                heuristicCost;
    }

}
