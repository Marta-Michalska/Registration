import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String FILE_NAME = "src/main/resources/Patients.xlsx";

    public static void main(String[] args) {


        List<Patient> patientsList = new ArrayList<>();
        patientsList.add(new Patient("Jan", "Kowalski", 12345678901L));


        Scanner scanner = new Scanner(System.in);
        System.out.println("Wpisz imię");
        String nameIn = scanner.nextLine();
        System.out.println("Witaj " + nameIn + "! A teraz wpisz nazwisko.");
        String surnameIn = scanner.nextLine();
        System.out.println("Dzięki " + nameIn + " " + surnameIn + ". Wpisz swój numer PESEL.");
        long peselIn = scanner.nextLong();

        while (peselIn < 10000000000L || peselIn > 99999999999L)
        //Jak do cholery napisać warunek, że while w dostarczonych danych nie ma longa?...
        {
            System.out.println("Podaj prawidłowy numer PESEL: 11 cyfr bez spacji, przecinków ani innych znaków");
            peselIn = scanner.nextLong();

        }

        System.out.println("Ok " + nameIn + ". Twoje dane to: " + nameIn + " " + surnameIn + ", PESEL: " + peselIn + ".");

        patientsList.add(new Patient(nameIn, surnameIn, peselIn));


        System.out.println("Zostałeś zarejestrowany!");
        System.out.println(patientsList);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Pacjenci");
        creatingPatientsExcel(sheet,patientsList);
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private static void creatingPatientsExcel(XSSFSheet sheet, List<Patient> patientList) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Imię");
        row.createCell(1).setCellValue("Nazwisko");
        row.createCell(2).setCellValue("PESEL");
        for (Patient patient : patientList) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(patient.getName());
            row.createCell(1).setCellValue(patient.getSurname());
            row.createCell(2).setCellValue(patient.getPesel());
        }

    }

}
