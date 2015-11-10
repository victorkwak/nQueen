/**
 * Created by Victor Kwak on 11/8/15.
 * <p>
 * Formulation
 * • States: Any arrangement of 0 to 8 queens on the board is a state.
 * • Initial state: No queens on the board.
 * • Actions: Add a queen to any empty square.
 * • Transition model: Returns the board with a queen added to the specified square.
 * • Goal test: 8 queens are on the board, none attacked.
 */
public class NQueens {
    public static void main(String[] args) {
        int n = 17;
        int spaces = (n * n) - n;
        StringBuilder stringBoard = new StringBuilder(spaces + n);
        for (int i = 0; i < n; i++) {
            stringBoard.append("Q");
        }
        for (int i = 0; i < spaces; i++) {
            stringBoard.append('-');
        }

        Board board = new Board(stringBoard.toString(), n);
        System.out.println(board);
    }

    public static void hillClimbing(Board problem) {
        Board current = problem;
    }
}
