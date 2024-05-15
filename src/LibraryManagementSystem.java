import java.util.List;

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

    public List<Book> searchBook(String title) {
        Book book = bookDAO.getBookByTitle(title);
        if (book != null) {
            System.out.println("书名：" + book.getTitle() + ", 作者：" + book.getAuthor());
        } else {
            System.out.println("找不到指定的图书！");
        }
        return null;
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