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
    public static char[] shuffle(char[] toShuffle) {
        Random random = new Random();
        for (int i = 0; i < toShuffle.length; i++) {
            int rand = random.nextInt(toShuffle.length);
            char temp = toShuffle[i];
            toShuffle[i] = toShuffle[rand];
            toShuffle[rand] = temp;
        }
        return toShuffle;
    }


}
