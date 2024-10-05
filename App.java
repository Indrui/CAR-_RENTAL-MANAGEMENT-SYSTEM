import java.util.*;
import java.util.Scanner;
class Car {
    private String id;
    private String brand;
    private String model1;
    private double basePricePerDay;
    private boolean isAvailable = true;
    public Car(String id, String brand, String model1, double basepriceperday) {
        this.id = id;
        this.brand = brand;
        this.model1 = model1;
        this.basePricePerDay = basepriceperday;
    }

    public String getid() {
        return this.id;
    }

    public String getbrand() {
        return this.brand;
    }

    public String getmodel() {
        return this.model1;
    }

    public double calculateprice(int rentaldays) {
        return this.basePricePerDay * rentaldays;
    }

    public boolean isavailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }
    public void renturncar() {
        this.isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    // create a customer contructor
    public Customer(String customerId, String name) {
        this.name = name;
        this.customerId = customerId;
    }

    public String getcustomerid() {
        return this.customerId;
    }

    public String getcustomername() {
        return this.name;
    }
}

class Rental {
    private int days;
    private Car car;
    private Customer customer;

    public Rental(int days, Customer customer, Car car) {
        this.days = days;
        this.customer = customer;
        this.car = car;
    }

    public Car getcar() {
        return this.car;
    }

    public Customer getcustomer() {
        return this.customer;
    }

    public int getdays() {
        return this.days;
    }

}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rental;

    // create a constructor of car rental system
    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rental = new ArrayList<>();
    }

    // add a car
    public void addcar(Car car) {
        cars.add(car);
    }

    //add a customer
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentcar(Car car, Customer customer, int days) {
        if (car.isavailable()) {
            car.rent();
            rental.add(new Rental(days, customer, car));
        } else {
            System.out.println("The car is not available");
        }
    }

    public void returnCar(Car car) {
        car.renturncar();
        Rental rentalcar = null;
        for (Rental r : rental) {
            if (r.getcar() == car) {
                rentalcar = r;
                break;
            }
        }
        if (rentalcar != null) {
            rental.remove(rentalcar);
        } else {
            System.out.println("Car is not rented");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("====Welcome!Car Rental System===");
            System.out.println("1.rent a car");
            System.out.println("2,Return a car");
            System.out.println("3,Exit");
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();//consume new line
            if (choice == 1) {
                System.out.println("\n==Rent a Car==\n");
                System.out.println("Enter your name\n");
                String name = scanner.nextLine();
                System.out.println("\nAvailable cars:\n");
                for (Car car : cars) {
                    if (car.isavailable()) {
                        System.out.println(car.getid() + "-" + car.getbrand() + " " + car.getmodel());
                    }
                }
                System.out.println("Enter the car id you want to tent\n");
                String carid = scanner.nextLine();
                System.out.println("Enter the number of days for rental");
                int rentaldays = scanner.nextInt();
                scanner.nextLine();
                Customer customer = new Customer("CUS" + customers.size() + 1, name);
                addCustomer(customer);
                Car selectedcar = null;
                for (Car c : cars) {
                    if (c.getid().equals(carid) && c.isavailable()) {
                        selectedcar = c;
                        break;
                    }
                }
                if (selectedcar != null) {
                    double totalprice = selectedcar.calculateprice(rentaldays);
                    System.out.println("\n==rental information==\n");
                    System.out.println("Customer ID:" + customer.getcustomerid());
                    System.out.println("Customer name" + customer.getcustomername());
                    System.out.println("Car: " + selectedcar.getbrand() + " " + selectedcar.getmodel());
                    System.out.println("Rentaldays:-" + rentaldays);
                    System.out.printf("Totalprice:$.2f%n", totalprice);
                    System.out.println("\nConfirm Rental (Y/N):");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentcar(selectedcar, customer, rentaldays);
                        System.out.println("Car rented sucessfully");
                    } else {
                        System.out.println("Rental cancelled");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car is not available for rent");
                }
            } else if (choice == 2) {
                System.out.println("==Return a car==");
                System.out.println("Enter a carid you want to return");
                String carid = scanner.nextLine();

                Car carreturn = null;
                for (Car c : cars) {
                    if (c.getid().equals(carid) && !c.isavailable()) {
                        carreturn = c;
                        break;
                    }
                }
                if (carreturn != null) {
                    Customer customer = null;
                    for (Rental r : rental) {
                        if (r.getcar() == carreturn) {
                            customer = r.getcustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carreturn);
                        System.out.println("Car returned sucessfully" + customer.getcustomername());
                    } else {
                        System.out.println("Inavlid Car ID or car is not rented");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("This is an invalid choice.Please give a correct choice");
            }
        }
        System.out.println("Thank you for using the car Rental System");
    }
}
public class App {
    public static void main(String[] args) throws Exception {
        CarRentalSystem c = new CarRentalSystem();
        Car c1 = new Car("C001", "TOYOTA", "Camry", 87.0);
        Car c2 = new Car("C002", "MARUTI", "UTI", 56.78);
        c.addcar(c1);
        c.addcar(c2);
        c.menu();
    }
}
