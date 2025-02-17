import java.util.*;
import java.util.stream.Collectors;

class Question {
    private static int idCounter = 1;
    private int id;
    private String questionText;
    private List<Answer> answers;
    private boolean resolved;  // Flag to indicate if the question is resolved

    // Constructor to initialize the question with a text
    public Question(String questionText) {
        this.id = idCounter++; // Assign a unique ID to each new question
        this.questionText = questionText;
        this.answers = new ArrayList<>();
        this.resolved = false; // Initially, the question is not resolved
    }

    // Getter for the question ID
    public int getId() {
        return id;
    }

    // Getter for the question text
    public String getQuestionText() {
        return questionText;
    }

    // Getter for the list of answers associated with the question
    public List<Answer> getAnswers() {
        return answers;
    }

    // Getter to check if the question is resolved
    public boolean isResolved() {
        return resolved;
    }

    // Mark the question as resolved
    public void resolve() {
        this.resolved = true;
    }

    // Add a new answer to the question
    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    // String representation of the question with its resolved status and answers
    @Override
    public String toString() {
        return "Q" + id + ": " + questionText + " [" +
                (resolved ? "Resolved" : "Unresolved") + ", " +
                (answers.isEmpty() ? "Unanswered" : "Answered") + "]";
    }
}

class Answer {
    private static int idCounter = 1;
    private int id;
    private String answerText;

    // Constructor to initialize the answer with its text
    public Answer(String answerText) {
        this.id = idCounter++; // Assign a unique ID to each new answer
        this.answerText = answerText;
    }

    // Getter for the answer ID
    public int getId() {
        return id;
    }

    // Getter for the answer text
    public String getAnswerText() {
        return answerText;
    }

    // String representation of the answer
    @Override
    public String toString() {
        return "A" + id + ": " + answerText;
    }
}

class QuestionsCollection {
    private List<Question> questions;

    // Constructor to initialize the questions list
    public QuestionsCollection() {
        questions = new ArrayList<>();
    }

    // Add a new question to the collection
    public void addQuestion(String questionText) {
        if (isValidInput(questionText)) {
            questions.add(new Question(questionText)); // Add the new question to the list
            System.out.println("Question added successfully.");
        } else {
            System.out.println("Please input a valid question and make it longer than 10 characters.");
        }
    }

    // Getter for all questions
    public List<Question> getAllQuestions() {
        return questions;
    }

    // Remove a question based on its ID
    public void removeQuestion(int questionId) {
        questions.removeIf(q -> q.getId() == questionId); // Remove the question by its ID
        System.out.println("Question " + questionId + " removed.");
    }

    // Helper method to check if input is valid (at least 10 characters)
    private boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty() && input.length() >= 10;
    }
}

class AnswersCollection {
    private Map<Integer, List<Answer>> answersByQuestion;

    // Constructor to initialize the answers map
    public AnswersCollection() {
        answersByQuestion = new HashMap<>();
    }

    // Add an answer to a specific question
    public void addAnswer(int questionId, String answerText) {
        if (isValidInput(answerText)) {
            Answer newAnswer = new Answer(answerText);
            // Find the question by ID and add the answer to it
            for (Question question : StudentQuestionAnswerSystem.questionsCollection.getAllQuestions()) {
                if (question.getId() == questionId) {
                    question.addAnswer(newAnswer);
                    System.out.println("Answer added successfully.");
                    return;
                }
            }
            System.out.println("Question not found.");
        } else {
            System.out.println("Please input a valid answer and make it longer than 10 characters.");
        }
    }

    // Retrieve all answers for a specific question
    public List<Answer> getAnswersForQuestion(int questionId) {
        for (Question question : StudentQuestionAnswerSystem.questionsCollection.getAllQuestions()) {
            if (question.getId() == questionId) {
                return question.getAnswers(); // Return the answers for the found question
            }
        }
        return Collections.emptyList(); // Return an empty list if no answers are found
    }

    // Remove an answer based on its ID from a specific question
    public void removeAnswer(int questionId, int answerId) {
        List<Answer> answers = getAnswersForQuestion(questionId); // Get all answers for the question
        if (answers != null) {
            answers.removeIf(a -> a.getId() == answerId); // Remove the specific answer by its ID
            System.out.println("Answer " + answerId + " removed.");
        } else {
            System.out.println("Answer not found.");
        }
    }

    // Helper method to check if input is valid (at least 10 characters)
    private boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty() && input.length() >= 10;
    }
}

public class StudentQuestionAnswerSystem {
    static QuestionsCollection questionsCollection = new QuestionsCollection(); // Collection of all questions
    static AnswersCollection answersCollection = new AnswersCollection(); // Collection of answers for questions
    private static final Scanner scanner = new Scanner(System.in); // Scanner object to read input

    public static void main(String[] args) {
        int choice;
        do {
            showMenu(); // Display the menu options
            choice = getUserChoice(); // Get the user's choice

            switch (choice) {
                case 1:
                    askQuestion(); // Ask a question
                    break;
                case 2:
                    searchQuestions(); // Search for questions
                    break;
                case 3:
                    displayQuestions(); // Display all questions
                    break;
                case 4:
                    removeQuestion(); // Remove a question
                    break;
                case 5:
                    answerQuestion(); // Answer a question
                    break;
                case 6:
                    removeAnswer(); // Remove an answer
                    break;
                case 7:
                    resolveQuestion(); // Resolve a question
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!"); // Exit the system
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid choices
            }
        } while (choice != 0); // Repeat until the user chooses to exit
    }

    // Show the main menu options
    private static void showMenu() {
        System.out.println("\n--- Question and Answer System ---");
        System.out.println("1. Ask a question");
        System.out.println("2. Search questions");
        System.out.println("3. Display all questions");
        System.out.println("4. Remove a question");
        System.out.println("5. Answer a question");
        System.out.println("6. Remove an answer");
        System.out.println("7. Resolve a question");
        System.out.println("0. Exit");
    }

    // Get a valid user choice (number)
    private static int getUserChoice() {
        int choice = -1;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input); // Try to parse the choice as an integer
                validChoice = true; // If successful, mark the choice as valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice number. Try again."); // Handle invalid input
            }
        }

        return choice; // Return the valid choice
    }

    // Ask a new question and add it to the system
    private static void askQuestion() {
        System.out.print("Enter your question (minimum 10 characters): ");
        String questionText = scanner.nextLine();
        questionsCollection.addQuestion(questionText); // Add the question to the collection
    }

    // Search for questions that contain a given search term
    private static void searchQuestions() {
        System.out.print("Enter a search term: ");
        String searchTerm = scanner.nextLine();
        List<Question> foundQuestions = questionsCollection.getAllQuestions().stream()
                .filter(q -> q.getQuestionText().toLowerCase().contains(searchTerm.toLowerCase())) // Filter questions by search term
                .collect(Collectors.toList());
        displayQuestionsWithAnswers(foundQuestions); // Display the found questions
    }

    // Display all questions with their answers and status (resolved/unresolved)
    private static void displayQuestions() {
        List<Question> allQuestions = questionsCollection.getAllQuestions(); // Get all questions
        displayQuestionsWithAnswers(allQuestions); // Display all questions with answers
    }

    // Display a list of questions with their status and answers
    private static void displayQuestionsWithAnswers(List<Question> questions) {
        if (questions.isEmpty()) {
            System.out.println("No questions available."); // If no questions, display a message
            return;
        }

        questions.forEach(q -> {
            System.out.println(q); // Print each question's details
            List<Answer> answers = q.getAnswers(); // Get answers for the question
            if (!answers.isEmpty()) {
                answers.forEach(a -> System.out.println("    " + a)); // Print each answer for the question
            }
        });
    }

    // Remove a question from the system based on its ID
    private static void removeQuestion() {
        displayQuestions(); // Display all questions
        int questionId = getValidIdInput("Enter the question ID to remove: "); // Get the question ID
        questionsCollection.removeQuestion(questionId); // Remove the question by ID
    }

    // Answer a question based on its ID
    private static void answerQuestion() {
        displayQuestions(); // Display all questions
        int questionId = getValidIdInput("Enter the question ID to answer: "); // Get the question ID
        System.out.print("Enter your answer (minimum 10 characters): ");
        String answerText = scanner.nextLine(); // Get the answer text
        answersCollection.addAnswer(questionId, answerText); // Add the answer to the question
    }

    // Remove an answer from a specific question based on answer ID
    private static void removeAnswer() {
        displayQuestions(); // Display all questions
        int questionId = getValidIdInput("Enter the question ID for the answer: "); // Get the question ID
        int answerId = getValidIdInput("Enter the answer ID to remove: "); // Get the answer ID
        answersCollection.removeAnswer(questionId, answerId); // Remove the answer
    }

    // Resolve a question by marking it as resolved
    private static void resolveQuestion() {
        displayQuestions(); // Display all questions
        int questionId = getValidIdInput("Enter the question ID to resolve: "); // Get the question ID
        for (Question question : questionsCollection.getAllQuestions()) {
            if (question.getId() == questionId) { // If the question is found
                if (question.isResolved()) {
                    System.out.println("This question is already resolved.");
                } else {
                    question.resolve(); // Mark the question as resolved
                    System.out.println("Question " + questionId + " marked as resolved.");
                }
                return;
            }
        }
        System.out.println("Question not found."); // If the question was not found
    }

    // Helper method for validating and obtaining numeric ID input
    private static int getValidIdInput(String prompt) {
        int id = -1;
        boolean validId = false;
        while (!validId) {
            System.out.print(prompt); // Prompt the user for an ID
            String input = scanner.nextLine();
            try {
                id = Integer.parseInt(input); // Try to parse the input as an integer
                validId = true; // If successful, mark the ID as valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID. Please enter a valid number."); // Handle invalid input
            }
        }
        return id; // Return the valid ID
    }
}
