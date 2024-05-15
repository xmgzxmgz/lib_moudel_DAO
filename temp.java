`User.java`：

        ```java
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
```

        `Book.java`：

        ```java
public class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
```

        `UserDAO.java`：

        ```java
public interface UserDAO {
    void addUser(User user);
    User getUser(String username, String password);
}
```

        `UserDAOImpl.java`：

        ```java
public class UserDAOImpl implements UserDAO {
    private DatabaseManager databaseManager;

    public UserDAOImpl() {
        this.databaseManager = new DatabaseManager();
    }

    @Override
    public void addUser(User user) {
        databaseManager.addUserToDatabase(user.getUsername(), user.getPassword());
        System.out.println("用户注册成功！");
    }

    @Override
    public User getUser(String username, String password) {
        return databaseManager.getUserFromDatabase(username, password);
    }
}
```

        `BookDAO.java`：

        ```java
        import java.util.List;

public interface BookDAO {
    void addBook(Book book);
    void deleteBook(String title);
    void updateBook(String title, String newTitle, String newAuthor);
    Book getBookByTitle(String title);
    List<Book> getAllBooks();
}
```

        `BookDAOImpl.java`：

        ```java
        import java.util.ArrayList;
        import java.util.List;

public class BookDAOImpl implements BookDAO {
    private DatabaseManager databaseManager;

    public BookDAOImpl() {
        this.databaseManager = new DatabaseManager();
    }

    @Override
    public void addBook(Book book) {
        databaseManager.addBookToDatabase(book.getTitle(), book.getAuthor());
        System.out.println("图书添加成功！");
    }

    @Override
    public void deleteBook(String title) {
        Book book = getBookByTitle(title);
        if (book != null) {
            databaseManager.deleteBookFromDatabase(title);
            System.out.println("图书删除成功！");
        } else {
            System.out.println("找不到指定的图书！");
        }
    }

    @Override
    public void updateBook(String title, String newTitle, String newAuthor) {
        Book book = getBookByTitle(title);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            databaseManager.updateBookInDatabase(book);
            System.out.println("图书信息修改成功！");
        } else {
            System.out.println("找不到指定的图书！");
        }
    }

    @Override
    public Book getBookByTitle(String title) {
        return databaseManager.getBookByTitle(title);
    }

    @Override
    public List<Book> getAllBooks() {
        return databaseManager.getAllBooks();
    }
}
```

        `DatabaseManager.java`：

        ```java
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
```

        `MenuManager.java`：

        ```java
        import java.util.Scanner;

public class MenuManager {
    private LibraryManagementSystem library;

    public MenuManager(LibraryManagementSystem library) {
        this.library = library;
    }

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("欢迎使用图书管理系统");
            System.out.println("1. 用户注册");
            System.out.println("2. 用户登录");
            System.out.println("3. 添加图书");
            System.out.println("4. 删除图书");
            System.out.println("5. 修改图书信息");
            System.out.println("6. 搜索图书");
            System.out.println("7. 显示所有图书");
            System.out.println("0. 退出系统");
            System.out.print("请输入选项：");
            choice = scanner.nextInt();
            scanner.nextLine(); // 读取换行符

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    addBook(scanner);
                    break;
                case 4:
                    deleteBook(scanner);
                    break;
                case 5:
                    updateBook(scanner);
                    break;
                case 6:
                    searchBook(scanner);
                    break;
                case 7:
                    displayAllBooks();
                    break;
                case 0:
                    System.out.println("谢谢使用，再见！");
                    break;
                default:
                    System.out.println("无效的选项，请重新输入！");
                    break;
            }
        } while (choice != 0);
    }

    private void registerUser(Scanner scanner) {
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();
        library.addUser(username, password);
    }

    private void loginUser(Scanner scanner) {
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();
        library.loginUser(username, password);
    }

    private void addBook(Scanner scanner) {
        System.out.print("请输入图书标题：");
        String title = scanner.nextLine();
        System.out.print("请输入图书作者：");
        String author = scanner.nextLine();
        library.addBook(title, author);
    }

    private void deleteBook(Scanner scanner) {
        System.out.print("请输入要删除的图书标题：");
        String title = scanner.nextLine();
        library.deleteBook(title);
    }

    private void updateBook(Scanner scanner) {
        System.out.print("请输入要修改的图书标题：");
        String title = scanner.nextLine();
        System.out.print("请输入新的图书标题：");
        String newTitle = scanner.nextLine();
        System.out.print("请输入新的图书作者：");
        String newAuthor = scanner.nextLine();
        library.updateBook(title, newTitle, newAuthor);
    }

    private void searchBook(Scanner scanner) {
        System.out.print("请输入要搜索的图书标题：");
        String title = scanner.nextLine();
        library.searchBook(title);
    }

    private void displayAllBooks() {
        System.out.println("所有图书列表：");
        for (Book book : library.getAllBooks()) {
            System.out.println("书名：" + book.getTitle() + ", 作者：" + book.getAuthor());
        }
    }
}
```

        `LibraryManagementSystem.java`：

        ```java
public class LibraryManagementSystem {
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private User loggedInUser;
    private MenuManager menuManager;

    public LibraryManagementSystem() {
        this.userDAO = new UserDAOImpl();
        this.bookDAO = new BookDAOImpl();
        this.loggedInUser = null;
        this.menuManager = new MenuManager(this);
        initializeDatabase();
    }

    private void initializeDatabase() {
        // 不再需要调用databaseManager.createBookTable()，因为表的创建已经在DAO中处理
    }

    public void addUser(String username, String password) {
        User newUser = new User(username, password);
        userDAO.addUser(newUser);
    }

    public User loginUser(String username, String password) {
        User user = userDAO.getUser(username, password);
        if (user != null) {
            System.out.println("登录成功！");
            return user;
        } else {
            System.out.println("用户名或密码错误！");
            return null;
        }
    }

    public void addBook(String title, String author) {
        Book newBook = new Book(title, author);
        bookDAO.addBook(newBook);
    }

    public void deleteBook(String title) {
        bookDAO.deleteBook(title);
    }

    public void updateBook(String title, String newTitle, String newAuthor) {
        bookDAO.updateBook(title, newTitle, newAuthor);
    }

    public void searchBook(String title) {
        Book book = bookDAO.getBookByTitle(title);
        if (book != null) {
            System.out.println("书名：" + book.getTitle() + ", 作者：" + book.getAuthor());
        } else {
            System.out.println("找不到指定的图书！");
        }
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public void mainMenu() {
        menuManager.mainMenu();
    }

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        library.mainMenu();
    }
}
```
