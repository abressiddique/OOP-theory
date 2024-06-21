# 🍏Nutrition Tracker System

𝓦𝓮𝓵𝓬𝓸𝓶𝓮 𝓽𝓸 𝓷𝓾𝓽𝓻𝓲𝓽𝓲𝓸𝓷 𝓽𝓻𝓪𝓬𝓴𝓮𝓻 𝓼𝔂𝓼𝓽𝓮𝓶 ! 

> This system helps you keep track of your daily food intake, monitor your nutritional goals, and maintain a balanced diet.

<img align= "center"  width=80% src= "https://i.giphy.com/j6SbdhHBWfz8SttRAJ.webp">


### 🥗 Features

📊 Daily Tracking: Log your daily meals and snacks.

⚖️ BMI Calculation: Calculate your Body Mass Index (BMI) to monitor your health and fitness progress.

📝 Meal Recording: Users can log their meals, including meal type, date, and food items.

✏️ Meal Editing: Easily edit your logged meals to ensure accurate tracking.

📈 Meal Reports: View your meals over date with visual reports.

👥 User Management: Admins can view food items, manage users, and delete user accounts.

🔒 Secure Login: Both admin and regular users can securely log in to access their personalized features.


### 🥗 OOP concepts
Encapsulation and data hiding
~~~
public class RegularUser extends User {
    private int age;
    private String gender;
    private double weight;
    private double height;
    private NutritionTracker nutritionTracker;

    public RegularUser(String username, String password, int age, String gender, double weight, double height) {
        super(username, password);
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.nutritionTracker = new NutritionTracker();
    }

    public int getAge() {
        return age;
    }
~~~
Above code shows the RegularUser class has implemented encapsulation by providing accessors such as getAge() for other objects to indirectly access data. 
Association
Composition
Inheritance
Abstract class and polymorphism
Exception handling


### 🥗 FlowChart

### 🥗 Class Diagram
