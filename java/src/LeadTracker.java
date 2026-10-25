import java.util.ArrayList;
import java.util.List;

public class LeadTracker {

    private final List<Lead> leads = new ArrayList<>();

    // returns an error message, or null if everything is ok
    public String validate(String name, String email, String scoreText) {
        if (name == null || name.trim().isEmpty()) {
            return "Name cannot be empty.";
        }
        if (email == null || email.trim().isEmpty()) {
            return "Email cannot be empty.";
        }
        if (scoreText == null || scoreText.trim().isEmpty()) {
            return "Score cannot be empty.";
        }

        int score;
        try {
            score = Integer.parseInt(scoreText.trim());
        } catch (NumberFormatException e) {
            return "Score must be a whole number between 0 and 100.";
        }

        if (score < 0 || score > 100) {
            return "Score must be between 0 and 100 (you entered " + score + ").";
        }
        return null;
    }

    public String addLead(String name, String email, String scoreText) {
        String error = validate(name, email, scoreText);
        if (error != null) {
            return error;
        }

        int score = Integer.parseInt(scoreText.trim());
        leads.add(new Lead(name.trim(), email.trim(), score));
        return null;
    }

    // return a copy so nobody can add leads from outside without validation
    public List<Lead> getAllLeads() {
        return new ArrayList<>(leads);
    }

    public boolean isEmpty() {
        return leads.isEmpty();
    }
}
