import java.sql.*;
import java.util.Scanner;

public class QuizApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String dbName = "quiz";
        String username = "postgres";
        String password = "God";

        // Step 1: Connect to an existing DB to create "quiz"
        String baseUrl = "jdbc:postgresql://localhost:5432/postgres";
        try (Connection conn = DriverManager.getConnection(baseUrl, username, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE " + dbName);
            System.out.println("Database created.");
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("Database already exists.");
            } else {
                e.printStackTrace();
                return;
            }
        }

        // Step 2: Connect to the newly created "quiz" database
        String quizUrl = "jdbc:postgresql://localhost:5432/" + dbName;

        try (Connection con = DriverManager.getConnection(quizUrl, username, password);
             Statement statement = con.createStatement()) {

            // Create questions table
            String createQuestionTable = """
                    CREATE TABLE IF NOT EXISTS questions (
                        id SERIAL PRIMARY KEY,
                        question TEXT,
                        option1 TEXT,
                        option2 TEXT,
                        option3 TEXT,
                        option4 TEXT,
                        answer INT
                    );
                    """;
            statement.executeUpdate(createQuestionTable);

            // Insert sample questions if table is empty
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM questions");
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                String insertSQL = "INSERT INTO questions (question, option1, option2, option3, option4, answer) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(insertSQL);

                preparedStatement.setString(1, "What is the size of int in Java?");
                preparedStatement.setString(2, "2 bytes");
                preparedStatement.setString(3, "4 bytes");
                preparedStatement.setString(4, "8 bytes");
                preparedStatement.setString(5, "Depends on OS");
                preparedStatement.setInt(6, 2);
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "Which keyword is used to inherit a class in Java?");
                preparedStatement.setString(2, "implement");
                preparedStatement.setString(3, "inherits");
                preparedStatement.setString(4, "extends");
                preparedStatement.setString(5, "super");
                preparedStatement.setInt(6, 3);
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "Which collection doesn't allow duplicates?");
                preparedStatement.setString(2, "List");
                preparedStatement.setString(3, "Set");
                preparedStatement.setString(4, "Map");
                preparedStatement.setString(5, "Queue");
                preparedStatement.setInt(6, 2);
                preparedStatement.executeUpdate();

                System.out.println("Questions inserted.");
            }

            // Create scores table
            String createScoresTable = """
                    CREATE TABLE IF NOT EXISTS scores (
                        id SERIAL PRIMARY KEY,
                        username TEXT,
                        score INT
                    );
                    """;
            statement.executeUpdate(createScoresTable);

            // Conduct quiz
            System.out.print("Enter your name: ");
            String user = scanner.nextLine();

            resultSet = statement.executeQuery("SELECT * FROM questions");
            int score = 0;

            while (resultSet.next()) {
                System.out.println("\nQ" + resultSet.getInt("id") + ": " + resultSet.getString("question"));
                System.out.println("1) " + resultSet.getString("option1"));
                System.out.println("2) " + resultSet.getString("option2"));
                System.out.println("3) " + resultSet.getString("option3"));
                System.out.println("4) " + resultSet.getString("option4"));
                System.out.print("Your answer (1-4): ");

                int answer = scanner.nextInt();
                if (answer == resultSet.getInt("answer")) {
                    score++;
                }
            }

            System.out.println("\n" + user + ", you scored: " + score);
            PreparedStatement ps = con.prepareStatement("INSERT INTO scores (username, score) VALUES (?, ?)");
            ps.setString(1, user);  // ✅ use the actual user's name
            ps.setInt(2, score);
            ps.executeUpdate();
            System.out.println("Score saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
