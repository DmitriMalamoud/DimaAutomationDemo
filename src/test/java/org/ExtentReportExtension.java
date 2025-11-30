package org;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.extension.*;
import org.testinfra.Logger;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ExtentReportExtension implements BeforeAllCallback, AfterAllCallback,
        BeforeEachCallback, AfterTestExecutionCallback {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void beforeAll(ExtensionContext context) {
        String reportPath = Paths.get("target",
                String.format("automation-report-%d.html",
                        LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()))
                .toString();
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String testName = context.getDisplayName();
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
        Logger.get().init(test.get());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            Throwable t = context.getExecutionException().get();
            test.get().fail(t);
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
