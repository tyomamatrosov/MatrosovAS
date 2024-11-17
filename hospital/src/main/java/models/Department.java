package models;

public class Department {
    private int id;
    private String name;
    private int patientCount;

    public Department(int id, String name, int patientCount) {
        this.id = id;
        this.name = name;
        this.patientCount = patientCount;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPatientCount() { return patientCount; }
    public void setPatientCount(int patientCount) { this.patientCount = patientCount; }
}