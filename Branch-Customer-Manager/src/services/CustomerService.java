package services;

import models.Customer;
import repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        if (customer == null || customer.getCustomerId() == null) {
            throw new IllegalArgumentException("Valid customer with ID is required");
        }
        if (!customerRepository.findById(customer.getCustomerId()).isPresent()) {
            throw new RuntimeException("Customer not found: " + customer.getCustomerId());
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(String customerId) {
        if (!customerRepository.findById(customerId).isPresent()) {
            throw new RuntimeException("Customer not found: " + customerId);
        }
        customerRepository.deleteById(customerId);
    }

    public List<Customer> searchCustomersByName(String name) {
        return customerRepository.findByName(name);
    }

    public String displayCustomerDetails(String customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        
        if (!customerOpt.isPresent()) {
            return "Customer not found: " + customerId;
        }

        Customer customer = customerOpt.get();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Customer Details ===\n");
        sb.append("Customer ID: ").append(customer.getCustomerId()).append("\n");
        sb.append("Name: ").append(customer.getName()).append("\n");
        sb.append("Email: ").append(customer.getEmail()).append("\n");
        sb.append("Phone: ").append(customer.getPhoneNumber()).append("\n");
        sb.append("Address: ").append(customer.getAddress()).append("\n");
        sb.append("========================");
        
        return sb.toString();
    }

    public String displayAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        
        if (customers.isEmpty()) {
            return "No customers found.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== All Customers ===\n");
        sb.append(String.format("%-10s %-20s %-25s %-15s %s\n", 
            "ID", "Name", "Email", "Phone", "Address"));
        sb.append("-".repeat(90)).append("\n");
        
        for (Customer c : customers) {
            sb.append(String.format("%-10s %-20s %-25s %-15s %s\n",
                c.getCustomerId(),
                c.getName(),
                c.getEmail(),
                c.getPhoneNumber(),
                c.getAddress()));
        }
        sb.append("=====================");
        
        return sb.toString();
    }
}
