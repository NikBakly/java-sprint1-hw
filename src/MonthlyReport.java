import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonthlyReport {
    private List<String[]> lineContentsMonthlyReports = new ArrayList<>();

    public MonthlyReport(String path) {
        if (readFileContentsOrNullMonthlyReport(path) != null) {
            String[] lines = readFileContentsOrNullMonthlyReport(path).split("\r\n");
            for (int i = 1; i < lines.length; ++i) {
                this.lineContentsMonthlyReports.add(lines[i].split(","));
            }
        }
    }

    private String readFileContentsOrNullMonthlyReport(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, " +
                    "файл не находится в нужной директории.");
            return null;
        }
    }

    public List<String[]> getLineContentsMonthlyReports() {
        return lineContentsMonthlyReports;
    }

    @Override
    public String toString() {
        return "MonthlyReport{" +
                "lineContentsMonthlyReports=" + lineContentsMonthlyReports +
                '}';
    }
}
