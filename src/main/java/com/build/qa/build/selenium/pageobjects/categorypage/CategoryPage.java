package com.build.qa.build.selenium.pageobjects.categorypage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.build.qa.build.selenium.pageobjects.BasePage;

public class CategoryPage extends BasePage {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryPage.class);
    private By categoryContent;

    public CategoryPage(WebDriver driver, Wait<WebDriver> wait) {
        super(driver, wait);
        categoryContent = By.id("category-content");
    }

    public boolean onCategoryContent() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(categoryContent)) != null;
    }

    /**
     * This method selects the product from the list displayed on category drop page.
     * @param index The order of product to be selected
     * @return Title of the item selected
     */
    public String pickProductByIndex(int index) {
        String titleXPath = String.format("(//div[contains(@class,'product-title-description')]/div[contains(@class,'product-title')])[%d]", index);
        By titleLocator = By.xpath(titleXPath);
        String productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(titleLocator)).getText();

        String productXPath = String.format("//*[@id='category-product-drop']/*[starts-with(@id,'product-composite-')][%d]", index);
        By productLocator = By.xpath(productXPath);
        wait.until(ExpectedConditions.presenceOfElementLocated(productLocator)).click();
        LOG.info("Product is selected. Title : '{}' - Index : '{}'", productTitle, index);

        return productTitle;
    }
}
