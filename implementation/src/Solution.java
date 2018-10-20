import java.util.ArrayList;
import java.util.HashSet;

public class Solution implements Comparable<Solution> {
    private ArrayList<Item> items = new ArrayList<>();
    private HashSet<String> conflicts = new HashSet<>();
    private int value = 0;
    private int totalWeight = 0;

    public void addItem(Item item) {
        items.add(item);
        value += item.getProfit();
        totalWeight += item.getWeight();
        for (Integer conflict : item.getConflicts()) {
            conflicts.add(conflict.toString());
        }
    }

    public boolean canAcceptItem(Item item) {
        boolean capacityIsOk = totalWeight + item.getWeight() < MainApplication.getCapacity();

        boolean conflictsAreOk = !conflicts.contains(MainApplication.getItems().indexOf(item) + "");
        return capacityIsOk && conflictsAreOk;
    }

    public boolean containsItem(Item item) {
        return items.contains(item);
    }

    @Override
    public int compareTo(Solution other) {
        if (getValue() == other.getValue()) {
            return 0;
        } else {
            return getValue() > other.getValue() ? 1 : -1;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Valor: %d, Peso total: %d\nItens: ", getValue(), getTotalWeight()));
        for (Item i : items) {
            result.append(MainApplication.getItems().indexOf(i)).append(" ");
        }
        return result.toString();
    }

    public int getValue() {
        return value;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public boolean canReplaceItem(Item item) {
        int conflictWeight = 0;
        for (int number : item.getConflicts()) {
            if (containsItem(MainApplication.getItems().get(number))) {
                conflictWeight += MainApplication.getItems().get(number).getWeight();
            }
        }
        return totalWeight - conflictWeight + item.getWeight() < MainApplication.getCapacity();
    }

    public void replaceItem(Item item) {
        for (int i : item.getConflicts()) {
            Item currentConflictedItem = MainApplication.getItems().get(i);
            if (containsItem(currentConflictedItem)) {
                items.remove(items.remove(currentConflictedItem));
                totalWeight -= currentConflictedItem.getWeight();
                value -= currentConflictedItem.getProfit();
            }
        }
        addItem(item);
    }
}
