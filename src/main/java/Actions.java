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

    public static void runCoronaTest(Patient patient) {

        double wallet = patient.getWallet();

        if (wallet < 500) {
            System.out.println("Sorry, masz za mało środków w portfelu żeby wykonać test!");
            Main.start();
        }

        wallet = wallet - 500;
        patient.setWallet(wallet);


        System.out.println("Czy jesteś chory na koronawirusa? Wybierz odpowiednią wartość: \n0 Oczywiście, że nie " +
                "\n1 No pewnie, że tak \n2 Nie wiem");
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        switch (a) {
            case 0: {

                patient.setCorona(Corona.ZDROWY);
                System.out.println("Garatuluję, Zakończylismy test na koronawirusa, jesteś zdrowy. " +
                        "Stan Twojego konta to: " + wallet);
                break;
            }
            case 1: {
                patient.setCorona(Corona.CHORY);
                System.out.println("Gratuluję. Test na koronawirusa wypadł pozytywnie. Sugeruję kontakt z Sanepidem. " +
                        "Stan Twojego konta to: " + wallet);
                break;
            }
            case 2: {
                Random generator = new Random();
                System.out.println("Poliż ekran i wpisz OK");
                if (scanner.next().equalsIgnoreCase("OK")) {
                    System.out.println("Przeprowadzam analizę...");
                   /* TimeUnit.SECONDS.sleep(2);
                    System.out.println("Nadal analizuję...");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Jeszcze tylko chwila...");*/
                    boolean cor = generator.nextBoolean();
                    if (cor) {
                        patient.setCorona(Corona.ZDROWY);
                        System.out.println("Garatuluję, Zakończylismy test na koronawirusa, jesteś zdrowy. ");

                    } else {
                        patient.setCorona(Corona.CHORY);
                        System.out.println("Gratuluję. Test na koronawirusa wypadł pozytywnie. Sugeruję kontakt z Sanepidem. ");
                    }
                }
                System.out.println("Stan Twojego konta to: " + wallet);
                break;
            }
            default: {
                System.out.println("Wybrałeś niewłaściwą wartość");
                Main.start();
            }


        }


    }
}
