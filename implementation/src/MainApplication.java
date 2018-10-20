import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApplication {
    private static int numberOfItems;
    private static int capacity;
    private static ArrayList<Item> items = new ArrayList<>();
    private static Random random;
    private static int populationSize;
    private static int probabilityOfMutation;
    private static int maxGenerationsWithoutImprovement;
    private static Solution currentBestSolution;

    public static void main(String[] args) {
        long initialTime = System.currentTimeMillis();

        readArgs(args);
        readInput();

        ArrayList<Solution> currentPopulation = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            Solution currentSolution = generateRandomSolution();
            currentPopulation.add(currentSolution);
        }

        currentPopulation.sort(Collections.reverseOrder());
        currentBestSolution = currentPopulation.get(0);

        int unimprovedGenerations = 0;
        while (unimprovedGenerations < maxGenerationsWithoutImprovement) {
            currentPopulation = generateNewPopulation(currentPopulation);
            currentPopulation.sort(Collections.reverseOrder());

            if (currentPopulation.get(0).getValue() > currentBestSolution.getValue()) {
                currentBestSolution = currentPopulation.get(0);
            } else {
                unimprovedGenerations++;
            }
        }

        System.out.println("Melhor solução: ");
        System.out.println(currentBestSolution.toString());
        System.out.println("Tempo utilizado: ");
        System.out.println(new Long(System.currentTimeMillis() - initialTime).floatValue() / 1000 + "s");
    }

    private static void readArgs(String args[]) {
        int size = args.length;
        if (size == 0) {
            System.out.println("Forneça a semente do gerador de números pseudo-aleatórios.");
            System.exit(-1);
        } else if (size == 1) {
            System.out.println("Forneça o tamanho da população.");
            System.exit(-1);
        } else if (size == 2) {
            System.out.println("Informe a probabilidade de mutação entre 0 e 100");
            System.exit(-1);
        } else if (size == 3) {
            System.out.println("Informe o número de gerações sem melhoras para terminar o algoritmo");
            System.exit(-1);
        } else {
            random = new Random(Integer.parseInt(args[0]));
            populationSize = Integer.parseInt(args[1]);
            probabilityOfMutation = Integer.parseInt(args[2]);
            maxGenerationsWithoutImprovement = Integer.parseInt(args[3]);
        }
    }

    private static Solution selectParent(ArrayList<Solution> population) {
        population.sort(Collections.reverseOrder());
        return population.get(random.nextInt(population.size() / 3));
    }

    private static ArrayList<Solution> generateNewPopulation(ArrayList<Solution> oldPopulation) {
        ArrayList<Solution> newPopulation = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            Solution firstParent = selectParent(oldPopulation);
            Solution secondParent = selectParent(oldPopulation);
            Solution newIndividual = crossover(firstParent, secondParent);

            if (random.nextInt(100) < probabilityOfMutation) {
                mutateSolution(newIndividual);
                mutateSolution(newIndividual);
            }
            newPopulation.add(newIndividual);
        }

        return newPopulation;
    }


    private static Solution generateRandomSolution() {
        Solution solution = new Solution();

        for (Item currentItem : items) {
            if (random.nextBoolean() && solution.canAcceptItem(currentItem)) {
                solution.addItem(currentItem);
            }
        }

        return solution;
    }

    private static Solution crossover(Solution firstParent, Solution secondParent) {
        Solution solution = new Solution();

        boolean firstIsTheOrigin = random.nextBoolean();
        for (Item currentItem : items) {
            if (firstIsTheOrigin) {
                if (firstParent.containsItem(currentItem) && solution.canAcceptItem(currentItem)) {
                    solution.addItem(currentItem);
                    firstIsTheOrigin = !firstIsTheOrigin;
                }
            } else {
                if (secondParent.containsItem(currentItem) && solution.canAcceptItem(currentItem)) {
                    solution.addItem(currentItem);
                    firstIsTheOrigin = !firstIsTheOrigin;
                }
            }
        }

        return solution;
    }

    private static void mutateSolution(Solution solution) {
        int itemToAdd = random.nextInt(numberOfItems);

        int iterations = 0;
        while (solution.containsItem(items.get(itemToAdd)) || !solution.canReplaceItem(items.get(itemToAdd))) {
            itemToAdd = random.nextInt(numberOfItems);
            iterations++;
            if (iterations == numberOfItems)
                return;
        }

        solution.replaceItem(items.get(itemToAdd));
    }


    private static void readInput() {
        Scanner scanner = new Scanner(System.in);
        Pattern digitsPattern = Pattern.compile("[0-9]+");

        String itemsLine = scanner.nextLine();
        Matcher itemsMatcher = digitsPattern.matcher(itemsLine);

        itemsMatcher.find();
        numberOfItems = Integer.parseInt(itemsMatcher.group());

        String capacityLine = scanner.nextLine();
        Matcher capacityMatcher = digitsPattern.matcher(capacityLine);

        capacityMatcher.find();
        capacity = Integer.parseInt(capacityMatcher.group());

        scanner.nextLine();

        for (int i = 0; i < numberOfItems; i++) {
            String currentItemLine = scanner.nextLine();
            Matcher itemMatcher = digitsPattern.matcher(currentItemLine);
            itemMatcher.find();

            itemMatcher.find();
            int currentItemProfit = Integer.parseInt(itemMatcher.group());
            itemMatcher.find();
            int currentItemWeight = Integer.parseInt(itemMatcher.group());

            Item currentItem = new Item(currentItemProfit, currentItemWeight);
            items.add(currentItem);
        }

        scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            Matcher lineMatcher = digitsPattern.matcher(currentLine);
            if (lineMatcher.find()) {
                int firstConflictItem = Integer.parseInt(lineMatcher.group());
                lineMatcher.find();
                int secondConflictItem = Integer.parseInt(lineMatcher.group());
                items.get(firstConflictItem).addConflict(secondConflictItem);
                items.get(secondConflictItem).addConflict(firstConflictItem);
            }
        }
    }

    public static int getCapacity() {
        return capacity;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }
}
