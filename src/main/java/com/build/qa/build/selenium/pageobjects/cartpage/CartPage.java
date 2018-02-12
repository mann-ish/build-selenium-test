package com.build.qa.build.selenium.pageobjects.cartpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.build.qa.build.selenium.pageobjects.BasePage;

public class CartPage extends BasePage{

    private By cartPageContent;

    public CartPage(WebDriver driver, Wait<WebDriver> wait) {
        super(driver, wait);
        cartPageContent = By.cssSelector("div.cart-page");
    }

    public boolean onCartPageContent() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(cartPageContent)) != null;
    }

    public String readFirstProductName() {
        By headingLocator = By.cssSelector("a.item-title");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator)).getText();
    }
}
