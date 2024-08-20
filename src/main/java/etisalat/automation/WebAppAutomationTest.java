package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.time.Duration;
import java.util.List;

public class WebAppAutomationTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    @BeforeClass
    public void setUp() {
        System.setProperty("webDriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        // Initialize reporting
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }

    @Test
    public void testLoginAndNavigate() {
        extentTest = extentReports.createTest("Login and Navigation Test");

        // Open login page
        driver.get("https://the-internet.herokuapp.com/login");
        extentTest.info("Opened login page.");

        // Login
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("NouranHM");
        passwordField.sendKeys("Letsgo123!");
        loginButton.click();

        // Wait for secure area
        wait.until(ExpectedConditions.urlContains("/secure"));
        extentTest.info("Logged in successfully.");

        // Check for secure area content
        WebElement secureAreaMessage = driver.findElement(By.cssSelector(".flash.success"));
        Assert.assertTrue(secureAreaMessage.isDisplayed(), "Secure area message is not displayed.");

        // Navigate to tables page
        driver.get("https://the-internet.herokuapp.com/tables");
        extentTest.info("Opened tables page.");

        // Extract data from table
        WebElement table = driver.findElement(By.id("table1"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            System.out.println(row.getText());
        }

        // Navigate to inputs page and submit form
        driver.get("https://the-internet.herokuapp.com/inputs");
        extentTest.info("Opened inputs page.");
        WebElement inputField = driver.findElement(By.cssSelector("input[type='number']"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        inputField.sendKeys("12345");
        submitButton.click();
        extentTest.info("Filled out and submitted form.");

        // Log out
        driver.get("https://the-internet.herokuapp.com/logout");
        extentTest.info("Logged out successfully.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extentReports.flush();
    }
}

