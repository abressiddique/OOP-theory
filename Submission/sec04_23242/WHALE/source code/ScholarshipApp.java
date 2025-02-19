import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

public class ScholarshipApp {
    static Scanner inp = new Scanner(System.in);
    static Administrator ad = null;
    static Student stu = null;

    public static void main(String[] args) throws IOException {
        Vector<Student> StudList = new Vector<Student>(); // Insert the student that have been registered for
                                                          // scholarship

        do {
            System.out.println("---------- WHALE SCHOLARSHIP ----------");
            System.out.println("Are you a Student or an Administrator?");

            System.out.print("[0]\tStudent\n[1]\tAdministrator\n");

            while (!inp.hasNextInt()) {
                System.out.println("Invalid input. Please enter 0 or 1");
                inp.next();
                System.out.print("[0]\tStudent\n[1]\tAdministrator\n");
            }
            int choice = inp.nextInt();

            if (choice == 0) { // Student Menu
                System.out.println("Register? (Y/N)");
                char rs = inp.next().toUpperCase().charAt(0);

                if (rs == 'Y') {
                    registerStudent(); // Student Registration function

                } else if (rs == 'N') {
                    stu = signInStudent(StudList); // Student sign in function. Will check with the current list
                    int i = 0;

                    if (StudList.size() == 0) { // If there is no student in the list, will add into it
                        StudList.add(stu);
                    }

                    if (!StudList.contains(stu)) { // Compare the list of students with the newly created
                        StudList.add(stu);
                    }

                    /*
                     * for (Student st : ad.getStudentApro()) { // Return the index of the ubject
                     * student
                     * if (st.equals(stu))
                     * break;
                     * else
                     * i++;
                     * }
                     */

                    System.out.println("[0] Do you want to check the status of your apllication?");
                    System.out.println("[1] Do you want to apply the scholarship?");
                    int ap = 0;

                    try {
                        ap = inp.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid Input. Please Enter a Number!");
                        inp.nextLine();
                    }

                    switch (ap) {
                        case 0:
                            if (ad == null) {
                                System.out.println("There is no administrator signed in. Please wait");
                            }

                            else if (openFileRej(stu.getfName())) {
                                System.out.println("Your application is not approved.");

                            } else if (openFileApro(stu.getfName())) {
                                System.out.println("Your application has approved.");
                                StudList.get(i).displayAllDetails();

                            } else {
                                System.out.println("Your application is being approved.");
                            }
                            break;
                        case 1:
                            applyFromDisplayScholarship(stu); // Student will apply the scholarship from this function
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter 0 or 1");
                            break;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter Y or N.");
                }
            }
            if (choice == 1) {// Administrator menu
                System.out.println("Register? (Y/N)");
                char rs = inp.next().toUpperCase().charAt(0);

                if (rs == 'Y') {
                    registerAdministrator(); // Administrator will register
                } else if (rs == 'N') {
                    ad = signInAdministrator(); // Sign in administrator

                    /*
                     * if (ad == null)
                     * break;
                     */
                } else {
                    System.out.println("Invalid choice. Please enter Y or N.");
                }

                System.out.println("Do you want to evaluate student?(Y/N)"); // Student evaluation process
                rs = inp.next().toUpperCase().charAt(0);

                if (rs == 'Y') {
                    if (StudList.size() > 0) {
                        for (int i = 0; i < StudList.size(); i++) {
                            StudList.get(i).displayAllDetails();
                            System.out.println("Do you want to Evaluate this student?(Y/N)");
                            rs = inp.next().toUpperCase().charAt(0);
                            boolean evs = false;
                            if (rs == 'Y') {
                                evs = ad.evaluateStudent(StudList.get(i), StudList.get(i).getScholarship()); // Call
                                                                                                             // Both
                                                                                                             // student
                                                                                                             // and
                                                                                                             // scholarship
                                StudList.remove(i);
                                ad.application.incrementStudent();
                                i--;
                            } else if (rs == 'N') {
                                evs = false;
                            }

                            else {
                            }
                        }
                    }
                    if (StudList.isEmpty()) {
                        System.out.println("There is no student applying the scholarship");
                    }

                    else {
                        displayStudList(StudList);
                    }

                    for (Student l : ad.getStudentApro()) { // Will display the list of the student that have been
                                                            // approved
                        l.displayAllDetails();
                    }

                    for (Student l : ad.getStudentRej()) { // Will display the list of the student that have been
                                                           // rejected
                        l.displayAllDetails();
                    }

                    writeFileApro(ad.getStudentApro()); // Write the approved student into output file
                    writeFileRej(ad.getStudentRej()); // Write the rejected student into output file

                }
                System.out.println("do you want to logout? (Y/N)");
                char y = inp.next().toUpperCase().charAt(0);

                if (y == 'Y') {
                    ad = null;
                } else if (y == 'N') {
                }
            }

        } while (ad != null || !StudList.isEmpty()); // If there is a student in the list or the administrator in not
                                                     // logged out, the system will keep going

        inp.close();
    }

    private static void registerAdministrator() {
        boolean validInput = false;
        int age = 0;

        try {
            System.out.println("---------- Personal Information ----------");
            System.out.print("FIRST NAME:\t");
            String fn = inp.next();
            System.out.print("LAST NAME:\t");
            String ln = inp.next();
            while (!validInput) {
                System.out.print("CURRENT AGE:\t");
                try {
                    age = inp.nextInt();
                    inp.nextLine();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    inp.nextLine();
                }
            }
            System.out.print("EMAIL:\t");
            String email = inp.nextLine();

            System.out.println("---------- Address Information ----------");
            System.out.print("STREET:\t");
            String s = inp.nextLine();
            System.out.print("CITY:\t");
            String ci = inp.nextLine();
            System.out.print("STATE:\t");
            String st = inp.nextLine();

            System.out.println("---------- Administrator Information ----------");
            System.out.print("USERNAME:\t");
            String usname = inp.next();
            inp.nextLine(); // Consume newline
            System.out.print("PASSWORD (Suggest to combine alphabet with numbers and symbols):\t");
            String pass = inp.next();
            inp.nextLine(); // Consume newline

            // Write to File
            PrintWriter outFile = new PrintWriter("Admin" + usname + ".txt");
            outFile.printf("%-20s\t%-20s\n", usname, pass);
            outFile.print(fn + "\t" + ln + "\t" + age + "\t" + email + "\n" + s + ", " + ci + ", " + st);
            outFile.close();
            System.out.println("Administrator information saved successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("Error: Unable to create or write to the file.");
            e.printStackTrace();
        }
    }

    private static Administrator signInAdministrator() {
        System.out.println("---------- Sign In ----------");
        System.out.print("ENTER YOUR USERNAME:\t");
        String us = inp.next();
        System.out.print("ENTER YOUR PASSWORD:\t");
        String pass = inp.next();
        String fileUsername = "", filePassword = "", fname = "", lname = "", email = "", address = "", street = "",
                cityAndPostalCode = "", state = "";
        int age = 0;

        File file = new File("Admin" + us + ".txt");

        try {
            // System.out.print("Inside the file") ;
            Scanner fileScanner = new Scanner(file);

            // Read the username and password from the file
            fileUsername = fileScanner.next();
            filePassword = fileScanner.next();

            // Check if the entered credentials match the stored ones
            if (fileUsername.equals(us) && filePassword.equals(pass)) {
                System.out.println("Login successful!");

                // Read the remaining details
                fname = fileScanner.next();
                lname = fileScanner.next();
                age = fileScanner.nextInt();
                // fileScanner.nextLine(); // Consume the newline character after age
                email = fileScanner.nextLine();

                address = fileScanner.nextLine();

                // Split the address by commas
                String[] addressParts = address.split(",");

                // Trim and assign each part to the corresponding variable
                street = addressParts[0].trim();
                cityAndPostalCode = addressParts[1].trim();
                state = addressParts[2].trim();

                fileScanner.close();
            }
            return new Administrator(fname, lname, age, email, new Address(street, cityAndPostalCode, state),
                    fileUsername);

        } catch (FileNotFoundException e) {
            System.out.println("There is no information about you");
            // If file is not found, continue to next authentication attempt
            System.err.println("Error: Unable to create or write to the file.");
            e.printStackTrace();
        }

        return null;
    }

    private static void registerStudent() {
        try {
            String fname, lname, email, s, ci, st, matricsNu;
            int age = 0;

            System.out.println("---------- Personal Information ----------");
            System.out.print("FIRST NAME:\t");
            fname = inp.next();
            System.out.print("LAST NAME:\t");
            lname = inp.next();
            System.out.print("CURRENT AGE:\t");
            age = inp.nextInt();
            inp.nextLine(); // Consume newline
            System.out.print("EMAIL:\t");
            email = inp.nextLine();

            System.out.println("---------- Address Information ----------");
            System.out.print("STREET:\t");
            s = inp.nextLine();
            System.out.print("CITY:\t");
            ci = inp.nextLine();
            System.out.print("STATE:\t");
            st = inp.nextLine();

            System.out.println("---------- Student Information ----------");
            System.out.print("USERNAME:\t");
            String usname = inp.next();
            inp.nextLine(); // Consume newline
            System.out.print("PASSWORD (Suggest to combine alphabet with numbers and symbols):\t");
            String pass = inp.next();
            inp.nextLine(); // Consume newline
            System.out.print("MATRIC NUMBER:\t");
            matricsNu = inp.next();

            // Write to File
            PrintWriter outFile = new PrintWriter("Student" + usname + ".txt");
            outFile.printf("%-20s\t%-20s\n", usname, pass);
            outFile.println(fname + "\t" + lname + "\t" + age + "\t" + email + "\n" + s + ", " + ci + ", " + st);
            outFile.printf("%-20s", matricsNu);
            outFile.close();
            System.out.println("Student information saved successfully.");

        }

        catch (FileNotFoundException e) {
            System.err.println("Error: Unable to create or write to the file.");
            e.printStackTrace();
        }
    }

    private static Student signInStudent(Vector<Student> so) {
        System.out.println("---------- Sign In ----------");
        System.out.print("ENTER YOUR USERNAME:\t");
        String us = inp.next();
        System.out.print("ENTER YOUR PASSWORD:\t");
        String pass = inp.next();
        String fileUsername = "", filePassword = "", fname = "", lname = "", email = "", address = "", street = "",
                cityAndPostalCode = "", state = "", matricsNu = "";
        int age = 0;

        File file = new File("Student" + us + ".txt");

        try {
            // System.out.print("Inside the file") ;
            Scanner fileScanner = new Scanner(file);

            // Read the username and password from the file
            fileUsername = fileScanner.next();
            filePassword = fileScanner.next();

            // Check if the entered credentials match the stored ones
            if (fileUsername.equals(us) && filePassword.equals(pass)) {
                System.out.println("Login successful!");

                // Read the remaining details
                fname = fileScanner.next();
                lname = fileScanner.next();
                age = fileScanner.nextInt();
                email = fileScanner.nextLine();

                address = fileScanner.nextLine();
                matricsNu = fileScanner.nextLine();

                // Split the address by commas
                String[] addressParts = address.split(",");

                // Trim and assign each part to the corresponding variable
                street = addressParts[0].trim();
                cityAndPostalCode = addressParts[1].trim();
                state = addressParts[2].trim();

                fileScanner.close();

                System.out.println("Have you applied for scholarship?(Y/N)");
                char rs = inp.next().toUpperCase().charAt(0);

                if (rs == 'Y') {

                    /*for (int i = 0; i < so.size(); i++) {
                        if (so.get(i).getEmail().equals(email)) {
                            return so.get(i);
                        } else {
                            i--;
                        }
                    }*/

                    return new Student(fname, lname, age, email, null, matricsNu, matricsNu, rs, null, null) ;
                    /*
                     * for (Student u : so) {
                     * if (u.getEmail().equals(email)) {
                     * return u;
                     * }
                     * 
                     * else {
                     * 
                     * }
                     * }
                     */
                }

                else if (rs == 'N') {
                    System.out.println("Choose your current program:");
                    System.out.println("[1] PREGRADUATE");
                    System.out.println("[2] UNDERGRADUATE");
                    System.out.println("[3] POSTGRADUATE");
                    int ch = inp.nextInt();

                    System.out.println("Enter the CGPA: ");
                    double cgpa = inp.nextDouble();
                    inp.nextLine(); // consume the newline character

                    // Get majors
                    System.out.println("Enter the majors: ");
                    String majors = inp.nextLine();

                    Programs p = Programs.UNDERGRADUATE;

                    switch (ch) {
                        case 1:
                            p = Programs.PREGRADUATE;
                            break;
                        case 2:
                            p = Programs.UNDERGRADUATE;
                            break;
                        case 3:
                            p = Programs.POSTGRADUATE;
                    }

                    // Scholarship o = applyFromDisplayScholarship(stu, age);
                    StudentHistory st = insertStudentHistory();

                    Student stu = new Student(fname, lname, age, email, new Address(street, cityAndPostalCode, state),
                            matricsNu, majors, cgpa, p, st);

                    return stu;
                }

            } else {
                System.out.println("Invalid username or password.");
            }

        } catch (FileNotFoundException e) {
            // If file is not found, continue to next authentication attempt
            System.out.println("User file not found.");
        }

        // Return null if authentication fails
        return null;
    }

    private static void applyFromDisplayScholarship(Student student) {
        ArrayList<meritBased> Merits = new ArrayList<>();
        ArrayList<needBased> Needs = new ArrayList<>();

        try {
            int choice = 0;

            while (true) {
                System.out.print("ENTER YOUR PARENTS THRESHOLD:\t");
                double th = inp.nextDouble();
                student.setThreshold(th);
                System.out.println("Select type of scholarship to apply:");
                System.out.println("[1] Merit Based");
                System.out.println("[2] Need Based");
                try {
                    choice = inp.nextInt();
                    inp.nextLine(); // consume the newline character
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input! Please Enter a Number!");
                    inp.nextLine(); // clear the invalid input
                }
            }

            if (choice == 1) {
                File file = new File("Submission\\sec04_23242\\WHALE\\source code\\meritScholarship.txt");
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String type = fileScanner.nextLine().trim();
                    if (!fileScanner.hasNextLine())
                        break;
                    double cgp = Double.parseDouble(fileScanner.nextLine().trim());
                    if (!fileScanner.hasNextLine())
                        break;
                    double all = Double.parseDouble(fileScanner.nextLine().trim());
                    Merits.add(new meritBased(all, type, cgp, 0));
                }

                fileScanner.close();

                System.out.println("Merit-Based Scholarships Available:");
                for (int i = 0; i < Merits.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + Merits.get(i).type);
                    System.out.println("  CGPA Required: " + Merits.get(i).getCgp());
                    System.out.println("  Allowance: " + Merits.get(i).getAllowance());
                }

                System.out.println("Enter the number of the scholarship you want to apply for:");
                int scholarshipChoice = inp.nextInt() - 1; // Adjust for 0-based index
                inp.nextLine(); // consume the newline character
                if (scholarshipChoice < 0 || scholarshipChoice >= Merits.size()) {
                    System.out.println("Invalid choice.");
                }

                System.out.println("You have selected: " + Merits.get(scholarshipChoice).type);
                System.out.println("Do you want to apply for this scholarship? (Y/N)");
                char response = inp.next().toUpperCase().charAt(0);
                if (response == 'Y') {
                    student.RegisterScholarship(Merits.get(scholarshipChoice));

                    // ad.setScholarshipAdmin(Merits.get(scholarshipChoice)) ;
                    // applyScholarship(student, sid);
                    System.out.println("Application successful.");
                    //return Merits.get(scholarshipChoice);
                } else {
                    System.out.println("Application cancelled.");
                    //return null;
                }
            } else if (choice == 2) {
                File file = new File("Submission\\sec04_23242\\WHALE\\source code\\needScholarshp.txt");
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    String type = fileScanner.nextLine().trim();
                    if (!fileScanner.hasNextLine())
                        break;
                    double cgp = Double.parseDouble(fileScanner.nextLine().trim());
                    if (!fileScanner.hasNextLine())
                        break;
                    double all = Double.parseDouble(fileScanner.nextLine().trim());
                    if (!fileScanner.hasNextLine())
                        break;
                    double thres = Double.parseDouble(fileScanner.nextLine().trim());
                    Needs.add(new needBased(all, type, cgp, thres));
                }
                fileScanner.close();

                System.out.println("Need-Based Scholarships Available:");
                for (int i = 0; i < Needs.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + Needs.get(i).type);
                    System.out.println("  CGPA Required: " + Needs.get(i).getCgp());
                    System.out.println("  Allowance: " + Needs.get(i).getAllowance());
                    System.out.println("  Threshold: " + Needs.get(i).getThres());
                }

                System.out.println("Enter the number of the scholarship you want to apply for:");
                int scholarshipChoice = inp.nextInt() - 1; // Adjust for 0-based index
                inp.nextLine(); // consume the newline character
                if (scholarshipChoice < 0 || scholarshipChoice >= Needs.size()) {
                    System.out.println("Invalid choice.");
                }

                System.out.println("You have selected: " + Needs.get(scholarshipChoice).type);
                System.out.println("Do you want to apply for this scholarship? (Y/N)");
                char response = inp.next().toUpperCase().charAt(0);
                if (response == 'Y') {
                    student.RegisterScholarship(Needs.get(scholarshipChoice));
                    System.out.println("Application successful.");
                    //return Needs.get(scholarshipChoice);
                } else {
                    System.out.println("Application cancelled.");
                }
            } else {
                System.out.println("Invalid choice.");
                //return null;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Scholarship file not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid format in scholarship file.");
        }
        //return null;
    }

    public static StudentHistory insertStudentHistory() {
        Scanner scanner = new Scanner(System.in);

        // Get last program
        System.out.println("Choose your last program:");
        System.out.println("[1] PREGRADUATE");
        System.out.println("[2] UNDERGRADUATE");
        System.out.println("[3] POSTGRADUATE");
        int ch = inp.nextInt();
        Programs lp = Programs.UNDERGRADUATE;

        switch (ch) {
            case 1:
                lp = Programs.PREGRADUATE;
                break;
            case 2:
                lp = Programs.UNDERGRADUATE;
                break;
            case 3:
                lp = Programs.POSTGRADUATE;
        }

        // Get CGPA
        System.out.println("Enter the last Program CGPA: ");
        double cgpa = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character

        // Get majors
        System.out.println("Enter the last Program majors: ");
        String majors = scanner.nextLine();

        // Create a new StudentHistory object and add it to the list
        StudentHistory newHistory = new StudentHistory(lp, cgpa, majors);
        System.out.println("Student history inserted successfully.");

        // scanner.close() ;
        return newHistory;

    }

    private static void displayStudList(Vector<Student> sop) {
        if (!sop.isEmpty()) {
            for (Student k : sop) {
                k.displayAllDetails();
            }
        }
    }

    private static void writeFileApro(ArrayList<Student> s) {
        try (PrintWriter writer = new PrintWriter(new PrintWriter("ApprovedStudent.txt"))) {
            for (Student o : s) {
                writer.write("Student Details:\n");
                writer.write("Full Name: " + o.getfName() + " " + o.getlName() + "\n");
                writer.write("Age: " + o.getAge() + "\n");
                writer.write("Email: " + o.getEmail() + "\n");
                writer.write("Address: " + o.getAdd().toString() + "\n");
                writer.write("Matrics Number: " + o.getMatricsNumber() + "\n");
                writer.write("Major: " + o.getMajor() + "\n");
                writer.write("CGPA: " + o.getCgpa() + "\n");
                writer.write("Program: " + o.getPrograms() + "\n");

                writer.write("\nScholarship Details:\n");
                writer.write("Type: " + o.getScholarship().getType() + "\n");
                writer.write("CGPA: " + o.getScholarship().getCgp() + "\n");
                writer.write("Allowance: $" + o.getScholarship().getAllowance() + "\n");

                writer.write("\nStudent History Details:\n");
                writer.write("Last Program: " + o.getStudentHistory().lastProgram() + "\n");
                writer.write("CGPA: " + o.getStudentHistory().getCgpa() + "\n");
                writer.write("Majors: " + o.getStudentHistory().getMajors() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

    }

    private static void writeFileRej(ArrayList<Student> s) {
        try (PrintWriter writer = new PrintWriter(new PrintWriter("RejectedStudent.txt"))) {
            for (Student o : s) {
                writer.write("Student Details:\n");
                writer.write("Full Name: " + o.getfName() + " " + o.getlName() + "\n");
                writer.write("Age: " + o.getAge() + "\n");
                writer.write("Email: " + o.getEmail() + "\n");
                writer.write("Address: " + o.getAdd().toString() + "\n");
                writer.write("Matrics Number: " + o.getMatricsNumber() + "\n");
                writer.write("Major: " + o.getMajor() + "\n");
                writer.write("CGPA: " + o.getCgpa() + "\n");
                writer.write("Program: " + o.getPrograms() + "\n");

                writer.write("\nScholarship Details:\n");
                writer.write("Type: " + o.getScholarship().getType() + "\n");
                writer.write("CGPA: " + o.getScholarship().getCgp() + "\n");
                writer.write("Allowance: $" + o.getScholarship().getAllowance() + "\n");

                writer.write("\nStudent History Details:\n");
                writer.write("Last Program: " + o.getStudentHistory().lastProgram() + "\n");
                writer.write("CGPA: " + o.getStudentHistory().getCgpa() + "\n");
                writer.write("Majors: " + o.getStudentHistory().getMajors() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

    }

    private static boolean openFileApro(String fy){
        try {
            Scanner sc = new Scanner(new File("ApprovedStudent.txt")) ;
            while(sc.hasNextLine()){
                String op = sc.nextLine() ;

                if (op.contains(fy)){
                    return true ;
                }

                else{

                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("There is no information about you");
            // If file is not found, continue to next authentication attempt
            System.err.println("Error: Unable to create or write to the file.");
            e.printStackTrace() ;
        }

        return false ;
    }

    private static boolean openFileRej(String fy){
        try {
            Scanner sc = new Scanner(new File("RejectedStudent.txt")) ;
            while(sc.hasNextLine()){
                String op = sc.nextLine() ;

                if (op.contains(fy)){
                    return true ;
                }

                else{

                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("There is no information about you");
            // If file is not found, continue to next authentication attempt
            System.err.println("Error: Unable to create or write to the file.");
            e.printStackTrace() ;
        }

        return false ;
    }
}
