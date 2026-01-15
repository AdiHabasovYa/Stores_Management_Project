package models;

public class Branch {
    private String branchId;
    private String location;
    private String managerName;
    private String contactNumber;

    public Branch() {}

    public Branch(String branchId, String location, String managerName, String contactNumber) {
        this.branchId = branchId;
        this.location = location;
        this.managerName = managerName;
        this.contactNumber = contactNumber;
    }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId='" + branchId + '\'' +
                ", location='" + location + '\'' +
                ", managerName='" + managerName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
