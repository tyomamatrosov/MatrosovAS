package UI;

import models.Patient;
import models.Department;
import services.PatientService;
import services.DepartmentService;

import java.util.Scanner;

class HospitalConsoleApp {
    private static Scanner scanner = new Scanner(System.in);
    private static PatientService patientService = new PatientService();
    private static DepartmentService departmentService = new DepartmentService();

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    removeDepartment();
                    break;
                case 3:
                    listDepartments();
                    break;
                case 4:
                    addPatient();
                    break;
                case 5:
                    removePatient();
                    break;
                case 6:
                    updatePatient();
                    break;
                case 7:
                    listPatients();
                    break;
                case 8:
                    return; // Выход из приложения
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n===== Меню =====");
        System.out.println("1. Добавить отделение");
        System.out.println("2. Удалить отделение");
        System.out.println("3. Показать все отделения");
        System.out.println("4. Добавить пациента");
        System.out.println("5. Удалить пациента");
        System.out.println("6. Редактировать пациента");
        System.out.println("7. Показать всех пациентов");
        System.out.println("8. Выйти");
        System.out.print("Выберите действие: ");
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            scanner.next(); // Сбрасываем некорректный ввод
            System.out.print("Введите число: ");
        }
        return scanner.nextInt();
    }

    private static void addDepartment() {
        scanner.nextLine(); // Поглощаем лишний символ новой строки
        System.out.print("Введите название отделения: ");
        String name = scanner.nextLine();
        Department department = new Department(0, name, 0);
        departmentService.addDepartment(department);
        System.out.println("Отделение добавлено!");
    }

    private static void removeDepartment() {
        System.out.print("Введите ID отделения для удаления: ");
        int id = getIntInput();
        if (departmentService.removeDepartment(id)) {
            System.out.println("Отделение удалено.");
        } else {
            System.out.println("Отделение с таким ID не найдено.");
        }
    }

    private static void listDepartments() {
        System.out.println("\n===== Список отделений =====");
        for (Department department : departmentService.getAllDepartments()) {
            System.out.println("ID: " + department.getId() + ", Название: " + department.getName() + ", Количество пациентов: " + department.getPatientCount());
        }
    }

    private static void addPatient() {
        scanner.nextLine(); // Поглощаем лишний символ новой строки
        System.out.print("Введите ФИО пациента: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите возраст пациента: ");
        int age = getIntInput();
        System.out.print("Введите пол пациента (M/F): ");
        char gender = scanner.next().charAt(0);
        System.out.print("Введите ID отделения: ");
        int departmentId = getIntInput();

        Patient patient = new Patient(0, fullName, age, gender, departmentId);
        patientService.addPatient(patient);
        System.out.println("Пациент добавлен!");
    }

    private static void removePatient() {
        System.out.print("Введите ID пациента для удаления: ");
        int id = getIntInput();
        if (patientService.removePatient(id)) {
            System.out.println("Пациент удален.");
        } else {
            System.out.println("Пациент с таким ID не найден.");
        }
    }

    private static void updatePatient() {
        System.out.print("Введите ID пациента для редактирования: ");
        int id = getIntInput();
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            System.out.println("Пациент с таким ID не найден.");
            return;
        }

        scanner.nextLine(); // Поглощаем лишний символ новой строки
        System.out.print("Введите новое ФИО пациента (оставьте пустым для без изменений): ");
        String fullName = scanner.nextLine();
        if (!fullName.isEmpty()) {
            patient.setFullName(fullName);
        }

        System.out.print("Введите новый возраст пациента (оставьте пустым для без изменений): ");
        String ageInput = scanner.nextLine();
        if (!ageInput.isEmpty()) {
            patient.setAge(Integer.parseInt(ageInput));
        }

        System.out.print("Введите новый пол пациента (M/F, оставьте пустым для без изменений): ");
        String genderInput = scanner.nextLine();
        if (!genderInput.isEmpty()) {
            patient.setGender(genderInput.charAt(0));
        }

        System.out.print("Введите новое ID отделения (оставьте пустым для без изменений): ");
        String departmentIdInput = scanner.nextLine();
        if (!departmentIdInput.isEmpty()) {
            patient.setDepartmentId(Integer.parseInt(departmentIdInput));
        }

        patientService.updatePatient(patient);
        System.out.println("Пациент обновлен!");
    }

    private static void listPatients() {
        System.out.println("\n===== Список пациентов =====");
        for (Patient patient : patientService.getAllPatients()) {
            System.out.println("ID: " + patient.getId() + ", ФИО: " + patient.getFullName() + ", Возраст: " + patient.getAge() + ", Пол: " + patient.getGender() + ", ID отделения: " + patient.getDepartmentId());
        }
    }
}
