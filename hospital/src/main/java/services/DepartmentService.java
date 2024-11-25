package services;

import models.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    private List<Department> departments = new ArrayList<>();
    private int nextDepartmentId = 1;

    public void addDepartment(Department department) {
        department.setId(nextDepartmentId++);
        departments.add(department);
    }

    public boolean removeDepartment(int id) {
        return departments.removeIf(department -> department.getId() == id);
    }

    public List<Department> getAllDepartments() {
        return departments;
    }
}
