import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class creatingXLSX {

    static XSSFWorkbook workbook = new XSSFWorkbook();
    static XSSFSheet sheet = workbook.createSheet("Pacjenci");

    private static final String FILE_NAME = "src/main/resources/Patients.xlsx";

    public static void createExcel(List<Patient> patientsList) {
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
            creatingPatientsExcel(sheet, patientsList);

        } catch (
                IOException e) {
            System.out.println("Upsss... Nie znaleziono pliku źródłowego.");
            e.printStackTrace();
        }
    }

    private static void creatingPatientsExcel(XSSFSheet sheet, List<Patient> patientList) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Imię");
        row.createCell(1).setCellValue("Nazwisko");
        row.createCell(2).setCellValue("PESEL");
        row.createCell(3).setCellValue("Koronawirus");
        row.createCell(4).setCellValue("Portfel");
        for (Patient patient : patientList) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(patient.getName());
            row.createCell(1).setCellValue(patient.getSurname());
            row.createCell(2).setCellValue(patient.getPesel());
            row.createCell(3).setCellValue(patient.getCorona().name());
            row.createCell(4).setCellValue(patient.getWallet());
        }
    }
}