import java.util.ArrayList;

public class Item {
    private int profit;
    private int weight;
    private ArrayList<Integer> conflicts = new ArrayList<>();

    public Item(int profit, int weight) {
        this.profit = profit;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return String.format("Lucro: %d, Peso: %d, Conflitos: %s", getProfit(), getWeight(), getConflicts().toString());
    }

    public void addConflict(int itemNumber) {
        conflicts.add(itemNumber);
    }

    public ArrayList<Integer> getConflicts() {
        return conflicts;
    }
}
