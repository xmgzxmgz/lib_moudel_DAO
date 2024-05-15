public interface UserDAO {
    void addUser(User user);
    User getUser(String username, String password);
}