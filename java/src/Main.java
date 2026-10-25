import java.util.List;
import java.util.Scanner;

public class Main {

    private static final LeadTracker tracker = new LeadTracker();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Simple Lead Tracker ===");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addLead();
                    break;
                case "2":
                    viewLeads();
                    break;
                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2 or 3.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add a lead");
        System.out.println("2. View all leads");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addLead() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Score (0-100): ");
        String score = scanner.nextLine();

        String error = tracker.addLead(name, email, score);
        if (error != null) {
            System.out.println("Lead not added: " + error);
        } else {
            System.out.println("Lead added successfully.");
        }
    }

    private static void viewLeads() {
        if (tracker.isEmpty()) {
            System.out.println("No leads yet. Choose option 1 to add your first lead.");
            return;
        }

        List<Lead> leads = tracker.getAllLeads();
        System.out.println();
        System.out.printf("%-3s %-20s %-28s %-6s %-8s%n", "#", "Name", "Email", "Score", "Priority");
        System.out.println("-".repeat(68));
        for (int i = 0; i < leads.size(); i++) {
            Lead lead = leads.get(i);
            System.out.printf("%-3d %-20s %-28s %-6d %-8s%n",
                    i + 1, lead.getName(), lead.getEmail(), lead.getScore(), lead.getPriority());
        }
        System.out.println("Total leads: " + leads.size());
    }
}
