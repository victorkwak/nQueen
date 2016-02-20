/**
 * Victor Kwak
 */
public class NQueens {

    private static int stepCounter;

    /**
     * For analysis, you should generate a large number of 17-queens instances (>100) and solve them.
     * Measure the percentage of solved problems, search costs and the average running time.
     * Explain why you get such results, for example, why the steepest-ascent hill climbing can only
     * solve about 14% of the problems (remember this percentage is for 8-queen, your answer might be different,
     * smaller percentage), or what kind of improvements have you made to make your algorithms more efficient.
     *
     * @param args
     */

    public static void main(String[] args) {
        //Program parameters
        final int N = 17;
        final int HILLCLIMBING_LOOPS = 100;

        long totalTime = 0;

//        //HillClimbing with lateral movement
        int solvedCounter = 0;
        stepCounter = 0;
        for (int i = 0; i < HILLCLIMBING_LOOPS; i++) {
            long start = System.nanoTime();
            Board board = hillClimbing(new Board(N));
            totalTime += (System.nanoTime() - start);
            if (board.getHeuristicCost() == 0) {
                ++solvedCounter;
            }
            if (i == HILLCLIMBING_LOOPS - 1) {
                System.out.println(board);
            }
        }
        System.out.println("Number of problems attempted: " + HILLCLIMBING_LOOPS);
        System.out.println("Number of problems solved: " + solvedCounter);
        System.out.println("Average number of steps for Hill Climbing: " + (double) stepCounter / HILLCLIMBING_LOOPS);
        System.out.println("Average time taken: " + (totalTime / 1000000000d) / HILLCLIMBING_LOOPS + "s");
        System.out.println("Average steps take: " + (double) solvedCounter / HILLCLIMBING_LOOPS);
    }

    public static Board hillClimbing(Board problem) {
        Board neighbor;
        int sameH = 0;
        int sameHMax = 100;
        while (true) {
            ++stepCounter;
            neighbor = BoardUtils.lowestNeighbor(problem);
            if (problem.getHeuristicCost() < neighbor.getHeuristicCost()) {
                return problem;
            }
            if (neighbor.getHeuristicCost() == problem.getHeuristicCost()) {
                ++sameH;
                if (sameH == sameHMax) {
                    return problem;
                }
            }
            problem = neighbor;
        }
    }
}
