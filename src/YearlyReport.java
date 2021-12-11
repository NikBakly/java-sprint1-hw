import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class YearlyReport {
    private List<Product> lineContentsYearlyReport = new ArrayList<>();
    private ReportUtils reportUtils;
    public YearlyReport(String path, ReportUtils reportUtils) {
        this.reportUtils = reportUtils;
        if (readFileContentsOrNullYearlyReport(path) != null) {
            String[] lines = readFileContentsOrNullYearlyReport(path).split("\r\n");
            for (int i = 1; i < lines.length; ++i) {
                this.lineContentsYearlyReport.add(new Product(lines[i].split(",")));
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

    public List<Product> getLineContentsYearlyReport() {
        return lineContentsYearlyReport;
    }

    @Override
    public String toString() {
        return "YearlyReport{" +
                "lineContentsYearlyReport=" + lineContentsYearlyReport +
                '}';
    }
}
