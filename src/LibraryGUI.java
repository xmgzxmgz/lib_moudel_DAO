import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI extends JFrame {
    private LibraryManagementSystem library;

    public LibraryGUI(LibraryManagementSystem library) {
        this.library = library;
        initialize();
    }

    private void initialize() {
        setTitle("图书管理系统");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        // 添加各个面板
        JPanel loginPanel = createLoginPanel();
        JPanel mainMenuPanel = createMainMenuPanel();
        JPanel registerPanel = createRegisterPanel();
        JPanel addBookPanel = createAddBookPanel();
        JPanel deleteBookPanel = createDeleteBookPanel();
        JPanel updateBookPanel = createUpdateBookPanel();
        JPanel searchBookPanel = createSearchBookPanel();
        JPanel displayBooksPanel = createDisplayBooksPanel();

        // 添加面板到卡片布局
        add(loginPanel, "Login");
        add(mainMenuPanel, "MainMenu");
        add(registerPanel, "Register");
        add(addBookPanel, "AddBook");
        add(deleteBookPanel, "DeleteBook");
        add(updateBookPanel, "UpdateBook");
        add(searchBookPanel, "SearchBook");
        add(displayBooksPanel, "DisplayBooks");

        // 显示登录面板
        showCard("Login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel userLabel = new JLabel("用户名:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("密码:");
        JPasswordField passwordText = new JPasswordField();
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            User user = library.loginUser(username, password);
            if (user != null) {
                showCard("MainMenu");
            } else {
                JOptionPane.showMessageDialog(this, "登录失败！");
            }
        });

        registerButton.addActionListener(e -> showCard("Register"));

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);
        panel.add(registerButton);

        return panel;
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 1));
        JButton addButton = new JButton("添加图书");
        JButton deleteButton = new JButton("删除图书");
        JButton updateButton = new JButton("更新图书");
        JButton searchButton = new JButton("搜索图书");
        JButton displayButton = new JButton("显示所有图书");
        JButton logoutButton = new JButton("退出登录");

        addButton.addActionListener(e -> showCard("AddBook"));
        deleteButton.addActionListener(e -> showCard("DeleteBook"));
        updateButton.addActionListener(e -> showCard("UpdateBook"));
        searchButton.addActionListener(e -> showCard("SearchBook"));
        displayButton.addActionListener(e -> showCard("DisplayBooks"));
        logoutButton.addActionListener(e -> showCard("Login"));

        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(searchButton);
        panel.add(displayButton);
        panel.add(logoutButton);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel userLabel = new JLabel("用户名:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("密码:");
        JPasswordField passwordText = new JPasswordField();
        JButton registerButton = new JButton("注册");
        JButton backButton = new JButton("返回");

        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            library.addUser(username, password);
            JOptionPane.showMessageDialog(this, "注册成功！");
            showCard("Login");
        });

        backButton.addActionListener(e -> showCard("Login"));

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(registerButton);
        panel.add(backButton);

        return panel;
    }

    private JPanel createAddBookPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel titleLabel = new JLabel("书名:");
        JTextField titleText = new JTextField();
        JLabel authorLabel = new JLabel("作者:");
        JTextField authorText = new JTextField();
        JButton addButton = new JButton("添加");
        JButton backButton = new JButton("返回");

        addButton.addActionListener(e -> {
            String title = titleText.getText();
            String author = authorText.getText();
            library.addBook(title, author);
            JOptionPane.showMessageDialog(this, "图书添加成功！");
            showCard("MainMenu");
        });

        backButton.addActionListener(e -> showCard("MainMenu"));

        panel.add(titleLabel);
        panel.add(titleText);
        panel.add(authorLabel);
        panel.add(authorText);
        panel.add(addButton);
        panel.add(backButton);

        return panel;
    }

    private JPanel createDeleteBookPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JLabel titleLabel = new JLabel("书名:");
        JTextField titleText = new JTextField();
        JButton deleteButton = new JButton("删除");
        JButton backButton = new JButton("返回");

        deleteButton.addActionListener(e -> {
            String title = titleText.getText();
            library.deleteBook(title);
            JOptionPane.showMessageDialog(this, "图书删除成功！");
            showCard("MainMenu");
        });

        backButton.addActionListener(e -> showCard("MainMenu"));

        panel.add(titleLabel);
        panel.add(titleText);
        panel.add(deleteButton);
        panel.add(backButton);

        return panel;
    }

    private JPanel createUpdateBookPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel titleLabel = new JLabel("原书名:");
        JTextField titleText = new JTextField();
        JLabel newTitleLabel = new JLabel("新书名:");
        JTextField newTitleText = new JTextField();
        JLabel authorLabel = new JLabel("新作者:");
        JTextField authorText = new JTextField();
        JButton updateButton = new JButton("更新");
        JButton backButton = new JButton("返回");

        updateButton.addActionListener(e -> {
            String title = titleText.getText();
            String newTitle = newTitleText.getText();
            String newAuthor = authorText.getText();
            library.updateBook(title, newTitle, newAuthor);
            JOptionPane.showMessageDialog(this, "图书信息更新成功！");
            showCard("MainMenu");
        });

        backButton.addActionListener(e -> showCard("MainMenu"));

        panel.add(titleLabel);
        panel.add(titleText);
        panel.add(newTitleLabel);
        panel.add(newTitleText);
        panel.add(authorLabel);
        panel.add(authorText);
        panel.add(updateButton);
        panel.add(backButton);

        return panel;
    }

    private JPanel createSearchBookPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JLabel titleLabel = new JLabel("书名:");
        JTextField titleText = new JTextField();
        JButton searchButton = new JButton("搜索");
        JButton backButton = new JButton("返回");

        searchButton.addActionListener(e -> {
            String title = titleText.getText();
            Book book = (Book) library.searchBook(title);
            if (book != null) {
                JOptionPane.showMessageDialog(this, "书名：" + book.getTitle() + ", 作者：" + book.getAuthor());
            } else {
                JOptionPane.showMessageDialog(this, "找不到指定的图书！");
            }
        });

        backButton.addActionListener(e -> showCard("MainMenu"));

        panel.add(titleLabel);
        panel.add(titleText);
        panel.add(searchButton);
        panel.add(backButton);

        return panel;
    }

    private JPanel createDisplayBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JButton backButton = new JButton("返回");

        backButton.addActionListener(e -> showCard("MainMenu"));

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        // 显示所有图书信息
        java.util.List<Book> books = library.getAllBooks(); // 修改这里，使用java.util.List<Book>
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append("书名：").append(book.getTitle()).append(", 作者：").append(book.getAuthor()).append("\n");
        }
        textArea.setText(sb.toString());

        return panel;
    }

    private void showCard(String card) {
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), card);
    }

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        SwingUtilities.invokeLater(() -> {
            LibraryGUI gui = new LibraryGUI(library);
            gui.setVisible(true);
        });
    }
}
