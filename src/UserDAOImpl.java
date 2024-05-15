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