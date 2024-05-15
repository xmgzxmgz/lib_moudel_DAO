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