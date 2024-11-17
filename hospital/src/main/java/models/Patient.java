package models;

public class Patient {
    private int id;
    private String fullName;
    private int age;
    private char gender;
    private int departmentId;

    public Patient(int id, String fullName, int age, char gender, int departmentId) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.departmentId = departmentId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public char getGender() { return gender; }
    public void setGender(char gender) { this.gender = gender; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
}