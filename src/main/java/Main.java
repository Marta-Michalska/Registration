import com.sun.xml.internal.bind.v2.TODO;
import javafx.scene.chart.ScatterChart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;

public class Main {

    private static final String FILE_NAME = "src/main/resources/Patients.xlsx";
    private static Scanner scanner = new Scanner(System.in);
    private static List<Patient> patientsList;


    public static void main(String[] args) {

        patientsList = new ArrayList<>();

        //test patientsList
      /* patientsList.add(new Patient("Jan", "Kowalski", 12345678901L, Corona.CHORY, 500));
        patientsList.add(new Patient("Anna", "Kowalska", 32145678901L, Corona.ZDROWY, 30));
        patientsList.add(new Patient("Jan", "Kowalski", 23145678901L, Corona.BRAK_DANYCH, 1500));*/

        try {
            FileInputStream puller = new FileInputStream(FILE_NAME);
            XSSFWorkbook workbook = new XSSFWorkbook(puller);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                DataFormatter formatter = new DataFormatter();
                patientsList.add(new Patient(row.getCell(0).toString(), row.getCell(1).toString(), Long.parseLong(formatter.formatCellValue(row.getCell(2))), Corona.valueOf(row.getCell(3).toString()), Double.parseDouble(row.getCell(4).toString())));
            }


        } catch (FileNotFoundException e) {
            System.out.println("Upsss... Niewłaściwie źródło");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Upsss... Nie znaleziono pliku");

            e.printStackTrace();
        }

        start();
    }

    public static void start() {
        System.out.println("Wybierz akcje: \n0 - Zakończ \n1 - Sprawdź czy pacjent jest zarejestrowany " +
                "\n2 - Zarejestruj pacjenta \n3 - Usuń pacjenta \n4 - Zleć badanie na koronawirusa");

        try {
            Integer action = scanner.nextInt();
            actionType(action);
        } catch (InputMismatchException exception) {
            System.out.println("Co Ty mi tu wypisujesz. Jeszcze raz...");
            scanner.nextLine();
            start();
        }

    }


    private static void actionType(int a) {
        switch (a) {
            case 0:
                System.out.println("Do widzenia!");
                try {
                    creatingXLSX.workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 1: {
                System.out.println("Jak chcesz wyszukiwać? \n1- Po imieniu i nazwisku \n2-Po PESELu \n0-Zakończ");
                int action = scanner.nextInt();
                switch (action) {
                    case 0: {
                        System.out.println("Do zobaczenia!");
                        break;
                    }

                    case 1: {
                        System.out.println("Wpisz imię");
                        String nameIn = scanner.next();
                        System.out.println("Wpisz nazwisko");
                        String surnameIn = scanner.next();
                        boolean isReg = Actions.isRegistered(patientsList, nameIn, surnameIn);
                        if (isReg) {
                            System.out.println("Pacjent o takim imieniu i nazwisku jest zarejestrowany");
                        } else {
                            System.out.println("Pacjent o takim imieniu i nazwisku nie jest zarejestrowany");
                        }
                        start();
                    }
                    case 2: {
                        System.out.println("Wpisz PESEL");
                        long peselIn = scanner.nextLong();
                        boolean isReg = Actions.isRegistered(patientsList, peselIn);
                        if (isReg) {
                            System.out.println("Pacjent jest zarejestrowany");
                        } else {
                            System.out.println("Pacjent nie jest zarejestrowany");
                        }
                        start();
                    }
                }
            }
            case 2: {
                registration();
                start();
                break;
            }
            case 3: {
                removing();
                start();
                break;
            }
            case 4: {
                System.out.println("Wpisz numer PESEL pacjenta, któremu chcesz zlecić badanie");
                long peselIn = scanner.nextLong();
                Patient tempPatient = Actions.findPatient(peselIn, patientsList);
                if (tempPatient != null) {
                    runCoronaTest(tempPatient);
                }
                start();
                break;

            }
            default: {
                System.out.println("Podałeś niewłaściwy numer. Wpisz numer od 0 do 4");
                start();

            }
        }
    }


    private static void registration() {
        System.out.println("Wpisz imię");
        String nameIn = scanner.next();
        System.out.println("Witaj " + nameIn + "! Teraz wpisz nazwisko.");
        String surnameIn = scanner.next();
        System.out.println("Dzięki " + nameIn + " " + surnameIn + ". Wpisz swój numer PESEL.");
        long peselIn = scanner.nextLong();
        boolean isReg = Actions.isRegistered(patientsList, peselIn);
        if (isReg == true) {
            System.out.println("Pacjent o tym PESELu jest już zarejestrowany. Spróbuj jeszcze raz.");
            registration();
        } else {


            while (peselIn < 10000000000L || peselIn > 99999999999L) {


                System.out.println("Podaj prawidłowy numer PESEL: 11 cyfr bez spacji, przecinków ani innych znaków");
                peselIn = scanner.nextLong();
            }


        }

        System.out.println("Ok " + nameIn + ". Twoje dane to: " + nameIn + " " + surnameIn + ", PESEL: " + peselIn + "." +
                " Jaką kwotę chcesz zdeponować w swoim wirtualnym portfelu?");
        double walletIn = scanner.nextDouble();


        patientsList.add(new Patient(nameIn, surnameIn, peselIn, Corona.BRAK_DANYCH, walletIn));
        creatingXLSX.createExcel(patientsList);
        System.out.println("Zostałeś zarejestrowany!");
    }

    private static void removing() {
        System.out.println("Wpisz numer PESEL pacjenta, którego chcesz usunąć");
        long peselIn = scanner.nextLong();
        Patient tempPatient = Actions.findPatient(peselIn, patientsList);
        if (tempPatient == null) {
            System.out.println("Nie ma takiego pacjenta");
        } else {
            patientsList.remove(tempPatient);
            creatingXLSX.createExcel(patientsList);
            System.out.println("Udało Ci się usunąć pacjenta: " + tempPatient.toString());
        }
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
        int a = scanner.nextInt();
        switch (a) {
            case 0: {

                patient.setCorona(Corona.ZDROWY);
                System.out.println("Garatuluję, Zakończylismy test na koronawirusa, jesteś zdrowy. " +
                        "Stan Twojego konta to: " + wallet);
                creatingXLSX.createExcel(patientsList);
                break;
            }
            case 1: {
                patient.setCorona(Corona.CHORY);
                System.out.println("Gratuluję. Test na koronawirusa wypadł pozytywnie. Sugeruję kontakt z Sanepidem. " +
                        "Stan Twojego konta to: " + wallet);
                creatingXLSX.createExcel(patientsList);
                break;
            }
            case 2: {
                Random generator = new Random();
                System.out.println("Poliż ekran i wpisz OK");
                String lick = scanner.next();
                while (lick.equalsIgnoreCase("ok") == false) {
                    System.out.println("Poliż ekran i wpisz OK. Dokładnie OK. Nie wpisuj niczego innego, bo test będzie niewiarygodny");
                    lick = scanner.next();
                }
                if (lick.equalsIgnoreCase("OK")) {
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
                creatingXLSX.createExcel(patientsList);
                break;
            }
            default: {
                System.out.println("Wybrałeś niewłaściwą wartość");
                Main.start();
            }


        }


    }

}





