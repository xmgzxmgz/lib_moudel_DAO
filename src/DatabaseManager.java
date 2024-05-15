import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/libdb";
    static final String USER = "root";
    static final String PASS = "xmgz5656";

    public DatabaseManager() {
        createConnection();
    }

    private void createConnection() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addUserToDatabase(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("添加用户到数据库失败：" + e.getMessage());
        }
    }

    public User getUserFromDatabase(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(username, password);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("登录失败：" + e.getMessage());
            return null;
        }
    }

    public void addBookToDatabase(String title, String author) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO book (title, author) VALUES (?, ?)")) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("添加图书失败：" + e.getMessage());
        }
    }

    public void deleteBookFromDatabase(String title) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM book WHERE title = ?")) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("删除图书失败：" + e.getMessage());
        }
    }

    public void updateBookInDatabase(Book book) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE book SET title = ?, author = ? WHERE title = ?")) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("更新图书信息失败：" + e.getMessage());
        }
    }

    public Book getBookByTitle(String title) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM book WHERE title = ?")) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String author = rs.getString("author");
                return new Book(title, author);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("查询图书失败：" + e.getMessage());
            return null;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM book")) {
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                books.add(new Book(title, author));
            }
        } catch (SQLException e) {
            System.out.println("获取图书列表失败：" + e.getMessage());
        }
        return books;
    }
}