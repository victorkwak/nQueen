/**
 * Victor Kwak
 */
abstract class Square {
    int x;
    int y;
    String symbol;

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Square)) {
            return false;
        }
        Square compare = (Square) o;
        return symbol.equals(compare.symbol) && x == compare.x && y == compare.y;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
