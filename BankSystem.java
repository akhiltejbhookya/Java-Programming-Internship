import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankSystem {
    private Map<String, User> users;
    private static final String DATA_FILE = "bank_data.ser";

    public BankSystem() {
        users = new HashMap<>();
        loadData();
    }

    public void createUser(String username, String password, String accountNumber, String accountHolderName, double initialBalance) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
            return;
        }
        Account account = new Account(accountNumber, accountHolderName, initialBalance);
        User user = new User(username, password, account);
        users.put(username, user);
        saveData();
        System.out.println("User created successfully.");
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            return user;
        }
        return null;
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found. Starting with empty data.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Enter account holder name: ");
                    String accountHolderName = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    bankSystem.createUser(username, password, accountNumber, accountHolderName, initialBalance);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    User user = bankSystem.login(username, password);
                    if (user != null) {
                        System.out.println("Login successful.");
                        userMenu(scanner, user, bankSystem);
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting.....!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu(Scanner scanner, User user, BankSystem bankSystem) {
        while (true) {
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Pay Bill");
            System.out.println("6. View Transaction History");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Current balance: " + user.getAccount().getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    user.getAccount().deposit(depositAmount);
                    System.out.println("Amount deposited successfully.");
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    if (user.getAccount().withdraw(withdrawAmount)) {
                        System.out.println("Amount withdrawn successfully.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 4:
                    System.out.print("Enter account number to transfer to: ");
                    String toAccountNumber = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    Account toAccount = getAccountByNumber(toAccountNumber, bankSystem);
                    if (toAccount != null) {
                        if (user.getAccount().transfer(toAccount, transferAmount)) {
                            System.out.println("Amount transferred successfully.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter biller name: ");
                    String biller = scanner.nextLine();
                    System.out.print("Enter amount to pay: ");
                    double billAmount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    user.getAccount().payBill(biller, billAmount);
                    System.out.println("Bill paid successfully.");
                    break;
                case 6:
                    System.out.println("Transaction History:");
                    for (String transaction : user.getAccount().getTransactionHistory()) {
                        System.out.println(transaction);
                    }
                    break;
                case 7:
                    System.out.println("Logging out.....!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Account getAccountByNumber(String accountNumber, BankSystem bankSystem) {
        for (User user : bankSystem.users.values()) {
            if (user.getAccount().getAccountNumber().equals(accountNumber)) {
                return user.getAccount();
            }
        }
        return null;
    }
}
