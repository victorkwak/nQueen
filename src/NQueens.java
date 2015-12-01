import java.util.*;

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

//        //GA stuff
        solvedCounter = 0;
        stepCounter = 0;
        final double PERCENTAGE_THAT_MATES = .8;
        final double MUTATION_RATE = 0.05;
        final int NUMBER_OF_GENERATIONS = 1000;
        final int GA_LOOPS = 100;
        for (int i = 0; i < GA_LOOPS; i++) {
            Board gaBoard = geneticAlgorithm(N, NUMBER_OF_GENERATIONS, PERCENTAGE_THAT_MATES, MUTATION_RATE);
            if (gaBoard != null) {
                if (i == 9) {
                    System.out.println(gaBoard);
                }
            }
        }
        System.out.println();
        System.out.println("Number of problems attempted: " + GA_LOOPS);
        System.out.println("Number of problems solved: " + solvedCounter);
        System.out.println("Max number of generations before cutoff: " + NUMBER_OF_GENERATIONS);
        System.out.println("Percentage of population that mates: " + PERCENTAGE_THAT_MATES * 100d + "%");
        System.out.println("Rate of Mutation: " + MUTATION_RATE * 100d + "%");
        System.out.println("Average number of steps for Genetic Algorithm: " + (double) stepCounter / GA_LOOPS);
        System.out.println("Average time taken: " + (totalTime / 1000000000d) / GA_LOOPS + "s");
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

    public static Board geneticAlgorithm(int n, int maxGenerations, double proportionOfPopToMate, double mutationProbability) {
        Random rand = new Random();
        int populationSize = n * n;
        List<Board> population = new ArrayList<>(populationSize);

        for (int i = 0; i < populationSize; i++) {
            Board toAdd = new Board(n);
            if (toAdd.getHeuristicCost() == 0) {
                System.out.println("Obtained a solution while generating initial population: ");
                return toAdd;
            }
            population.add(toAdd);
        }

        int generation = 0;

        while (generation < maxGenerations) {
            ++generation;
//            System.out.println(generation);
            ++stepCounter;
            HashMap<Integer, Board> randomNumberToBoard = new HashMap<>();
//            randomNumberToBoard = new ArrayList<>(30000);
            int totalNumberOfNonAttackingPairs = 0;
            int currentRandomNumber = 0;
            for (int i = 0; i < populationSize; i++) {
                Board toAdd = population.get(i);
                totalNumberOfNonAttackingPairs += toAdd.getFitnessScore();
                for (; currentRandomNumber < totalNumberOfNonAttackingPairs; currentRandomNumber++) {
                    randomNumberToBoard.put(currentRandomNumber, toAdd);
//                    randomNumberToBoard.add(currentRandomNumber, toAdd);
                }

            }
            int numberOfMates = (int) (proportionOfPopToMate * populationSize);

            //ensure the number of mating individuals is even
            numberOfMates = (numberOfMates % 2 == 0 ? numberOfMates : numberOfMates + 1);
            int numberOfPairs = numberOfMates / 2;

            //generating mating pairs
            List<Board[]> allPairs = new ArrayList<>();
            for (int i = 0; i < numberOfPairs; i++) {
                Board[] pair = new Board[2];
                pair[0] = randomNumberToBoard.get(rand.nextInt(totalNumberOfNonAttackingPairs));
                pair[1] = randomNumberToBoard.get(rand.nextInt(totalNumberOfNonAttackingPairs));
//                pair[0] = randomNumberToBoard[rand.nextInt(totalNumberOfNonAttackingPairs)];
//                pair[1] = randomNumberToBoard[rand.nextInt(totalNumberOfNonAttackingPairs)];
                allPairs.add(pair);
            }

            //generating children of mating pairs
            ArrayList<Board> children = new ArrayList<>();
            for (Board[] pair : allPairs) {
                Board[] twins = BoardUtils.getTwins(pair, mutationProbability);
                if (twins[0].getHeuristicCost() == 0) {
                    return twins[0];
                }
                if (twins[1].getHeuristicCost() == 0) {
                    return twins[1];
                }
                children.add(twins[0]);
                children.add(twins[1]);
            }

            for (Board child : children) {
                population.add(child);
            }

            Collections.sort(population);
            population = population.subList(children.size(), population.size());
            if (population.size() != populationSize) {
                throw new RuntimeException("Incorrect Population amount");
            }
        }
        return null;
    }
}
