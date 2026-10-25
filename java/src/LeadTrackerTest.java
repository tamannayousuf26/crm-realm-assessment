// no JUnit because third-party libraries are not allowed, so I made a small check() helper
public class LeadTrackerTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testPriorityBoundaries();
        testValidLeadIsAdded();
        testInvalidInputIsRejected();
        testEmptyTracker();
        testListCannotBeChangedFromOutside();

        System.out.println();
        System.out.println("Passed: " + passed + ", Failed: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testPriorityBoundaries() {
        check("score 0 is COLD", Lead.calculatePriority(0).equals("COLD"));
        check("score 49 is COLD", Lead.calculatePriority(49).equals("COLD"));
        check("score 50 is WARM", Lead.calculatePriority(50).equals("WARM"));
        check("score 79 is WARM", Lead.calculatePriority(79).equals("WARM"));
        check("score 80 is HOT", Lead.calculatePriority(80).equals("HOT"));
        check("score 100 is HOT", Lead.calculatePriority(100).equals("HOT"));
    }

    private static void testValidLeadIsAdded() {
        LeadTracker tracker = new LeadTracker();
        String error = tracker.addLead("  Alice Rahman ", " alice@example.com ", " 85 ");

        check("valid lead returns no error", error == null);
        check("valid lead is stored", tracker.getAllLeads().size() == 1);

        Lead lead = tracker.getAllLeads().get(0);
        check("name is trimmed", lead.getName().equals("Alice Rahman"));
        check("email is trimmed", lead.getEmail().equals("alice@example.com"));
        check("priority comes from score", lead.getPriority().equals("HOT"));
    }

    private static void testInvalidInputIsRejected() {
        LeadTracker tracker = new LeadTracker();

        check("empty name rejected", tracker.addLead("", "a@b.com", "50") != null);
        check("blank name rejected", tracker.addLead("   ", "a@b.com", "50") != null);
        check("empty email rejected", tracker.addLead("Bob", "", "50") != null);
        check("score -1 rejected", tracker.addLead("Bob", "a@b.com", "-1") != null);
        check("score 101 rejected", tracker.addLead("Bob", "a@b.com", "101") != null);
        check("text score rejected", tracker.addLead("Bob", "a@b.com", "abc") != null);
        check("decimal score rejected", tracker.addLead("Bob", "a@b.com", "50.5") != null);
        check("empty score rejected", tracker.addLead("Bob", "a@b.com", "") != null);
        check("nothing was added", tracker.isEmpty());

        check("score 0 accepted", tracker.addLead("Min", "min@b.com", "0") == null);
        check("score 100 accepted", tracker.addLead("Max", "max@b.com", "100") == null);
    }

    private static void testEmptyTracker() {
        LeadTracker tracker = new LeadTracker();
        check("new tracker is empty", tracker.isEmpty());
        check("new tracker has no leads", tracker.getAllLeads().isEmpty());
    }

    private static void testListCannotBeChangedFromOutside() {
        LeadTracker tracker = new LeadTracker();
        tracker.addLead("Alice", "alice@example.com", "60");
        tracker.getAllLeads().clear();
        check("clearing the returned list does not delete leads", tracker.getAllLeads().size() == 1);
    }

    private static void check(String description, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + description);
        } else {
            failed++;
            System.out.println("FAIL: " + description);
        }
    }
}
