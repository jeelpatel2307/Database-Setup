import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseSetup {
    public static void main(String[] args) {
        setupDatabase();
    }

    private static void setupDatabase() {
        String url = "jdbc:postgresql://localhost:5432/myapp";
        String username = "myappuser";
        String password = "myapppassword";

        try {
            Connection connection = getConnection(url, username, password);
            System.out.println("Connected to the database!");

            // Create tables, insert data, or perform other setup tasks here
            createTables(connection);
            insertSampleData(connection);

            connection.close();
            System.out.println("Database setup complete.");
        } catch (SQLException e) {
            System.err.println("Error setting up the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Connection getConnection(String url, String username, String password) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        return DriverManager.getConnection(url, props);
    }

    private static void createTables(Connection connection) throws SQLException {
        String usersTableSql = "CREATE TABLE IF NOT EXISTS users (" +
                               "id SERIAL PRIMARY KEY," +
                               "username VARCHAR(255) NOT NULL UNIQUE," +
                               "email VARCHAR(255) NOT NULL UNIQUE," +
                               "password VARCHAR(255) NOT NULL)";

        String postsTableSql = "CREATE TABLE IF NOT EXISTS posts (" +
                               "id SERIAL PRIMARY KEY," +
                               "title VARCHAR(255) NOT NULL," +
                               "content TEXT NOT NULL," +
                               "user_id INTEGER NOT NULL," +
                               "FOREIGN KEY (user_id) REFERENCES users(id))";

        connection.createStatement().executeUpdate(usersTableSql);
        connection.createStatement().executeUpdate(postsTableSql);
    }

    private static void insertSampleData(Connection connection) throws SQLException {
        String insertUsersSql = "INSERT INTO users (username, email, password) VALUES " +
                                "('john_doe', 'john.doe@example.com', 'password123')," +
                                "('jane_smith', 'jane.smith@example.com', 'password456')";

        String insertPostsSql = "INSERT INTO posts (title, content, user_id) VALUES " +
                                "('My First Post', 'This is the content of my first post.', 1)," +
                                "('Jane''s Blog Post', 'This is the content of Jane''s blog post.', 2)";

        connection.createStatement().executeUpdate(insertUsersSql);
        connection.createStatement().executeUpdate(insertPostsSql);
    }
}
