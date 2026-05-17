package com.assignment.utils;

import com.assignment.tests.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        
        WebDriver driver = ((BaseTest) testClass).getDriver();

        if (driver != null) {

            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = result.getName() + "_" + timestamp + ".png";
            File destination = new File("screenshots/" + fileName);
            
            try {

                destination.getParentFile().mkdirs();
                Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Error! Screen capture saved to: " + destination.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}