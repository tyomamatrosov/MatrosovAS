package services;

import models.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private List<Patient> patients = new ArrayList<>();
    private int nextPatientId = 1;

    // Метод для добавления пациента
    public void addPatient(Patient patient) {
        patient.setId(nextPatientId++);
        patients.add(patient);
    }

    // Метод для удаления пациента по ID
    public boolean removePatient(int id) {
        return patients.removeIf(patient -> patient.getId() == id);
    }

    // Метод для получения всех пациентов
    public List<Patient> getAllPatients() {
        return patients;
    }

    // Метод для получения пациента по ID
    public Patient getPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }

    // Метод для обновления данных пациента
    public void updatePatient(Patient patient) {
        // Можно добавить логику для поиска и обновления пациента, если он существует.
    }
}