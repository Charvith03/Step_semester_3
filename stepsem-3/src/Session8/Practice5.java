import java.util.ArrayList;
import java.util.List;

class Customer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Product {

    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class OrderItem {

    private Product product;
    private int quantity;

    public OrderItem(
            Product product,
            int quantity) {

        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}

interface PaymentMethod {

    boolean processPayment(double amount);
}

class CreditCardPayment
        implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {

        System.out.println(
                "Processing Credit Card payment of $"
                        + amount
        );

        return true;
    }
}

class PayPalPayment
        implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {

        System.out.println(
                "Processing PayPal payment of $"
                        + amount
        );

        // Simulate failure
        return false;
    }
}

class BankTransferPayment
        implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {

        System.out.println(
                "Processing Bank Transfer payment of $"
                        + amount
        );

        return true;
    }
}

class Order {

    private Customer customer;

    private List<OrderItem> items =
            new ArrayList<>();

    private String status;

    public Order(Customer customer) {

        this.customer = customer;
        this.status = "Pending";
    }

    public void addProduct(
            Product product,
            int quantity) {

        items.add(
                new OrderItem(
                        product,
                        quantity
                )
        );
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double calculateTotal() {

        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotal();
        }

        return total;
    }

    public void pay(
            PaymentMethod paymentMethod) {

        if (items.isEmpty()) {

            System.out.println(
                    "Cannot process payment for an empty order."
            );

            return;
        }

        double total = calculateTotal();

        System.out.println(
                "Payment initiated for "
                        + customer.getName()
        );

        boolean success =
                paymentMethod.processPayment(total);

        if (success) {

            status = "Paid";

            System.out.println(
                    "Payment for order successful."
            );

        } else {

            System.out.println(
                    "Payment for order failed."
            );
        }

        System.out.println(
                "Order status: "
                        + status
        );
    }

    public String getStatus() {
        return status;
    }
}

public class Main {

    public static void main(String[] args) {

        // -------------------------------
        // Customer X
        // -------------------------------

        Customer customerX =
                new Customer("Customer X");

        Order orderX =
                new Order(customerX);

        Product productA =
                new Product("Product A", 50);

        Product productB =
                new Product("Product B", 100);

        orderX.addProduct(productA, 2);
        orderX.addProduct(productB, 1);

        System.out.println(
                "Order created for Customer X."
        );

        System.out.println(
                "Payment initiated via Credit Card for Order X."
        );

        orderX.pay(
                new CreditCardPayment()
        );

        // -------------------------------
        // Customer Y - Empty order
        // -------------------------------

        Customer customerY =
                new Customer("Customer Y");

        Order orderY =
                new Order(customerY);

        orderY.pay(
                new CreditCardPayment()
        );

        // -------------------------------
        // Customer Z - PayPal failure
        // -------------------------------

        Customer customerZ =
                new Customer("Customer Z");

        Order orderZ =
                new Order(customerZ);

        Product productC =
                new Product("Product C", 75);

        orderZ.addProduct(productC, 1);

        System.out.println(
                "Order created for Customer Z."
        );

        System.out.println(
                "Payment initiated via PayPal for Order Z."
        );

        orderZ.pay(
                new PayPalPayment()
        );
    }
}