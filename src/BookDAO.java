import java.util.List;

public interface BookDAO {
    void addBook(Book book);
    void deleteBook(String title);
    void updateBook(String title, String newTitle, String newAuthor);
    Book getBookByTitle(String title);
    List<Book> getAllBooks();
}