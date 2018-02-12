package com.build.qa.build.selenium.pageobjects.emailpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.build.qa.build.selenium.pageobjects.BasePage;

public class EmailPage extends BasePage{

    private By emailPageContent;

    public EmailPage(WebDriver driver, Wait<WebDriver> wait) {
        super(driver, wait);
        emailPageContent = By.id("cart-email");
    }

    public boolean onEmailPageContent() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailPageContent)) != null;
    }

    public void populateYourName(String yourName) {
        By yourNameLocator = By.id("yourName");
        wait.until(ExpectedConditions.visibilityOfElementLocated(yourNameLocator)).sendKeys(yourName);
    }

    public void populateYourEmail(String email) {
        By yourEmailLocator = By.id("yourEmail");
        wait.until(ExpectedConditions.visibilityOfElementLocated(yourEmailLocator)).sendKeys(email);
    }

    public void populateRecipientName(String recipientName) {
        By recipientNameLocator = By.id("recipientName");
        wait.until(ExpectedConditions.visibilityOfElementLocated(recipientNameLocator)).sendKeys(recipientName);
    }

    public void populateRecipientEmail(String recipientEmail) {
        By recipientEmailLocator = By.id("recipientEmail");
        wait.until(ExpectedConditions.visibilityOfElementLocated(recipientEmailLocator)).sendKeys(recipientEmail);
    }

    public void populateMessage(String message) {
        By messageLocator = By.id("quoteMessage");
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator)).sendKeys(message);
    }

    public void sendEmail() {
        By messageLocator = By.xpath("//button[contains(@class, 'js-email-cart-submit-button')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator)).click();
    }

    public boolean emailSentSuccess() {
        By messageLocator = By.xpath("//li[text()='Cart Sent! The cart has been submitted to the recipient via email.']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator)) != null;
    }
}
