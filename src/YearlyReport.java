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
        if (Files.exists(Path.of(path))) {
            String[] lines = reportUtils.readFileContentsOrNullMonthlyReport(path).split("\r\n");
            for (int i = 1; i < lines.length; ++i) {
                this.lineContentsYearlyReport.add(new Product(lines[i].split(",")));
            }
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
