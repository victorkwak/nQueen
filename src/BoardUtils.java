import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Victor Kwak
 */
public class BoardUtils {
    /**
     * Uses Fisher-Yates shuffle to shuffle array
     *
     * @param toShuffle array to shuffle
     * @return Shuffled array
     */
    public static void shuffle(Square[][] toShuffle) {
        Random random = new Random();
        //Shuffle columns
        for (int j = 0; j < toShuffle[0].length; j++) {
            int rand = random.nextInt(toShuffle.length);
            swap(toShuffle, 0, j, rand, j);
        }
    }

    public static void swap(Square[][] array, int i, int j, int k, int l) {
        Square temp = array[i][j];
        array[i][j] = array[k][l];
        array[i][j].setXY(i, j);
        array[k][l] = temp;
        array[k][l].setXY(k, l);
    }

    public static Board lowestNeighbor(Board current) {
        List<Board> children = generateChildren(current);
        Board lowest = children.get(0);
        for (int i = 1; i < children.size(); i++) {
            if (children.get(i).getHeuristicCost() < lowest.getHeuristicCost()) {
                lowest = children.get(i);
            }
        }

        //Random
        List<Board> lowestChildren = new ArrayList<>();
        int lowestH = lowest.getHeuristicCost();
        for (Board child : children) {
            if (child.getHeuristicCost() == lowestH) {
                lowestChildren.add(child);
            }
        }
        Random random = new Random();
        return lowestChildren.get(random.nextInt(lowestChildren.size()));
    }

    public static List<Board> generateChildren(Board current) {
        List<Board> children = new ArrayList<>();
        for (Square[] squares : current.getBoard()) {
            for (Square square : squares) {
                if (square instanceof Queen) {
                    Square[][] toPassIn = copyBoard(current.getBoard());
                    generateChildrenForQueen(toPassIn, (Queen) square, children);
                }
            }
        }
        return children;
    }

    private static void generateChildrenForQueen(Square[][] board, Queen queen, List<Board> children) {
        if (queen.x != 0) {
            swap(board, queen.x, queen.y, 0, queen.y);
            children.add(new Board(board));
        }
        for (int i = 0; i < board.length - 1; i++) {
            swap(board, i, queen.y, i + 1, queen.y);
            if (i + 1 != queen.x) {
                children.add(new Board(board));
            }
        }
    }

    private static void printBoard(Square[][] board) {
        for (Square[] squares : board) {
            for (Square square : squares) {
                System.out.print(square);
            }
            System.out.println();
        }
    }

    public static Square[][] copyBoard(Square[][] board) {
        Square[][] newBoard = new Square[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] instanceof Queen) {
                    newBoard[i][j] = new Queen(i, j);
                } else {
                    newBoard[i][j] = new Empty(i, j);
                }
            }
        }
        return newBoard;
    }
}
