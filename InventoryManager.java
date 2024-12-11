import java.io.*;
import java.util.ArrayList;

public class InventoryManager {
    private ArrayList<Item> inventory;
    private String[] soldOutItems;
    private final String INVENTORY_FILE = "inventory.txt";
    private final String SOLD_OUT_FILE = "sold_out.txt";

    public InventoryManager() {
        this.inventory = new ArrayList<>();
        this.soldOutItems = new String[10]; // Initial size, expandable
        loadInventory();
        loadSoldOutItems();
    }

    public void addItem(String name, int quantity, double price) {
        inventory.add(new Item(name, quantity, price));
    }

    public void sellItem(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            if (item.getName().equalsIgnoreCase(name)) {
                if (item.getQuantity() > 0) {
                    item.setQuantity(item.getQuantity() - 1);
                    if (item.getQuantity() == 0) {
                        moveToSoldOut(item.getName());
                        inventory.remove(i);
                    }
                }
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    private void moveToSoldOut(String itemName) {
        for (int i = 0; i < soldOutItems.length; i++) {
            if (soldOutItems[i] == null) {
                soldOutItems[i] = itemName;
                return;
            }
        }
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    public void displaySoldOutItems() {
        System.out.println("Sold-Out Items:");
        for (String item : soldOutItems) {
            if (item != null) {
                System.out.println(item);
            }
        }
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(inventory);
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SOLD_OUT_FILE))) {
            oos.writeObject(soldOutItems);
        } catch (IOException e) {
            System.out.println("Error saving sold-out items: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(INVENTORY_FILE))) {
            inventory = (ArrayList<Item>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous inventory found. Starting fresh.");
        }
    }

    private void loadSoldOutItems() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SOLD_OUT_FILE))) {
            soldOutItems = (String[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous sold-out items found. Starting fresh.");
        }
    }
}
