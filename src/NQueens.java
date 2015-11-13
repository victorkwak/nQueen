import java.util.*;

/**
 * Created by Victor Kwak on 11/8/15.
 */
public class NQueens {

    public static void main(String[] args) {
        int n = 17;
        int counter = 0;
        int loops = 1000;
        long totalTime = 0;
        for (int i = 0; i < loops; i++) {
            long start = System.nanoTime();
            Board board = hillClimbing(new Board(n));
            totalTime += (System.nanoTime() - start);
            if (board.getHeuristicCost() == 0) {
                ++counter;
            }
        }
        System.out.println((totalTime / 1000000000d) / loops);
        System.out.println(counter);

        //GA stuff
        counter = 0;
        double percentageThatMates = .8;
        double mutationRate = 0.01;
        int numberOfGenerations = 1000;
        for (int i = 0; i < 10; i++) {
            Board gaBoard = geneticAlgorithm(n, numberOfGenerations, percentageThatMates, mutationRate);
            if (gaBoard != null) {
                System.out.println("Solved!");
                System.out.println(gaBoard);
            } else {
                System.out.println("Orgy failed");
            }
        }

        System.out.println((totalTime / 1000000000d) / loops);
        System.out.println(counter);
    }

    public static Board hillClimbing(Board problem) {
        Board neighbor;
        int sameH = 0;
        int sameHMax = 100;
        while (true) {
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

    public static Board simulatedAnnealing(Board problem, int annealLimit) {
        for (int i = 0; i < annealLimit; i++) {
            double t = expDecay(i);
            Board next = BoardUtils.randomChild(problem);
            if (next.getHeuristicCost() == 0) {
                return next;
            }
            double deltaE = problem.getHeuristicCost() - next.getHeuristicCost();
            if (deltaE > 0) {
                problem = next;
            } else {
                Random random = new Random();
                double probability = Math.exp(deltaE / t);
                if (random.nextDouble() < probability) {
                    problem = next;
                }
            }
        }
        return problem;
    }

    /**
     * Exponential decay function
     *
     * @param time
     * @return
     */
    public static double expDecay(int time) {
        return 300 * Math.pow(0.5, time);
    }

    public static Board geneticAlgorithm(int n, int maxGenerations, double proportionOfPopToMate, double mutationProbability) {
        Random rand = new Random();
        int populationSize = n * n;
        List<Board> population = new ArrayList<>(populationSize);

        for (int i = 0; i < populationSize; i++) {
            Board toAdd = new Board(n);
            if (toAdd.getHeuristicCost() == 0) {
                System.out.println("Obtained a solution while generating initial population: ");
                System.out.println(toAdd);
                return toAdd;
            }
            population.add(toAdd);
        }

        int generation = 0;
        while (generation < maxGenerations) {
            ++generation;
            /**
             * Magic Hashmap maps each possible randomly generated number to a board, obeying the probabilities
             * of the heuristic function for the genetic algorithm. Memory heavy but hopefully fast.
             */
            HashMap<Integer, Board> randomNumberToBoard = new HashMap<>();
            int totalNumberOfNonAttackingPairs = 0;
            int currentRandomNumber = 0;
            for (int i = 0; i < populationSize; i++) {
                Board toAdd = population.get(i);
                totalNumberOfNonAttackingPairs += toAdd.getFitnessScore();
                for (; currentRandomNumber < totalNumberOfNonAttackingPairs; currentRandomNumber++) {
                    randomNumberToBoard.put(currentRandomNumber, toAdd);
                }
            }
            int numberOfMates = (int) (proportionOfPopToMate * populationSize);
            //ensure the number of mating individuals is even
            numberOfMates = (numberOfMates % 2 == 0 ? numberOfMates : numberOfMates + 1);
            int numberOfPairs = numberOfMates / 2;

            //generating mating pairs
            ArrayList<Board[]> allPairs = new ArrayList<>();
            for (int i = 0; i < numberOfPairs; i++) {
                Board[] pair = new Board[2];
                pair[0] = randomNumberToBoard.get(rand.nextInt(totalNumberOfNonAttackingPairs));
                pair[1] = randomNumberToBoard.get(rand.nextInt(totalNumberOfNonAttackingPairs));
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
