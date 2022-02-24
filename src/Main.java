import java.util.*;

public class Main {

    public static final String GENERAL_CULTUR = "Culturilla";
    public static final String GEOGRAPY = "Geografía";
    public static final String FUNNY = "Diversion";
    public static final String LITERATUR_AND_CINEMA = "Arte";
    public static final int MAX_POINTS_FOR_WIN = 5;


    public static void main(String[] args) {

        HashMap<String, List<Question>> mapTopics = createMapTopics();

        List<Player> players = createPlayers();
        int currentPlayer = 0;


        while (!someoneHasWon(players) && hasRemainingQuestions(mapTopics)) {

            String topic = askForTopic();
            List<Question> questionsOfTopic = selectTopic(topic, mapTopics);//pasar lista preguntas
            playQuestion(questionsOfTopic, players, currentPlayer);
            currentPlayer = nextPlayerTurn(players, currentPlayer);
        }


        printFinalGame(players);
    }

    private static int nextPlayerTurn(List<Player> players, int currentPlayer) {
        currentPlayer = (currentPlayer + 1) % players.size();
        return currentPlayer;
    }


    private static List<Player> createPlayers() {

        List<Player> players = new ArrayList<>();

        players.add(new Player("Juan"));
        players.add(new Player("Pepe"));

        return players;
    }


    private static void printFinalGame(List<Player> players) {
        players = (ArrayList<Player>) players.stream()
                .sorted(Comparator.comparing(Player::getPoints).reversed());
        System.out.println("Ha ganado " + players.get(0).getName());

        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i).toString());

        }

    }

    private static boolean hasRemainingQuestions(HashMap<String, List<Question>> mapThemes) {
        for (List<Question> questions : mapThemes.values()) {
            for (Question question : questions) {
                return true;
            }
        }
        return false;
    }

    private static void playQuestion(List<Question> questionsOfTopic, List<Player> players, int currentPlayer) {

        if (hasQuestion(questionsOfTopic)) {
            printQuestion(questionsOfTopic.get(0));
            String answer = giveAnswer();
            questionsOfTopic.get(0).setIfIsCorrect(isAnswerCorrect(answer, questionsOfTopic.get(0).getAnswer()));
            printResult(questionsOfTopic.get(0));
            players.get(currentPlayer).getAssignedQuestions().add(questionsOfTopic.get(0));
            questionsOfTopic.remove(0);
        }

    }

    private static boolean hasQuestion(List<Question> questionsOfTheme) {

        if (questionsOfTheme.isEmpty()) {
            System.out.println("No quedan preguntas de esta categoria, por favor escribe otra.");
            return false;
        }
        return true;
    }


    private static boolean someoneHasWon(List<Player> players) {

        return players.stream()
                .mapToInt(Player::getPoints)
                .sum() >= MAX_POINTS_FOR_WIN;
    }


    private static String askForTopic() {
        System.out.println("Dime un tema entre estos: " + GENERAL_CULTUR + " " + GEOGRAPY + " " + FUNNY + " " + LITERATUR_AND_CINEMA);
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    private static HashMap<String, List<Question>> createMapTopics() {

        HashMap<String, List<Question>> mapThemes = new HashMap<>();

        List<Question> geoQuestions = createListQuestion(GEOGRAPY);
        mapThemes.put(GEOGRAPY, geoQuestions);

        List<Question> culturQuestions = createListQuestion(GENERAL_CULTUR);
        mapThemes.put(GENERAL_CULTUR, culturQuestions);

        List<Question> funQuestions = createListQuestion(FUNNY);
        mapThemes.put(FUNNY, funQuestions);

        List<Question> literatureFilmsQuestions = createListQuestion(LITERATUR_AND_CINEMA);
        mapThemes.put(LITERATUR_AND_CINEMA, literatureFilmsQuestions);


        return mapThemes;

    }

    private static List<Question> selectTopic(String theme, HashMap<String, List<Question>> questions) {

        List<Question> questionsOfTheme = questions.get(theme);
        Collections.shuffle(questionsOfTheme);
        return questionsOfTheme;

    }

    private static boolean isAnswerCorrect(String answer, String answerQuestion) {

        return answer.equalsIgnoreCase(answerQuestion);

    }

    private static void printResult(Question question) {

        if (question.getIfIsCorrect()) {
            System.out.println("Correcto");
        } else {
            System.out.println("A ver si estudiamos mas...");
            System.out.println("La respuesta correcta era: " + question.getAnswer());
        }
    }

    private static String giveAnswer() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private static void printQuestion(Question question) {
        System.out.println(question.getQuestion());
    }

    private static List<Question> createListQuestion(String theme) {

        List<Question> questions = new ArrayList<>();


        switch (theme) {
            case GEOGRAPY -> {
                questions.add(new Question("Donde nace el rio Ebro?:", "Fontibre", "Geografia"));
                questions.add(new Question("Que rio pasa por París?:", "Sena", "Geografia"));
                questions.add(new Question("Cual es la capital de Japón?:", "Tokio", "Geografia"));
                questions.add(new Question("Que rio pasa por Londres?", "Tamesis", "Geografia"));
            }
            case GENERAL_CULTUR -> {
                questions.add(new Question("De que color es el caballo blanco de SAntiago?:", "Blanco", "Culturilla General"));
                questions.add(new Question("¿Quién escribió La Odisea?", "Homero", "Culturilla General"));
                questions.add(new Question("¿Qué tipo de animal es la ballena?", "Mamifero", "Culturilla General"));
                questions.add(new Question("¿Qué cantidad de huesos en el cuerpo humano adulto?", "206", "Culturilla General"));
            }
            case FUNNY -> {
                questions.add(new Question("Cuantas caras tiene un dado?:", "6", "Diversion"));
                questions.add(new Question("Cuantas caras tiene el cubo de rubick?:", "6", "Diversion"));
                questions.add(new Question("¿Qué sube, pero nunca baja?:", "Edad", "Diversion"));
                questions.add(new Question("¿Qué entra duro pero sale blando y suave?:", "Chicle", "Diversion"));
            }
            case LITERATUR_AND_CINEMA -> {
                questions.add(new Question("Quien escribió 100 años de soledad?:", "Garcia Marquez", "Literatura y cine"));
                questions.add(new Question("Quien dirigió el film Indiana Jones?:", "Steven Spilberg", "Literatura y cine"));
                questions.add(new Question("Quien escribió 100 años de soledad?:", "Garcia Marquez", "Literatura y cine"));
                questions.add(new Question("Quien dirigió el film Indiana Jones?:", "Steven Spilberg", "Literatura y cine"));
            }
        }

        return questions;
    }
}
