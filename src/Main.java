import java.util.ArrayList;
import java.util.Scanner;

// ==========================================
// CLASS 1: CUSTOMER
// ==========================================

class Customer {

    private String name;
    private String email;
    private String password;
    private boolean registered = false;

    public void register(Scanner sc) {

        System.out.println("\n===== CUSTOMER REGISTRATION =====");

        System.out.print("Enter your name: ");
        name = sc.nextLine();

        System.out.print("Enter your email: ");
        email = sc.nextLine();

        System.out.print("Enter your password: ");
        password = sc.nextLine();

        registered = true;

        System.out.println("\nRegistration Successful!");
        System.out.println("Welcome " + name);
    }

    public boolean login(Scanner sc) {

        if (!registered) {
            System.out.println("Please register first!");
            return false;
        }

        System.out.println("\n===== LOGIN =====");

        System.out.print("Enter email: ");
        String inputEmail = sc.nextLine();

        System.out.print("Enter password: ");
        String inputPassword = sc.nextLine();

        if (email.equals(inputEmail) &&
                password.equals(inputPassword)) {

            System.out.println("Login Successful!");
            return true;

        } else {

            System.out.println("Invalid email or password!");
            return false;
        }
    }

    public String getName() {
        return name;
    }
}



class Product {

    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        stock -= quantity;
    }

    public void displayProduct() {

        System.out.printf(
                "%-10d %-20s %-12.2f %-10d%n",
                id, name, price, stock
        );
    }
}


// ==========================================
// CLASS 3: SHOP
// ==========================================

class Shop {

    private ArrayList<Product> products = new ArrayList<>();

    public Shop() {

        products.add(new Product(101, "Laptop", 55000, 10));
        products.add(new Product(102, "Mobile", 25000, 15));
        products.add(new Product(103, "Headphones", 2000, 20));
        products.add(new Product(104, "Keyboard", 1500, 25));
        products.add(new Product(105, "Mouse", 800, 30));
        products.add(new Product(106, "Smart Watch", 3500, 12));
    }

    public void displayProducts() {

        System.out.println("\n========== OUR SHOP ==========");

        System.out.printf(
                "%-10s %-20s %-12s %-10s%n",
                "ID", "PRODUCT", "PRICE", "STOCK"
        );

        System.out.println("-----------------------------------------------");

        for (Product product : products) {

            product.displayProduct();
        }
    }

    public Product findProduct(int id) {

        for (Product product : products) {

            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }
}


// ==========================================
// CLASS 4: CART ITEM
// ==========================================

class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {

        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {

        return product.getPrice() * quantity;
    }
}


// ==========================================
// CLASS 5: SHOPPING CART
// ==========================================

class ShoppingCart {

    private ArrayList<CartItem> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {

        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Enter a valid quantity!");
            return;
        }

        int existingQuantity = 0;

        for (CartItem item : items) {

            if (item.getProduct().getId() == product.getId()) {

                existingQuantity = item.getQuantity();
                break;
            }
        }

        if (existingQuantity + quantity > product.getStock()) {

            System.out.println("Insufficient stock!");
            return;
        }

        for (CartItem item : items) {

            if (item.getProduct().getId() == product.getId()) {

                item.setQuantity(existingQuantity + quantity);

                System.out.println("Cart updated successfully!");
                return;
            }
        }

        items.add(new CartItem(product, quantity));

        System.out.println("Product added successfully!");
    }

    public void displayCart() {

        if (items.isEmpty()) {

            System.out.println("\nYour cart is empty!");
            return;
        }

        double total = 0;

        System.out.println("\n========== SHOPPING CART ==========");

        System.out.printf(
                "%-20s %-12s %-10s %-12s%n",
                "PRODUCT", "PRICE", "QUANTITY", "TOTAL"
        );

        for (CartItem item : items) {

            double amount = item.getTotal();

            total += amount;

            System.out.printf(
                    "%-20s %-12.2f %-10d %-12.2f%n",
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity(),
                    amount
            );
        }

        System.out.println("-----------------------------------------------");

        System.out.println("Grand Total: Rs. " + total);
    }

    public double calculateTotal() {

        double total = 0;

        for (CartItem item : items) {

            total += item.getTotal();
        }

        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }
}


// ==========================================
// CLASS 6: ORDER
// ==========================================

class Order {

    private static int counter = 1001;

    private int orderId;
    private String customerName;
    private double amount;

    public Order(String customerName, double amount) {

        this.orderId = counter++;
        this.customerName = customerName;
        this.amount = amount;
    }

    public void displayOrder() {

        System.out.println("\n========== ORDER CONFIRMED ==========");

        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customerName);
        System.out.println("Amount: Rs. " + amount);
        System.out.println("Status: SUCCESS");

        System.out.println("=====================================");
    }
}


// ==========================================
// CLASS 7: MAIN
// ==========================================

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Customer customer = new Customer();

        Shop shop = new Shop();

        ShoppingCart cart = new ShoppingCart();

        ArrayList<Order> orders = new ArrayList<>();

        boolean loggedIn = false;

        while (true) {

            try {

                if (!loggedIn) {

                    System.out.println("\n================================");
                    System.out.println("       WELCOME TO OUR SHOP");
                    System.out.println("================================");

                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("3. Exit");

                    System.out.print("\nEnter choice: ");

                    int choice = Integer.parseInt(sc.nextLine());

                    switch (choice) {

                        case 1:

                            customer.register(sc);
                            break;

                        case 2:

                            loggedIn = customer.login(sc);
                            break;

                        case 3:

                            System.out.println("Thank you for visiting!");
                            sc.close();
                            return;

                        default:

                            System.out.println("Invalid choice!");
                    }

                } else {

                    System.out.println("\n========== SHOPPING MENU ==========");

                    System.out.println("1. View Products");
                    System.out.println("2. Add Product to Cart");
                    System.out.println("3. View Cart");
                    System.out.println("4. Checkout");
                    System.out.println("5. Order History");
                    System.out.println("6. Logout");

                    System.out.print("\nEnter choice: ");

                    int choice = Integer.parseInt(sc.nextLine());

                    switch (choice) {

                        case 1:

                            shop.displayProducts();
                            break;

                        case 2:

                            shop.displayProducts();

                            System.out.print("\nEnter Product ID: ");

                            int id = Integer.parseInt(sc.nextLine());

                            Product product = shop.findProduct(id);

                            if (product == null) {

                                System.out.println("Invalid Product ID!");
                                break;
                            }

                            System.out.println("Product: " + product.getName());

                            System.out.println("Price: Rs. " + product.getPrice());

                            System.out.print("Enter Quantity: ");

                            int quantity = Integer.parseInt(sc.nextLine());

                            cart.addProduct(product, quantity);

                            break;

                        case 3:

                            cart.displayCart();
                            break;

                        case 4:

                            if (cart.isEmpty()) {

                                System.out.println("Cart is empty!");
                                break;
                            }

                            cart.displayCart();

                            System.out.print("\nConfirm order? (yes/no): ");

                            String confirm = sc.nextLine();

                            if (confirm.equalsIgnoreCase("yes")) {

                                boolean validStock = true;

                                for (CartItem item : cart.getItems()) {

                                    if (item.getQuantity() >
                                            item.getProduct().getStock()) {

                                        validStock = false;
                                        break;
                                    }
                                }

                                if (!validStock) {

                                    System.out.println("Insufficient stock!");
                                    break;
                                }

                                for (CartItem item : cart.getItems()) {

                                    item.getProduct().reduceStock(
                                            item.getQuantity()
                                    );
                                }

                                Order order = new Order(
                                        customer.getName(),
                                        cart.calculateTotal()
                                );

                                orders.add(order);

                                order.displayOrder();

                                cart.clearCart();

                            } else {

                                System.out.println("Order cancelled!");
                            }

                            break;

                        case 5:

                            if (orders.isEmpty()) {

                                System.out.println("No orders found!");

                            } else {

                                for (Order order : orders) {

                                    order.displayOrder();
                                }
                            }

                            break;

                        case 6:

                            loggedIn = false;

                            cart.clearCart();

                            System.out.println("Logged out successfully!");

                            break;

                        default:

                            System.out.println("Invalid choice!");
                    }
                }

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number!");

            } catch (Exception e) {

                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }
}