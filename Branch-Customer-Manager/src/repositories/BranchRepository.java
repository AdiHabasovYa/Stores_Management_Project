package repositories;

import models.Branch;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BranchRepository {

    // =====================================================================
    // MOCK DATABASE: Replace this with actual database connection
    // 
    // TO CONNECT TO REAL DATABASE:
    // 1. Add JDBC driver dependency to your project
    // 2. Replace the mockDatabase with actual DB queries using:
    //    - Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    //    - PreparedStatement stmt = conn.prepareStatement("SELECT * FROM branches WHERE branch_id = ?");
    // =====================================================================
    private static final Map<String, Branch> mockDatabase = new HashMap<>();

    static {
        mockDatabase.put("BR001", new Branch("BR001", "New York", "John Smith", "555-0101"));
        mockDatabase.put("BR002", new Branch("BR002", "Los Angeles", "Jane Doe", "555-0102"));
        mockDatabase.put("BR003", new Branch("BR003", "Chicago", "Mike Johnson", "555-0103"));
    }

    public Optional<Branch> findById(String branchId) {
        // =====================================================================
        // DATABASE QUERY: Replace with actual query
        // Example: SELECT * FROM branches WHERE branch_id = ?
        // =====================================================================
        return Optional.ofNullable(mockDatabase.get(branchId));
    }

    public Map<String, Branch> findAll() {
        // =====================================================================
        // DATABASE QUERY: Replace with actual query
        // Example: SELECT * FROM branches
        // =====================================================================
        return new HashMap<>(mockDatabase);
    }

    public void save(Branch branch) {
        // =====================================================================
        // DATABASE INSERT/UPDATE: Replace with actual query
        // Example: INSERT INTO branches (branch_id, location, manager_name, contact_number) VALUES (?, ?, ?, ?)
        // Or: UPDATE branches SET location = ?, manager_name = ?, contact_number = ? WHERE branch_id = ?
        // =====================================================================
        mockDatabase.put(branch.getBranchId(), branch);
    }

    public void deleteById(String branchId) {
        // =====================================================================
        // DATABASE DELETE: Replace with actual query
        // Example: DELETE FROM branches WHERE branch_id = ?
        // =====================================================================
        mockDatabase.remove(branchId);
    }
}
