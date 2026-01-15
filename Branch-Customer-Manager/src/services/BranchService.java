package services;

import models.Branch;
import models.User;
import repositories.BranchRepository;
import java.util.Optional;

public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService() {
        this.branchRepository = new BranchRepository();
    }

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch getBranchInfoForUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        String branchId = user.getBranchId();
        if (branchId == null || branchId.isEmpty()) {
            throw new IllegalArgumentException("User does not have an assigned branch");
        }

        Optional<Branch> branch = branchRepository.findById(branchId);

        if (branch.isPresent()) {
            return branch.get();
        } else {
            throw new RuntimeException("Branch not found for ID: " + branchId);
        }
    }

    public String displayBranchInfo(User user) {
        Branch branch = getBranchInfoForUser(user);
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== Branch Information ===\n");
        sb.append("Branch ID: ").append(branch.getBranchId()).append("\n");
        sb.append("Location: ").append(branch.getLocation()).append("\n");
        sb.append("Manager: ").append(branch.getManagerName()).append("\n");
        sb.append("Contact: ").append(branch.getContactNumber()).append("\n");
        sb.append("==========================");
        
        return sb.toString();
    }

    public Optional<Branch> findBranchById(String branchId) {
        return branchRepository.findById(branchId);
    }
}
