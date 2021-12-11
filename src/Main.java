import java.util.HashMap;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Словарь месяц(ключ) - объект(значение).
        HashMap<String, MonthlyReport> monthlyReports = new HashMap<>();
        // Словарь год(ключ) - объект(значение).
        HashMap<Integer, YearlyReport> yearlyReports = new HashMap<>();

        ReportUtils reportUtils = new ReportUtils(monthlyReports, yearlyReports);

        while(true){
            String enteredValue = mainMenu();
            switch (enteredValue){
                case "1":
                    reportUtils.readingMonthlyReportFiles();
                    break;
                case "2":
                    reportUtils.readingYearlyReportFiles();
                    break;
                case "3":
                    reportUtils.CheсkYearlyAndMonthlyReports();
                    break;
                case "4":
                    reportUtils.printInformationMonthlyReports();
                    break;
                case "5":
                    reportUtils.printInformationYearlyReports();
                    break;
                case "6":
                    System.out.println("Завершение программы");
                    return;
                default:
                    System.out.println("Вы ввели неверную команду '" + enteredValue + "'");
            }
        }
    }

    public static String mainMenu() {
        System.out.println();
        System.out.println("Выберите операцию:");
        System.out.println("1 - Считывание файлов месячных отчетов");
        System.out.println("2 - Считывание файлов годового отчета");
        System.out.println("3 - Сверка годового и месячных отчетов");
        System.out.println("4 - Вывод информации по месячным отчетам");
        System.out.println("5 - Вывод информации по годовому отчету");
        System.out.println("6 - Завершить работу приложения");
        return scanner.nextLine();
    }

}

