package com.trung.helper;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import static com.trung.helper.IntegrationAPIGmail.formatTestResults;
import static com.trung.helper.SpreadSheetIntegration.writeData;

public class CustomExtentListener extends ExtentITestListenerClassAdapter {
    private Map<String, TestResult> testResults = new HashMap<>();
    private Map<String, Long> testStartTimes = new HashMap<>();

    @Override
    public void onTestStart(ITestResult result) {
        testStartTimes.put(result.getName(), System.currentTimeMillis());
        super.onTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long duration = calculateDuration(result.getName());
        testResults.put(result.getName(), new TestResult("Passed", duration));
        super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        long duration = calculateDuration(result.getName());
        testResults.put(result.getName(), new TestResult("Failed", duration));
        super.onTestFailure(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        long duration = calculateDuration(result.getName());
        testResults.put(result.getName(), new TestResult("Skipped", duration));
        super.onTestSkipped(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        super.onFinish(context);
        try {
            writeData(testResults);
            String message = IntegrationAPIGmail.formatTestResults(testResults);
            new IntegrationAPIGmail().sendMail("New Report",message);

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private long calculateDuration(String testName) {
        long startTime = testStartTimes.getOrDefault(testName, System.currentTimeMillis());
        return System.currentTimeMillis() - startTime;
    }
}
