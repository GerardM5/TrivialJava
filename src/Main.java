import java.util.*;

public class Main {

    public static final String GENERAL_CULTUR = "Culturilla";
    public static final String GEOGRAPY = "Geografía";
    public static final String FUNNY = "Diversion";
    public static final String LITERATUR_AND_CINEMA = "Arte";
    public static final int MAX_POINTS_FOR_WIN = 5;


    public static void main(String[] args) {

        HashMap<String, List<Question>> mapTopics = createMapTopics();
        int countCorrectAnswers = 0;
        int countTotalAnswers = 0;

        while (!hasWon(countCorrectAnswers) && hasRemainingQuestions(mapTopics)) {
            String topic = askForTopic();
            List<Question> questionsOfTopic = selectTopic(topic, mapTopics);//pasar lista preguntas
            boolean isCorrect = playQuestion(questionsOfTopic);
            countCorrectAnswers = sumIfIsCorrect(isCorrect, countCorrectAnswers);
            countTotalAnswers++;
            //countTotalAnswers = sumIfIsIncorrect(isCorrect, countTotalAnswers);//suma les correct

        }
        printFinalGame(countCorrectAnswers, countTotalAnswers);
    }

    private static int sumIfIsIncorrect(boolean isCorrect, int countIncorrectAnswers) {
        if (!isCorrect) countIncorrectAnswers++;
        return countIncorrectAnswers;
    }


    private static int sumIfIsCorrect(boolean isCorrect, int countCorrectAnswers) {
        if (isCorrect) countCorrectAnswers++;
        return countCorrectAnswers;
    }


    private static void printFinalGame(int countCorrectAnswers, int countTotalAnswers) {
        if (countCorrectAnswers >= MAX_POINTS_FOR_WIN) System.out.println("Has ganado");
        else System.out.println("Has perdido");

        System.out.println("Tu ratio de acierto es de: " + (countCorrectAnswers * 100 / (countTotalAnswers)) + "%");
    }

    private static boolean hasRemainingQuestions(HashMap<String, List<Question>> mapThemes) {
        for (List<Question> questions : mapThemes.values()) {
            for (Question question : questions) {
                return true;
            }
        }
        return false;
    }

    private static boolean playQuestion(List<Question> questionsOfTopic) {
        boolean isCorrect = false;

        if (hasQuestion(questionsOfTopic)) {
            printQuestion(questionsOfTopic.get(0));
            String answer = giveAnswer();
            isCorrect = isAnswerCorrect(answer, questionsOfTopic.get(0));
            printResult(isCorrect, questionsOfTopic.get(0));
            questionsOfTopic.remove(0);
        }

        return isCorrect;
    }

    private static boolean hasQuestion(List<Question> questionsOfTheme) {

        if (questionsOfTheme.isEmpty()) {
            System.out.println("No quedan preguntas de esta categoria, por favor escribe otra.");
            return false;
        }
        return true;
    }


    private static boolean hasWon(int countCorrectAnswer) {

        return countCorrectAnswer >= MAX_POINTS_FOR_WIN;
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

    private static boolean isAnswerCorrect(String answer, Question question) {

        return answer.equalsIgnoreCase(question.getAnswer());

    }

    private static void printResult(boolean isCorrect, Question question) {
        if (isCorrect) {
            System.out.println("Correcto");
        } else {
            System.out.println("A ver si estudiamos mas...");
            System.out.println("La respuesta correcta era: " + question.getAnswer());
        }
    }

    private static String giveAnswer() {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        return text;
    }

    private static void printQuestion(Question question) {
        System.out.println(question.getQuestion());
    }

    private static List<Question> createListQuestion(String theme) {

        List<Question> questions = new ArrayList<>();


        switch (theme) {
            case GEOGRAPY:
                questions.add(new Question("Donde nace el rio Ebro?:", "Fontibre", "Geografia"));
                questions.add(new Question("Que rio pasa por París?:", "Sena", "Geografia"));
                questions.add(new Question("Cual es la capital de Japón?:", "Tokio", "Geografia"));
                questions.add(new Question("Que rio pasa por Londres?", "Tamesis", "Geografia"));
                break;

            case GENERAL_CULTUR:
                questions.add(new Question("De que color es el caballo blanco de SAntiago?:", "Blanco", "Culturilla General"));
                questions.add(new Question("¿Quién escribió La Odisea?", "Homero", "Culturilla General"));
                questions.add(new Question("¿Qué tipo de animal es la ballena?", "Mamifero", "Culturilla General"));
                questions.add(new Question("¿Qué cantidad de huesos en el cuerpo humano adulto?", "206", "Culturilla General"));

                break;
            case FUNNY:
                questions.add(new Question("Cuantas caras tiene un dado?:", "6", "Diversion"));
                questions.add(new Question("Cuantas caras tiene el cubo de rubick?:", "6", "Diversion"));
                questions.add(new Question("¿Qué sube, pero nunca baja?:", "Edad", "Diversion"));
                questions.add(new Question("¿Qué entra duro pero sale blando y suave?:", "Chicle", "Diversion"));
                break;

            case LITERATUR_AND_CINEMA:
                questions.add(new Question("Quien escribió 100 años de soledad?:", "Garcia Marquez", "Literatura y cine"));
                questions.add(new Question("Quien dirigió el film Indiana Jones?:", "Steven Spilberg", "Literatura y cine"));
                questions.add(new Question("Quien escribió 100 años de soledad?:", "Garcia Marquez", "Literatura y cine"));
                questions.add(new Question("Quien dirigió el film Indiana Jones?:", "Steven Spilberg", "Literatura y cine"));
                break;


        }

        return questions;
    }
}
