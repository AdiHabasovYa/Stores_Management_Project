package repositories;

import models.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerRepository {

    // =====================================================================
    // SHARED DATA STRUCTURE: ConcurrentHashMap ensures thread-safe access
    // across all branches. This simulates a centralized database.
    //
    // TO CONNECT TO REAL DATABASE:
    // 1. Add JDBC driver dependency (e.g., mysql-connector-java, postgresql)
    // 2. Create a DatabaseConnection utility class
    // 3. Replace mock operations with actual SQL queries
    // =====================================================================
    private static final Map<String, Customer> sharedCustomerDatabase = new ConcurrentHashMap<>();

    static {
        sharedCustomerDatabase.put("C001", new Customer("C001", "Alice Brown", "alice@email.com", "555-1001", "123 Main St, New York"));
        sharedCustomerDatabase.put("C002", new Customer("C002", "Bob Wilson", "bob@email.com", "555-1002", "456 Oak Ave, Los Angeles"));
        sharedCustomerDatabase.put("C003", new Customer("C003", "Carol Davis", "carol@email.com", "555-1003", "789 Pine Rd, Chicago"));
    }

    public Optional<Customer> findById(String customerId) {
        // =====================================================================
        // DATABASE QUERY: Replace with actual query
        // Example: SELECT * FROM customers WHERE customer_id = ?
        // =====================================================================
        return Optional.ofNullable(sharedCustomerDatabase.get(customerId));
    }

    public List<Customer> findAll() {
        // =====================================================================
        // DATABASE QUERY: Replace with actual query
        // Example: SELECT * FROM customers
        // =====================================================================
        return new ArrayList<>(sharedCustomerDatabase.values());
    }

    public void save(Customer customer) {
        // =====================================================================
        // DATABASE INSERT/UPDATE: Replace with actual query
        // Example: INSERT INTO customers (customer_id, name, email, phone, address) VALUES (?, ?, ?, ?, ?)
        // =====================================================================
        sharedCustomerDatabase.put(customer.getCustomerId(), customer);
    }

    public void deleteById(String customerId) {
        // =====================================================================
        // DATABASE DELETE: Replace with actual query
        // Example: DELETE FROM customers WHERE customer_id = ?
        // =====================================================================
        sharedCustomerDatabase.remove(customerId);
    }

    public List<Customer> findByName(String name) {
        // =====================================================================
        // DATABASE QUERY: Replace with actual query
        // Example: SELECT * FROM customers WHERE name LIKE ?
        // =====================================================================
        List<Customer> results = new ArrayList<>();
        for (Customer customer : sharedCustomerDatabase.values()) {
            if (customer.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(customer);
            }
        }
        return results;
    }
}
