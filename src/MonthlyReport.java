import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MonthlyReport {
    private List<Product> lineContentsMonthlyReports = new ArrayList<>();
    private ReportUtils reportUtils;

    public MonthlyReport(String path, ReportUtils reportUtils) {
        this.reportUtils = reportUtils;
        if (Files.exists(Path.of(path))) {
            String[] lines = reportUtils.readFileContentsOrNullMonthlyReport(path).split("\r\n");
            for (int i = 1; i < lines.length; ++i) {
                this.lineContentsMonthlyReports.add(new Product(lines[i].split(",")));
            }
        }
    }

    public List<Product> getLineContentsMonthlyReports() {
        return lineContentsMonthlyReports;
    }

    @Override
    public String toString() {
        return "MonthlyReport{" +
                "lineContentsMonthlyReports=" + lineContentsMonthlyReports +
                '}';
    }
}
