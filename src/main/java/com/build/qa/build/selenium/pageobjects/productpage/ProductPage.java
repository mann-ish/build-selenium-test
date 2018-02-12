package com.build.qa.build.selenium.pageobjects.productpage;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.build.qa.build.selenium.pageobjects.BasePage;

public class ProductPage extends BasePage{
    private static final Logger LOG = LoggerFactory.getLogger(ProductPage.class);
    private By productContent;

    public ProductPage(WebDriver driver, Wait<WebDriver> wait) {
        super(driver, wait);
        productContent = By.id("product");
    }

    public boolean onProductContent() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(productContent)) != null;
    }

    public String readProductName() {
        By headingLocator = By.id("heading");
        String productName = wait.until(ExpectedConditions.presenceOfElementLocated(headingLocator)).getText();
        LOG.info("Found product '{}' on product page.", productName);
        return wait.until(ExpectedConditions.presenceOfElementLocated(headingLocator)).getText();
    }

    public void addToCart() {
        LOG.info("Adding product to cart.");
        By addToCartButtonLocator = By.xpath("//*[@id='add-to-cart-wrap']/button");
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator)).click();

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(addToCartButtonLocator));
        } catch (TimeoutException timeoutException) {
            LOG.warn("Clicking on add to cart did not work. So retrying clicking again.");
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator)).click();
        }
        LOG.info("Finished adding product.");
    }

    public void proceedToCart() {
        LOG.info("Starting proceed to cart.");
        By proceedToCartButtonLocator = By.xpath("//a[contains(@class,'js-proceed-to-cart')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(proceedToCartButtonLocator)).click();
        LOG.info("Finished proceed to cart.");
    }
}
