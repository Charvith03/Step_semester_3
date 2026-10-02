import java.util.ArrayList;
import java.util.List;

abstract class Vehicle {

    protected String vehicleId;
    protected boolean available;

    public Vehicle(String vehicleId) {
        this.vehicleId = vehicleId;
        this.available = true;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateRentalCharge(int days);
}

class Sedan extends Vehicle {

    public Sedan(String vehicleId) {
        super(vehicleId);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * 50;
    }
}

class SUV extends Vehicle {

    public SUV(String vehicleId) {
        super(vehicleId);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * 80;
    }
}

class Truck extends Vehicle {

    public Truck(String vehicleId) {
        super(vehicleId);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * 100;
    }
}

class Customer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Rental {

    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double amount;
    private boolean active;

    public Rental(Customer customer, Vehicle vehicle, int days) {

        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;

        this.amount = vehicle.calculateRentalCharge(days);

        this.active = true;

        vehicle.setAvailable(false);
    }

    public void returnVehicle() {

        if (active) {
            active = false;
            vehicle.setAvailable(true);

            System.out.println(
                    vehicle.getVehicleId()
                            + " returned by "
                            + customer.getName()
            );
        }
    }

    public double getAmount() {
        return amount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}

class RentalSystem {

    private List<Rental> rentals = new ArrayList<>();

    public void rentVehicle(
            Customer customer,
            Vehicle vehicle,
            int days) {

        if (!vehicle.isAvailable()) {

            System.out.println(
                    vehicle.getVehicleId()
                            + " is currently unavailable."
            );

            return;
        }

        Rental rental =
                new Rental(customer, vehicle, days);

        rentals.add(rental);

        System.out.println(
                vehicle.getVehicleId()
                        + " rented successfully by "
                        + customer.getName()
        );

        System.out.println(
                "Rental charge: $"
                        + rental.getAmount()
        );
    }
}

public class Main {

    public static void main(String[] args) {

        RentalSystem system =
                new RentalSystem();

        Vehicle sedanA =
                new Sedan("Sedan A");

        Vehicle suvB =
                new SUV("SUV B");

        Customer customer1 =
                new Customer("Customer 1");

        Customer customer2 =
                new Customer("Customer 2");

        Customer customer3 =
                new Customer("Customer 3");

        // Customer 1 rents Sedan A
        Rental rental1 =
                new Rental(customer1, sedanA, 3);

        System.out.println(
                "Sedan A rented successfully by Customer 1."
        );

        System.out.println(
                "Rental charge: $"
                        + rental1.getAmount()
        );

        // Customer 2 tries to rent the same vehicle
        system.rentVehicle(
                customer2,
                sedanA,
                2
        );

        // Customer 1 returns the vehicle
        rental1.returnVehicle();

        // Customer 3 rents SUV B
        system.rentVehicle(
                customer3,
                suvB,
                5
        );
    }
}