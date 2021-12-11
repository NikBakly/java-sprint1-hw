import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportUtils {
    private HashMap<String, MonthlyReport> monthlyReports;
    private HashMap<Integer, YearlyReport> yearlyReports;

    public ReportUtils(HashMap<String, MonthlyReport> monthlyReports,
                       HashMap<Integer, YearlyReport> yearlyReports) {
        this.monthlyReports = monthlyReports;
        this.yearlyReports = yearlyReports;

    }

    public String getMonthForWord(String monthWord) {
        switch (monthWord) {
            case "January":
                return  "Январь";
            case "February":
                return  "Февраль";
            case "March":
                return "Март";
        }
        return null;
    }

    public String getMonthForNumber(String monthNumber) {
        switch (monthNumber) {
            case "01":
                return  "Январь";
            case "02":
                return  "Февраль";
            case "03":
                return "Март";
        }
        return null;
    }

    public String readFileContentsOrNullMonthlyReport(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, " +
                    path + " файл не находится в нужной директории.");
            return null;
        }
    }

    public void readingMonthlyReportFiles(){
        monthlyReports.put("January", new MonthlyReport("resources\\m.202101.csv", this));
        monthlyReports.put("February", new MonthlyReport("resources\\m.202102.csv", this));
        monthlyReports.put("March", new MonthlyReport("resources\\m.202103.csv", this));
    }

    public void readingYearlyReportFiles(){
        yearlyReports.put(2021, new YearlyReport("resources\\y.2021.csv", this));
    }

    public void CheсkYearlyAndMonthlyReports(){
        boolean flag = false;
        for (String month : monthlyReports.keySet()) {
            int sumYearlyExpenses = 0;
            int sumYearlyIncome = 0;
            int sumMonthlyExpenses = 0;
            int sumMonthlyIncome = 0;
            for (Product productMonth :
                    monthlyReports.get(month).getLineContentsMonthlyReports()) {
                //Считываем данные со строки
                //Сохраняем данные
                boolean isExpenseMonthly = productMonth.isExpense();
                int amountMonth = productMonth.getAmount();
                // высчитываем суссу расходов и доходов за месяц
                if (isExpenseMonthly)
                    sumMonthlyExpenses += amountMonth;
                else
                    sumMonthlyIncome += amountMonth;
            }
            //выбираем номер месяца
            String numberMonth = "";
            switch (month) {
                case "January":
                    numberMonth = "01";
                    break;
                case "February":
                    numberMonth = "02";
                    break;
                case "March":
                    numberMonth = "03";
                    break;
            }
            // этот цикл оставлен на будущие отчеты за 2022 год, 2023 год и т.д.
            for (int year : yearlyReports.keySet()) {
                for (Product productYear :
                        yearlyReports.get(year).getLineContentsYearlyReport()) {
                    // сравниваем расходы и доходы с определенным месяцем (numberMonth)
                    if (productYear.getProduct().equals(numberMonth)) {
                        int amountYear = productYear.getAmount();
                        boolean isExpenseYearly = productYear.isExpense();
                        if (isExpenseYearly)
                            sumYearlyExpenses = amountYear;
                        else
                            sumYearlyIncome = amountYear;
                    }
                }
                if (sumMonthlyExpenses != sumYearlyExpenses
                        || sumMonthlyIncome != sumYearlyIncome) {
                    System.out.println("Обнаружено несоответствие в " + month + " месяце.");
                    System.out.println();
                    flag = true;
                }
            }
        }
        if (!flag) {
            System.out.println("Операция прошла успешно. В отчётах нету расхождений.");
            System.out.println();
        }
    }

    public void printInformationMonthlyReports(){
        for (String month :
                monthlyReports.keySet()) {
            String monthRussian = getMonthForWord(month);

            System.out.println("Месяц: " + monthRussian);
            int sumMaxInclome = 0;
            String profitableProduct = "";
            int sumMaxExpense = 0;
            String consumableProduct = "";
            for (Product productMonth :
                    monthlyReports.get(month).getLineContentsMonthlyReports()) {
                String productName = productMonth.getProduct();
                boolean isExpense = productMonth.isExpense();
                int amountMonth = productMonth.getAmount();
                //находим самую большую трату
                if (isExpense) {
                    if (sumMaxExpense < (amountMonth)) {
                        sumMaxExpense = amountMonth;
                        consumableProduct = productName;
                    }
                    // находим самый прибыльный товар
                } else {
                    if (sumMaxInclome < (amountMonth)) {
                        sumMaxInclome = amountMonth;
                        profitableProduct = productName;
                    }
                }
            }
            System.out.println("Самый прибыльный товар: " + profitableProduct + "," +
                    " его цена: " + sumMaxInclome + ';');
            System.out.println("Самая большая трата за товар: " + consumableProduct + "," +
                    " его цена: " + sumMaxExpense + ';');
            System.out.println("-------------");

        }
    }

    public void printInformationYearlyReports(){
        for (int year :
                yearlyReports.keySet()) {
            System.out.println("Год: " + year);
            int profit = 0; //прибыль
            double averageExpense;
            int sumExpeneses = 0;
            double averageIncome;
            int sumIncome = 0;
            double countMonth = 0;
            //копируем массив из объекта YearlyReport для дальнейшего удобного использования
            List<Product> productsYear = new ArrayList<>();
            productsYear.
                    addAll(yearlyReports.get(year).getLineContentsYearlyReport());

            for (int i = 0;
                 i < productsYear.size() - 1; i += 2) {
                ++countMonth;

                boolean firstIsExpense = productsYear.get(i).isExpense();
                boolean secondIsExpense = productsYear.get(i+1).isExpense();
                // расчет прибыли и средних значений расхода и дохода
                if (firstIsExpense == false && secondIsExpense == true) {
                    profit = productsYear.get(i).getAmount()
                            - productsYear.get(i+1).getAmount();
                    sumExpeneses += productsYear.get(i+1).getAmount();
                    sumIncome += productsYear.get(i).getAmount();

                } else if (firstIsExpense == true && secondIsExpense == false) {
                    profit = productsYear.get(i+1).getAmount()
                            - productsYear.get(i).getAmount();
                    sumExpeneses += productsYear.get(i).getAmount();
                    sumIncome += productsYear.get(i+1).getAmount();
                }
                // Под .getProduct подразумевается передача номера месяца
                String month = getMonthForNumber(productsYear.get(i).getProduct());
                System.out.println("Прибыль за " + month + ": " + profit + ';');
            }
            averageExpense = sumExpeneses / countMonth;
            System.out.println("Средний расход за все месяцы в году: " + averageExpense);
            averageIncome = sumIncome / countMonth;
            System.out.println("Средний доход за все месяцы в году: " + averageIncome);
            System.out.println();
        }
    }



}
