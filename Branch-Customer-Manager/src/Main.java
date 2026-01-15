import models.Branch;
import models.Customer;
import models.User;
import services.BranchService;
import services.CustomerService;

public class Main {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Management System - Feature Tests   ");
        System.out.println("========================================\n");

        testBranchInfoDisplay();
        
        System.out.println("\n");
        
        testCustomerManagement();
    }

    private static void testBranchInfoDisplay() {
        System.out.println(">>> TASK 1: Branch Information Display <<<\n");
        
        BranchService branchService = new BranchService();

        User user1 = new User("U001", "Employee One", "emp1@company.com", "BR001");
        User user2 = new User("U002", "Employee Two", "emp2@company.com", "BR002");

        System.out.println("User: " + user1.getName());
        System.out.println(branchService.displayBranchInfo(user1));

        System.out.println();

        System.out.println("User: " + user2.getName());
        System.out.println(branchService.displayBranchInfo(user2));

        System.out.println("\n--- Getting Branch object directly ---");
        Branch branch = branchService.getBranchInfoForUser(user1);
        System.out.println("Branch Object: " + branch);
    }

    private static void testCustomerManagement() {
        System.out.println(">>> TASK 2: Customer Management Interface <<<\n");
        
        CustomerService customerService = new CustomerService();

        System.out.println("--- Displaying All Customers ---");
        System.out.println(customerService.displayAllCustomers());

        System.out.println("\n--- Displaying Single Customer Details ---");
        System.out.println(customerService.displayCustomerDetails("C001"));

        System.out.println("\n--- Adding New Customer ---");
        Customer newCustomer = new Customer(
            "C004", 
            "David Lee", 
            "david@email.com", 
            "555-1004", 
            "321 Elm St, Houston"
        );
        customerService.addCustomer(newCustomer);
        System.out.println("Added: " + newCustomer.getName());

        System.out.println("\n--- Updated Customer List ---");
        System.out.println(customerService.displayAllCustomers());

        System.out.println("\n--- Searching Customers by Name ---");
        System.out.println("Search for 'Alice':");
        for (Customer c : customerService.searchCustomersByName("Alice")) {
            System.out.println("  Found: " + c);
        }

        System.out.println("\n--- Updating Customer ---");
        Customer updatedCustomer = new Customer(
            "C001", 
            "Alice Brown-Smith", 
            "alice.new@email.com", 
            "555-9999", 
            "999 Updated Ave, New York"
        );
        customerService.updateCustomer(updatedCustomer);
        System.out.println("Updated customer C001:");
        System.out.println(customerService.displayCustomerDetails("C001"));

        System.out.println("\n--- Deleting Customer ---");
        customerService.deleteCustomer("C002");
        System.out.println("Deleted customer C002");

        System.out.println("\n--- Final Customer List ---");
        System.out.println(customerService.displayAllCustomers());
    }
}
