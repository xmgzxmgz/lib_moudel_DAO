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