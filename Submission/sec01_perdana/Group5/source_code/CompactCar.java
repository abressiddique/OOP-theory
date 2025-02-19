//MUHADMMAD ERFAN SYABIL BIN ESA
//B23CS0055
//Compact.java
//Subclass to Car
class CompactCar extends Car{ //Shows Inheritance
    public CompactCar(String type, String make, String model, int year) {
        super(type, make, model, year, 50.00); //use super keyword to because of inheritance
    }

    public double getRentalRate(){
        return 50.00; //Rate per day
    }

    //toString() method is used to get the whole car name, make, model and year
    public String toString() {
        return getType() + "\t" + getMake() + "\t" + getModel() + "\t" + " (" + getYear() + ")";
    }
    
}
