package GroupProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseManager {
    private ArrayList<Admin> admins;
    private ArrayList<RegularUser> regularUsers;
    private static final String ADMIN_FILE = "C:\\Users\\SOH FEI ZHEN\\Documents\\Documents\\UTM sem4\\OOP\\GroupProject0\\GroupProject\\GroupProject\\admins.txt";
    private static final String REGULAR_USER_FILE = "C:\\Users\\SOH FEI ZHEN\\Documents\\Documents\\UTM sem4\\OOP\\GroupProject0\\GroupProject\\GroupProject\\regular_users.txt";
    private static final String MEAL_FILE = "C:\\Users\\SOH FEI ZHEN\\Documents\\Documents\\UTM sem4\\OOP\\GroupProject0\\GroupProject\\GroupProject\\meals.txt";

    public DatabaseManager() throws IOException {
        this.admins = new ArrayList<>();
        this.regularUsers = new ArrayList<>();
        loadAdmins();
        loadRegularUsers();
        loadMeals();
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public ArrayList<RegularUser> getRegularUsers() {
        return regularUsers;
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public void addRegularUser(RegularUser user) {
        regularUsers.add(user);
    }

    public boolean deleteRegularUser(String username) {
        boolean userDeleted = regularUsers.removeIf(user -> user.getUsername().equals(username));
        if (userDeleted) {
            saveRegularUsers();
            saveMeals();
        }
        return userDeleted;
    }

    public void saveMeals() {
        try (PrintWriter out = new PrintWriter(new FileWriter(MEAL_FILE))) {
            for (RegularUser user : regularUsers) {
                for (Meal meal : user.getMeals()) {
                    out.print(user.getUsername() + " " + meal.getType() + " " + meal.getDate());
                    for (FoodItem item : meal.getFoodItems()) {
                        out.print(" " + item.getName() + " " + item.getCalories());
                    }
                    out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAdmins() throws FileNotFoundException {
        File file = new File(ADMIN_FILE);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length == 2) {
                admins.add(new Admin(parts[0], parts[1]));
            }
        }
        scanner.close();
    }

    private void loadRegularUsers() throws FileNotFoundException {
        File file = new File(REGULAR_USER_FILE);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts.length == 6) {
                String username = parts[0];
                String password = parts[1];
                int age = Integer.parseInt(parts[2]);
                String gender = parts[3];
                double weight = Double.parseDouble(parts[4]);
                double height = Double.parseDouble(parts[5]);
                regularUsers.add(new RegularUser(username, password, age, gender, weight, height));
            }
        }
        scanner.close();
    }

    private void loadMeals() throws FileNotFoundException {
        File file = new File(MEAL_FILE);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println("Reading line: " + line); // Debug statement
            String[] parts = line.split(" ");
            if (parts.length >= 3) {
                String username = parts[0];
                String mealType = parts[1];
                String mealDate = parts[2];
                Meal meal = new Meal(mealType, mealDate); // Updated constructor

                System.out.println("Parsed meal: " + mealType + ", " + mealDate); // Debug statement

                // Process food items and their calories
                int index = 3;
                while (index < parts.length) {
                    StringBuilder foodName = new StringBuilder();
                    while (index < parts.length && !isNumeric(parts[index])) {
                        foodName.append(parts[index]).append(" ");
                        index++;
                    }
                    if (index < parts.length && isNumeric(parts[index])) {
                        int foodCalories = Integer.parseInt(parts[index]);
                        meal.addFoodItem(new FoodItem(foodName.toString().trim(), foodCalories));
                        System.out.println(
                                "Added food item: " + foodName.toString().trim() + " with calories: " + foodCalories); // Debug
                                                                                                                       // statement
                        index++;
                    } else {
                        System.out.println(
                                "Error parsing food item: " + foodName.toString().trim() + " in line: " + line);
                    }
                }

                boolean userFound = false;
                for (RegularUser user : regularUsers) {
                    if (user.getUsername().equals(username)) {
                        user.addMeal(meal);
                        System.out.println("Associated meal with user: " + username); // Debug statement
                        userFound = true;
                        break;
                    }
                }

                if (!userFound) {
                    System.out.println("User not found for username: " + username);
                }
            } else {
                System.out.println("Line does not have enough parts: " + line); // Debug statement
            }
        }
        scanner.close();
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void saveRegularUsers() {
        try (PrintWriter out = new PrintWriter(new FileWriter(REGULAR_USER_FILE))) {
            for (RegularUser user : regularUsers) {
                out.println(user.getUsername() + " " + user.getPassword() + " " + user.getAge() + " " + user.getGender()
                        + " " + user.getWeight() + " " + user.getHeight());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}