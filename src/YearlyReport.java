import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.List;

public class YearlyReport {
    private List<String[]> lineContentsYearlyReport = new ArrayList<>();

    public YearlyReport(String path) {
        if (readFileContentsOrNullYearlyReport(path) != null) {
            String[] lines = readFileContentsOrNullYearlyReport(path).split("\r\n");
            for (int i = 1; i < lines.length; ++i) {
                this.lineContentsYearlyReport.add(lines[i].split(","));
            }
        }
    }

    private String readFileContentsOrNullYearlyReport(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно, " +
                    "файл не находится в нужной директории.");
            return null;
        }
    }

    public List<String[]> getLineContentsYearlyReport() {
        return lineContentsYearlyReport;
    }

    @Override
    public String toString() {
        return "YearlyReport{" +
                "lineContentsYearlyReport=" + lineContentsYearlyReport +
                '}';
    }
}
