import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Item");
            System.out.println("2. Sell Item");
            System.out.println("3. Display Inventory");
            System.out.println("4. Display Sold-Out Items");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = recursiveErrorCheck(scanner);

            switch (choice) {
                case 1 -> addItem(manager, scanner);
                case 2 -> sellItem(manager, scanner);
                case 3 -> manager.displayInventory();
                case 4 -> manager.displaySoldOutItems();
                case 5 -> {
                    manager.saveData();
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addItem(InventoryManager manager, Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = recursiveErrorCheck(scanner);
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        manager.addItem(name, quantity, price);
    }

    private static void sellItem(InventoryManager manager, Scanner scanner) {
        System.out.print("Enter the name of the item to sell: ");
        String name = scanner.nextLine();
        manager.sellItem(name);
    }

    private static int recursiveErrorCheck(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.print("Invalid input. Try again: ");
            scanner.nextLine(); // Clear the invalid input
            return recursiveErrorCheck(scanner);
        }
    }
}
