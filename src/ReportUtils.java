import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReportUtils {

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
                    "файл не находится в нужной директории.");
            return null;
        }
    }

}
