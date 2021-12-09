import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Словарь месяц(ключ) - объект(значение).
        HashMap<String, MonthlyReport> monthlyReports = new HashMap<>();
        // Слолварь год(ключ) - объект(значение).
        HashMap<Integer, YearlyReport> yearlyReports = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Что хотите сделать:\n" +
                    "1.Считать все месячные отчёты;\n" + // считывание 3-х файлов месясов.csv
                    "2.Считать годовой отчёт;\n" +       // считывние только годового отчета
                    "3.Сверить отчёты;\n" +
                    "4.Вывести информацию о всех месячных отчётах;\n" +
                    "5.Вывести информацию о годовом отчёте;\n" +
                    "0.Выход;");
            int command = scanner.nextInt();

            if (command == 1) {
                monthlyReports.put("January", new MonthlyReport("resources\\m.202101.csv"));
                monthlyReports.put("February", new MonthlyReport("resources\\m.202102.csv"));
                monthlyReports.put("March", new MonthlyReport("resources\\m.202103.csv"));
            } else if (command == 2) {
                yearlyReports.put(2021, new YearlyReport("resources\\y.2021.csv"));
            } else if (command == 3) {
                boolean flag = false;
                for (String month : monthlyReports.keySet()) {
                    int sumYearlyExpenses = 0;
                    int sumYearlyIncome = 0;
                    int sumMonthlyExpenses = 0;
                    int sumMonthlyIncome = 0;
                    for (String[] lines :
                            monthlyReports.get(month).getLineContentsMonthlyReports()) {
                        // высчитываем расходы и доходы за месяц
                        boolean isExpenseMonthly = Boolean.parseBoolean(lines[1]);
                        int quantity = Integer.parseInt(lines[2]);
                        int sumOfOne = Integer.parseInt(lines[3]);
                        if (isExpenseMonthly)
                            sumMonthlyExpenses += quantity * sumOfOne;
                        else
                            sumMonthlyIncome += quantity * sumOfOne;
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
                        for (String[] linesContentsYearly :
                                yearlyReports.get(year).getLineContentsYearlyReport()) {
                            // сравниваем расходы и доходы с определенным месяцем (numberMonth)
                            if (linesContentsYearly[0].equals(numberMonth)) {
                                int amount = Integer.parseInt(linesContentsYearly[1]);
                                boolean isExpenseYearly =
                                        Boolean.parseBoolean(linesContentsYearly[2]);
                                if (isExpenseYearly)
                                    sumYearlyExpenses = amount;
                                else
                                    sumYearlyIncome = amount;
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

            } else if (command == 4) {
                for (String month :
                        monthlyReports.keySet()) {
                    String monthRussian = "";
                    switch (month) {
                        case "January":
                            monthRussian = "Январь";
                            break;
                        case "February":
                            monthRussian = "Февраль";
                            break;
                        case "March":
                            monthRussian = "Март";
                            break;
                    }
                    System.out.println("Месяц: " + monthRussian);
                    int sumMaxInclome = 0;
                    String profitableProduct = "";
                    int sumMaxExpense = 0;
                    String consumableProduct = "";
                    for (String[] lines :
                            monthlyReports.get(month).getLineContentsMonthlyReports()) {
                        String product = lines[0];
                        boolean isExpense = Boolean.parseBoolean(lines[1]);
                        int quantity = Integer.parseInt(lines[2]);
                        int sumOfOne = Integer.parseInt(lines[3]);
                        //находим самую большую трату
                        if (isExpense) {
                            if (sumMaxExpense < (quantity * sumOfOne)) {
                                sumMaxExpense = quantity * sumOfOne;
                                consumableProduct = product;
                            }
                            // находим самый прибыльный товар
                        } else {
                            if (sumMaxInclome < (quantity * sumOfOne)) {
                                sumMaxInclome = quantity * sumOfOne;
                                profitableProduct = product;
                            }
                        }
                    }
                    System.out.println("Самый прибыльный товар: " + profitableProduct + "," +
                            " его цена: " + sumMaxInclome + ';');
                    System.out.println("Самая большая трата за товар: " + consumableProduct + "," +
                            " его цена: " + sumMaxExpense + ';');
                    System.out.println("-------------");

                }

            } else if (command == 5) {
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
                    List<String[]> lineContentsYearlyReport = new ArrayList<>();
                    lineContentsYearlyReport.
                            addAll(yearlyReports.get(year).getLineContentsYearlyReport());

                    for (int i = 0;
                         i < lineContentsYearlyReport.size() - 1; i += 2) {
                        ++countMonth;
                        boolean firstIsExpense =
                                Boolean.parseBoolean(lineContentsYearlyReport.get(i)[2]);
                        boolean secondIsExpense =
                                Boolean.parseBoolean(lineContentsYearlyReport.get(i + 1)[2]);
                        // расчет прибыли и средних значений расхода и дохода
                        if (firstIsExpense == false && secondIsExpense == true) {
                            profit = Integer.parseInt(lineContentsYearlyReport.get(i)[1])
                                    - Integer.parseInt(lineContentsYearlyReport.get(i + 1)[1]);
                            sumExpeneses += Integer.parseInt(lineContentsYearlyReport.get(i + 1)[1]);
                            sumIncome += Integer.parseInt(lineContentsYearlyReport.get(i)[1]);

                        } else if (firstIsExpense == true && secondIsExpense == false) {
                            profit = Integer.parseInt(lineContentsYearlyReport.get(i + 1)[1])
                                    - Integer.parseInt(lineContentsYearlyReport.get(i)[1]);
                            sumExpeneses += Integer.parseInt(lineContentsYearlyReport.get(i)[1]);
                            sumIncome += Integer.parseInt(lineContentsYearlyReport.get(i + 1)[1]);
                        }
                        String month = "";
                        switch (lineContentsYearlyReport.get(i)[0]) {
                            case "01":
                                month = "Январь";
                                break;
                            case "02":
                                month = "Февраль";
                                break;
                            case "03":
                                month = "Март";
                                break;
                        }

                        System.out.println("Прибыль за " + month + ": " + profit + ';');
                    }
                    averageExpense = sumExpeneses / countMonth;
                    System.out.println("Средний расход за все месяцы в году: " + averageExpense);
                    averageIncome = sumIncome / countMonth;
                    System.out.println("Средний доход за все месяцы в году: " + averageIncome);
                    System.out.println();
                }
            } else if (command == 0) {
                System.out.println("Завершение программы");
                break;
            } else {
                System.out.println("К сожалению, такой команды не существует. Попробуйте снова.");
                System.out.println();
            }
        }
    }

}

