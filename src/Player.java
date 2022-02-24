import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private int points;
    private List<Question> assignedQuestions;


    public Player(String name) {
        this.name = name;
        points = 0;
        assignedQuestions = new ArrayList<>();
    }

    public int getPoints() {
        points = assignedQuestions.stream()
                .mapToInt(question -> question.getIfIsCorrect() ? 1 : 0)
                .sum();
        return points;
    }

    public List<Question> getAssignedQuestions() {
        return assignedQuestions;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name +
                ", points=" + points +
                ", win rate = " + getWinRate() + "% " +
                '}';
    }

    private int getWinRate() {

        return points / assignedQuestions.size() * 100;


    }
}
