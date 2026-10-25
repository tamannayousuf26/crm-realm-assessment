public class Lead {

    private final String name;
    private final String email;
    private final int score;

    public Lead(String name, String email, int score) {
        this.name = name;
        this.email = email;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getScore() {
        return score;
    }

    public String getPriority() {
        return calculatePriority(score);
    }

    public static String calculatePriority(int score) {
        if (score >= 80) {
            return "HOT";
        } else if (score >= 50) {
            return "WARM";
        } else {
            return "COLD";
        }
    }

    @Override
    public String toString() {
        return name + " | " + email + " | score: " + score + " | priority: " + getPriority();
    }
}
