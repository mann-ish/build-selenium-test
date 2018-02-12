package com.build.qa.build.selenium.pageobjects.homepage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import com.build.qa.build.selenium.pageobjects.BasePage;

public class HomePage extends BasePage {
	
	private By buildThemeBody;
	
	public HomePage(WebDriver driver, Wait<WebDriver> wait) {
		super(driver, wait);
		buildThemeBody = By.cssSelector("body.build-theme");
	}
	
	public boolean onBuildTheme() { 
		return wait.until(ExpectedConditions.presenceOfElementLocated(buildThemeBody)) != null;
	}
	
	public void searchFor(String productName) {
	    By searchTextBox = By.id("search_txt");
	    WebElement searchTextBoxElement = wait.until(ExpectedConditions.presenceOfElementLocated(searchTextBox));
	    searchTextBoxElement.sendKeys(productName);
	    searchTextBoxElement.sendKeys(Keys.ENTER);
	}

	public boolean foundItem(String productName) {
	    By productTitle = By.id("heading");
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(productTitle, productName)) != null;
	}
}
