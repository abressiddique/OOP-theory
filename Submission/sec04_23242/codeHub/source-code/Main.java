import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Main {
    public static void main(String[] args) throws IOException{

        Report report = new Report(null); // rate set to be 1.0
        Vector<Category> categories = new Vector<>(); // Store category
        Scanner scanner = new Scanner(System.in);
        Scanner inp = new Scanner(new File("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\bank.txt"));
        Scanner acc = new Scanner(new File("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\Account.txt"));
        Scanner bud = new Scanner(new File("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\Budjet.txt"));
        Scanner trans = new Scanner(new File("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\Transaction.txt"));
        Scanner sav = new Scanner(new File("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\Saving.txt"));
        categories.add(new ShoppingCategory(1));    
        categories.add(new FoodCategory(2));
        categories.add(new OtherCategory(3));
    
        Bank bank1 = null;
        Users user1 = null;

                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\bank.txt"));
            if (br.readLine() == null) {
                System.out.println("Errors no Bank, and file empty");
                System.out.print("Enter Bank name: ");
                String bname = scanner.nextLine();
                System.out.print("Enter Your ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter Your Name: ");
                String name = scanner.nextLine();
                bank1 = new Bank(bname);
                user1 = new Users(id, name);
                report.setUser(user1);

                try (FileWriter writer = new FileWriter("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\bank.txt", false)) {
                    writer.write(bank1.getName() + " " + user1.getId() + " " + user1.getName() + "\n");
                }
            }
            br.close();



        while (inp.hasNextLine()) {
            String line = inp.nextLine();
            String[] parts = line.split(" ");
            if (parts.length == 3) {
                String bankName = parts[0];
                int id = Integer.parseInt(parts[1]);
                String name = parts[2];
                bank1 = new Bank(bankName);
                user1 = new Users(id, name);
                report.setUser(user1);
            }
        }
        while (acc.hasNextLine()) {
            String line = acc.nextLine();
            String[] parts = line.split(" ");
            int idAcc = Integer.parseInt(parts[0]);
            String name = parts[1];
            double balance = Double.parseDouble(parts[2]);
            Account acc1 = new Account(user1.getAccounts().size()+1, name, balance);
            user1.addAccount(acc1);
            user1.getAccounts().get(idAcc).addBank(bank1);
        }

        while (bud.hasNextLine()) {
            String line = bud.nextLine();
            String[] parts = line.split(" ");
            int id = Integer.parseInt(parts[0]);
            double limit = Double.parseDouble(parts[1]);
            int catID = Integer.parseInt(parts[2]);
            if(catID == 1){
                user1.getAccounts().get(id).addBudget(limit, categories.elementAt(0));
            }
            else if(catID == 2){
                user1.getAccounts().get(id).addBudget(limit, categories.elementAt(1));
            }
        }

        while (trans.hasNextLine()) {
            String line = trans.nextLine();
            String[] parts = line.split(" ");
            int uid = Integer.parseInt(parts[0]);
            int id = Integer.parseInt(parts[1]);
            String des = parts[2];
            String date = parts[3];
            Date d = Date.valueOf(date);
            double amount = Double.parseDouble(parts[4]);
            int catID = Integer.parseInt(parts[5]);
            if(catID == 1){
                user1.getAccounts().get(uid).addTransaction(id, des,amount ,d, categories.elementAt(0));

            }
            else if(catID == 2){
                user1.getAccounts().get(uid).addTransaction(id, des,amount ,d, categories.elementAt(1));

            }
        }

        while (sav.hasNextLine()) {
            String line = sav.nextLine();
            String[] parts = line.split(" ");
            int ID = Integer.parseInt(parts[0]);
            String Name = parts[1];
            double target = Double.parseDouble(parts[2]);
            double current = Double.parseDouble(parts[3]);
            String date = parts[4];
            Date d = Date.valueOf(date);
            Saving s = new Saving(ID, Name, target, current, d);
            user1.getAccounts().get(ID).addSaving(s);
        }

        inp.close();
        acc.close();
        bud.close();
        trans.close();
        sav.close();

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    screen.ClearScreen();
                    addAccount(scanner, bank1, user1);
                    scanner.nextLine();
                    screen.pauseScreen(scanner);
                    break;
                case 2:
                    screen.ClearScreen();
                    depositMoney(scanner, user1);
                    scanner.nextLine();
                    screen.pauseScreen(scanner);
                    break;
                case 3:
                    screen.ClearScreen();
                    withdrawMoney(scanner, user1);
                    screen.pauseScreen(scanner);
                    break;
                case 4:
                    screen.ClearScreen();
                    report.displayAccountBalancesAndTransactions();
                    //screen.pauseScreen(scanner);
                    break;
                case 5:
                    screen.ClearScreen();
                    addBudget(scanner, user1, categories);
                    screen.pauseScreen(scanner);
                    break;
                case 6:
                    screen.ClearScreen();
                    addSaving(scanner, user1);
                    screen.pauseScreen(scanner);
                    break;
                case 7:
                    screen.ClearScreen();
                    addTransaction(scanner, user1, categories);
                    screen.pauseScreen(scanner);
                    break;
                case 8:
                    screen.ClearScreen();
                    report.displayAllInfo();
                    //screen.pauseScreen(scanner);
                    break;
                case 9:
                    System.out.printf("%37s","Exiting...");
                    scanner.close();
                    writeBankFile(bank1, user1);
                    writeAccountFile(user1);
                    writeBudgetFile(user1, categories);
                    writeTransactionFile(user1);
                    writeSavingFile(user1);

                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

       private static void writeBankFile(Bank bank, Users user) throws IOException {
        try (FileWriter writer = new FileWriter("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\bank.txt", false)) {
            writer.write(bank.getName() + " " + user.getId() + " " + user.getName() + "\n");
        }
    }

    private static void writeAccountFile(Users user) throws IOException {
        try (FileWriter writer = new FileWriter("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\Account.txt", false)) {
            for (Account account : user.getAccounts()) {
                writer.write((account.getId()-1) + " " + account.getName() + " " + account.getBalance() + "\n");
            }
        }
    }

    private static void writeBudgetFile(Users user, Vector<Category> categories) throws IOException {
        try (FileWriter writer = new FileWriter("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\Budjet.txt", false)) {
            for (Account account : user.getAccounts()) {
                for (Budget budget : account.getBudgets()) {
                    writer.write((account.getId()-1) + " " + budget.getLimit() + " " + (categories.indexOf(budget.getCategory()) + 1) + "\n");
                }
            }
        }
    }

    private static void writeTransactionFile(Users user) throws IOException {
        try (FileWriter writer = new FileWriter("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\Transaction.txt", false)) {
            for (Account account : user.getAccounts()) {
                for (Transaction transaction : account.getTransactions()) {
                    writer.write((account.getId()-1) + " " + transaction.getDescription() + " " +transaction.getDate() + " " + transaction.getAmount() + " " + transaction.getCategoryId() + "\n");
                }
            }
        }
    }
    

    private static void writeSavingFile(Users user) throws IOException {
        try (FileWriter writer = new FileWriter("C:\\Users\\User\\Documents\\GitHub\\SECJ2154-OOP\\Submission\\sec04_23242\\codeHub\\source-code\\Saving.txt", false)) {
            for (Account account : user.getAccounts()) {
                for (Saving saving : account.getSavings()) {
                    writer.write((account.getId()-1) + " " + saving.getName() + " " + saving.getTargetAmount() + " " + saving.getCurrentAmount() + " " + saving.getTargetDate() + "\n");
                }
            }
        }
    }

    private static PauseScreen screen= new PauseScreen();

    private static void printMenu() {
        screen.ClearScreen();
        System.out.printf("%88s%n", "<<<<<<<PERSONAL FINANCE MANAGER>>>>>>>");
        System.out.printf("%90s%n", "----------------(Main Menu)----------------");
        System.out.printf("%60s%n","1. Add Account");
        System.out.printf("%62s%n","2. Deposit Money");
        System.out.printf("%63s%n","3. Withdraw Money");
        System.out.printf("%90s%n","4. Display Account Balances and Transactions");
        System.out.printf("%59s%n","5. Add Budget");
        System.out.printf("%59s%n","6. Add Saving");
        System.out.printf("%64s%n","7. Add Transaction");
        System.out.printf("%72s%n","8. Display All Information");
        System.out.printf("%53s%n","9. Exit");
        System.out.printf("%90s%n%n","-------------------------------------------");
        System.out.printf("%46s","Enter your choice: ");
    }

    private static void addAccount(Scanner scanner, Bank bank, Users user) {
        System.out.printf("%50s%n","***********ADD ACCOUNT***********");
        System.out.print("Enter account name: ");
        String accountName = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        Account account = new Account(user.getAccounts().size() + 1, accountName, balance);
        account.addBank(bank);
        user.addAccount(account);
        System.out.println("\nAccount added successfully.\n");
        System.out.printf("%31s%n%n","---------Account Details---------");
        account.displayInfo();
        System.out.println("");
    }
        

    private static void depositMoney(Scanner scanner, Users user) {
        System.out.printf("%50s%n","***********DEPOSIT MONEY***********");
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        try {
            Account account = findAccountById(user.getAccounts(), accountId);
            account.deposit(amount);
            System.out.println("\nMoney deposited successfully...\n");
            Date d = new Date(System.currentTimeMillis());
            account.addTransaction(accountId, "DEPOSIT", amount, d,new Deposit(accountId) );
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void withdrawMoney(Scanner scanner, Users user) {
        System.out.printf("%50s%n","**********WITHDRAW MONEY**********");
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {
            Account account = findAccountById(user.getAccounts(), accountId);
            account.withdraw(amount);
            Date d = new Date(System.currentTimeMillis());
            System.out.println("\nPRESS 1 FOR SHOPPING");
            System.out.println("PRESS 2 FOR FOOD");
            System.out.println("PRESS 3 FOR OTHER");
            System.out.print("-> ");
            int cater = scanner.nextInt();
            scanner.nextLine();

            if(cater == 1){
                
                System.out.print("\nEnter Shopping name: ");
                String Shop = scanner.nextLine();
                ShoppingCategory s = new ShoppingCategory(accountId);
                account.addTransaction(accountId, Shop, amount, d,s );
            }
            else if(cater == 2){
                System.out.print("\nEnter Food name: ");
                String Food = scanner.nextLine();
                FoodCategory s = new FoodCategory(accountId);
                account.addTransaction(accountId,Food, amount, d,s );
            }
            else if(cater == 3){
                System.out.print("\nEnter other name: ");
                String Other = scanner.nextLine();
                OtherCategory s = new OtherCategory(accountId);
                account.addTransaction(accountId, Other, amount, d, s);
            }
            else{
                System.out.println("NOT VALID");
            }
            System.out.println("\nMoney withdrawn successfully...");
        } catch (AccountNotFoundException | InsufficientFundsException e) {
           System.out.println(e.getMessage());
        }
    }

    private static void addBudget(Scanner scanner, Users user, Vector<Category> categories) {
        System.out.printf("%50s%n","***********ADD BUDGET***********");
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        System.out.print("Enter budget limit: ");
        double limit = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.println("Choose category: ");
        for (Category category : categories) {
            System.out.println(category.getId() + ". " + category.getCategoryType());
        }
        System.out.print("-> ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        try {
            Account account = findAccountById(user.getAccounts(), accountId);
            Category category = findCategoryById(categories, categoryId);

            //check only one budget for a category if want to make a new one will be reset
            Budget existingBudget = account.getBudgetByCategory(category);
            if (existingBudget != null) {
                System.out.println("A budget already exists for the category " + category.getCategoryType() + ".");
                System.out.print("Do you want to delete the existing budget and set a new one? (yes/no): ");
                scanner.nextLine(); // consume newline
                String confirm = scanner.nextLine();
                if (!confirm.equalsIgnoreCase("yes")) {
                    System.out.println("Budget setting cancelled.");
                    return;
                } else {
                    account.removeBudget(existingBudget);
                }
            }   
            account.addBudget(limit, category);
            System.out.println("\nBudget added successfully...\n");
        } catch (AccountNotFoundException | CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addSaving(Scanner scanner, Users user) {
        System.out.printf("%50s%n","***********ADD SAVING***********");
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        System.out.print("Enter saving goal name: ");
        scanner.nextLine(); // consume newline
        String name = scanner.nextLine();
        System.out.print("Enter target amount: ");
        double targetAmount = scanner.nextDouble();
        System.out.print("Enter current amount: ");
        double currentAmount = scanner.nextDouble();
        System.out.print("Enter target date (YYYY-MM-DD): ");
        scanner.nextLine();
        String targetDateString = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(targetDateString, formatter);
        Date targetDate = Date.valueOf(localDate);

        try {
            Account account = findAccountById(user.getAccounts(), accountId);
            Saving saving = new Saving(user.getAccounts().size() + 1, name, targetAmount,currentAmount, targetDate);
            //composition
            account.addSaving(saving);
            System.out.println("\nSaving goal added successfully...\n");
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addTransaction(Scanner scanner, Users user, Vector<Category> categories) {
        System.out.printf("%53s%n","**********ADD TRANSACTION**********");
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter transaction description: ");
        String description = scanner.nextLine();
        System.out.print("Enter transaction amount: ");
        double amount = scanner.nextDouble();
        System.out.println("Choose category: ");
        for (Category category : categories) {
            System.out.println(category.getId() + ". " + category.getCategoryType());
        }
        System.out.print("-> ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        try {
            Account account = findAccountById(user.getAccounts(), accountId);
            Category category = findCategoryById(categories, categoryId);
            Budget budget = account.getBudgetByCategory(category);

            // if the transaction amount is more than the budget need confirmation to exceed the budget
            if (budget != null && budget.getCurrentExpense() + amount > budget.getLimit()) {
                System.out.println(
                        "This transaction exceeds the budget limit for the category " + category.getName() + ".");
                System.out.print("Do you want to proceed? (yes/no): ");
                scanner.nextLine(); // consume newline
                String confirm = scanner.nextLine();
                if (!confirm.equalsIgnoreCase("yes")) {
                    System.out.println("Transaction cancelled.");
                    return;
                }
            }
            if (account.getBalance() >= amount) {

            Date d = new Date(System.currentTimeMillis());
            
            account.addTransaction(account.getTransactions().size() + 1, description, amount,d, category);
            
            account.withdraw(amount);
            }
            else {
                throw new InsufficientFundsException("Insufficient funds for this transaction.");
            }
            if (budget != null) {
                budget.setCurrentExpense(budget.getCurrentExpense() + amount);
            }
            System.out.println("\nTransaction added successfully...\n");
        } catch (AccountNotFoundException | InsufficientFundsException | CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Account findAccountById(Vector<Account> accounts, int id) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        throw new AccountNotFoundException("Account with ID " + id + " not found.");
    }

    private static Category findCategoryById(Vector<Category> categories, int id) throws CategoryNotFoundException {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        throw new CategoryNotFoundException("Category with ID " + id + " not found.");
    }
}

class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
