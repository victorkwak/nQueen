import java.util.HashSet;
import java.util.Set;

/**
 * Created by Victor Kwak on 11/9/15.
 */
public class Board {
    private static Square[][] board;
    private static Set<Pair> attackingPairs;
    private int heuristicCost;

    public Board(String stringBoard) {
        board = new Square[8][8];
        attackingPairs = new HashSet<>();
        char[] temp = stringBoard.toCharArray();
        BoardUtils.shuffle(temp);
        int k = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (temp[k] == 'Q') {
                    board[i][j] = new Queen(i, j);
                } else {
                    board[i][j] = new Empty(i, j);
                }
                ++k;
            }
        }
        heuristicCost = heuristic();
    }

    private int heuristic() {
        for (Square[] squares : board) {
            for (Square square : squares) {
                if (square instanceof Queen) {
                    checkUp((Queen) square);
                    checkDown((Queen) square);
                }
            }
        }
        attackingPairs.forEach(System.out::println);
        return attackingPairs.size();
    }

    private void checkUp(Queen queen) {
        if (queen.x == 0) {
            return;
        }
        for (int i = queen.x - 1; i >= 0; i--) {
            if (board[i][queen.y] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][queen.y]));
                return;
            }
        }
    }

    private void checkDown(Queen queen) {
        if (queen.x == board[0].length - 1) {
            return;
        }
        for (int i = queen.x + 1; i < board[i].length; i++) {
            System.out.println(queen.x + ", " + queen.y + " " + i);
            if (board[i][queen.y] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][queen.y]));
                return;
            }
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

    /**
     * A pair for when two queens are attacking each other
     */
    private class Pair {
        Queen[] pair;

        Pair(Queen one, Queen two) {
            pair = new Queen[2];
            pair[0] = one;
            pair[1] = two;
        }

        @Override
        public String toString() {
            return "{" + pair[0].x+ ", " + pair[0].y + "}" +
                    "{" + pair[1].x+ ", " + pair[1].y + "}";
        }
    }

    private abstract class Square {
        int x;
        int y;
        char symbol;

        @Override
        public String toString() {
            return String.valueOf(symbol);
        }
    }

    private class Queen extends Square {
        Queen(int x, int y) {
            this.x = x;
            this.y = y;
            symbol = 'Q';
        }
    }

    private class Empty extends Square {
        Empty(int x, int y) {
            this.x = x;
            this.y = y;
            symbol = '-';
        }
    }
}
