import java.util.Random;

/**
 * Created by Victor Kwak on 11/9/15.
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
            for (int i = 0; i < toShuffle.length; i++) {
                int rand = random.nextInt(toShuffle.length);
                swap(toShuffle, i, j, rand, j);
            }
        }
    }

    public static void swap(Square[][] array, int i, int j, int k, int l) {
        Square temp = array[i][j];
        array[i][j] = array[k][l];
        array[i][j].setXY(i, j);
        array[k][l] = temp;
        array[k][l].setXY(k, l);
    }

    public Board highestNeighbor(Board current) {
        Board highest = null;
        for (Square[] squares : current.getBoard()) {
            for (Square square : squares) {
                if (square instanceof Queen) {

                }
            }
        }
        return highest;
    }

//    private Board swapUp(Board current, Queen queen) {
//        if (queen.x == 0) {
//            return null;
//        }
//        Square[][] temp = Arrays.copyOf(current.getBoard(), current.getBoard().length);
//        Square temp =
//
//
//
//        for (int i = queen.x - 1; i >= 0; i--) {
//            if (board[i][queen.y] instanceof Queen) {
//                attackingPairs.add(new Pair(queen, (Queen) board[i][queen.y]));
//                return;
//            }
//        return null;
//    }
}
