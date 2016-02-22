/**
 * Victor Kwak
 * A pair for when two queens are attacking each other
 */
class Pair {
    private Queen one;
    private Queen two;

    Pair(Queen one, Queen two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public int hashCode() {
        return (one.x * 17 + one.y) + (two.x * 17 + two.y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair pairToCompare = (Pair) o;
        return one.equals(pairToCompare.one) &&
                two.equals(pairToCompare.two) ||
                one.equals(pairToCompare.two) &&
                        two.equals(pairToCompare.one);
    }

    @Override
    public String toString() {
        return "{" + one.x + ", " + one.y + "}" +
                "{" + two.x + ", " + two.y + "}";
    }
}
