import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        // Словарь месяц(ключ) - объект(значение).
        HashMap<String, MonthlyReport> monthlyReports = new HashMap<>();
        // Словарь год(ключ) - объект(значение).
        HashMap<Integer, YearlyReport> yearlyReports = new HashMap<>();
        ReportUtils reportUtils = new ReportUtils();
        ReportManager reportManager = new ReportManager();
        //чтение данных и формирование отчётов
        reportManager.managingReports(monthlyReports, yearlyReports, reportUtils);
    }

}

