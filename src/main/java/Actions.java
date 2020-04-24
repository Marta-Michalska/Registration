import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Actions {


    public static Patient findPatient(long peselIn, List<Patient> patientsList) {
        Patient tempPatient = null;
        for (Patient patient : patientsList) {
            if (patient.getPesel() == peselIn) {

                tempPatient = patient;
                System.out.println("Znaleziono pacjenta.");

            }

        }
        if (tempPatient == null){
            System.out.println("Ups... Nie ma takiego pacjenta w rejestrze");
        }
        return tempPatient;
    }

    public static boolean isRegistered(List<Patient> patientsList, long pesel) {
        boolean isRegistered = false;
        for (Patient patient : patientsList) {
            if (patient.getPesel() == pesel) {
                isRegistered = Boolean.TRUE;
            }
        }
        return isRegistered;
    }

    public static boolean isRegistered(List<Patient> patientsList, String name, String surname) {
        boolean isRegistered = false;
        for (Patient patient : patientsList) {
            if (patient.getName().equals(name) && patient.getSurname().equals(surname)) {
                isRegistered = Boolean.TRUE;
            }
        }
        return isRegistered;
    }


}
