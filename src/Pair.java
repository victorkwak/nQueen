/**
 * Victor Kwak
 * A pair for when two queens are attacking each other
 */
class Pair {
    Queen[] pair;

    Pair(Queen one, Queen two) {
        pair = new Queen[2];
        pair[0] = one;
        pair[1] = two;
    }

    @Override
    public int hashCode() {
        return (pair[0].x * 17 + pair[0].y) + (pair[1].x * 17 + pair[1].y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair pairToCompare = (Pair) o;
        return pair[0].equals(pairToCompare.pair[0]) &&
                pair[1].equals(pairToCompare.pair[1]) ||
                pair[0].equals(pairToCompare.pair[1]) &&
                        pair[1].equals(pairToCompare.pair[0]);
    }

    @Override
    public String toString() {
        return "{" + pair[0].x + ", " + pair[0].y + "}" +
                "{" + pair[1].x + ", " + pair[1].y + "}";
    }
}
