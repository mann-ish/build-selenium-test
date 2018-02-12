package com.build.qa.build.selenium.tests;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.build.qa.build.selenium.framework.BaseFramework;
import com.build.qa.build.selenium.pageobjects.cartpage.CartPage;
import com.build.qa.build.selenium.pageobjects.categorypage.CategoryPage;
import com.build.qa.build.selenium.pageobjects.emailpage.EmailPage;
import com.build.qa.build.selenium.pageobjects.homepage.HomePage;
import com.build.qa.build.selenium.pageobjects.productpage.ProductPage;

public class BuildTest extends BaseFramework {
    private static final Logger LOG = LoggerFactory.getLogger(BuildTest.class);

	/** 
	 * Extremely basic test that outlines some basic
	 * functionality and page objects as well as assertJ
	 */
	@Test
	public void navigateToHomePage() { 
		driver.get(getConfiguration("HOMEPAGE"));
		HomePage homePage = new HomePage(driver, wait);
		
		softly.assertThat(homePage.onBuildTheme())
			.as("The website should load up with the Build.com desktop theme.")
			.isTrue();
	}
	
	/** 
	 * Search for the Quoizel MY1613 from the search bar
	 * @assert: That the product page we land on is what is expected by checking the product title
	 * @difficulty Easy
	 */
	@Test
	public void searchForProductLandsOnCorrectProduct() {
	    String productName = "Quoizel MY1613";
	    driver.get(getConfiguration("HOMEPAGE"));
	    closeSubscriberDialogueIfDisplayed();
        HomePage homePage = new HomePage(driver, wait);
        homePage.searchFor(productName);
        softly.assertThat(homePage.foundItem(productName))
        .as("The website should be able to find product " + productName + ".")
        .isTrue();
	}
	
	/** 
	 * Go to the Bathroom Sinks category directly (https://www.build.com/bathroom-sinks/c108504) 
	 * and add the second product on the search results (Category Drop) page to the cart.
	 * @assert: the product that is added to the cart is what is expected
	 * @difficulty Easy-Medium
	 */
	@Test
	public void addProductToCartFromCategoryDrop() {
	    driver.get("https://www.build.com/bathroom-sinks/c108504");
        CategoryPage categoryPage = new CategoryPage(driver, wait);
        softly.assertThat(categoryPage.onCategoryContent())
        .as("The website should load up with the category content.")
        .isTrue();

        closeSubscriberDialogueIfDisplayed();
        String selectedProductName = categoryPage.pickProductByIndex(2);

        ProductPage productPage = new ProductPage(driver, wait);
        softly.assertThat(productPage.onProductContent())
        .as("The website should load up with the product content upon clicking on a product.")
        .isTrue();

        softly.assertThat(productPage.readProductName())
        .as("The product page should display the selected product from category page.")
        .isEqualTo(selectedProductName);
        productPage.addToCart();
        productPage.proceedToCart();

        CartPage cartPage = new CartPage(driver, wait);
        softly.assertThat(cartPage.onCartPageContent())
        .as("The website should load up with the cart page.")
        .isTrue();

        softly.assertThat(cartPage.readFirstProductName())
        .as("The cart should display the selected product.")
        .startsWith(selectedProductName);
	}

	/** 
	 * Add a product to the cart and email the cart to yourself, also to my email address: jgilmore+SeleniumTest@build.com
	 * Include this message in the "message field" of the email form: "This is {yourName}, sending you a cart from my automation!"
	 * @assert that the "Cart Sent" success message is displayed after emailing the cart
	 * @difficulty Medium-Hard
	 */
	@Test
	public void addProductToCartAndEmailIt() {
	    addProductToCartFromCategoryDrop();
        new CartPage(driver, wait).clickEmail();

        EmailPage emailPage = new EmailPage(driver, wait);
        softly.assertThat(emailPage.onEmailPageContent())
        .as("The website should load up with the email form.")
        .isTrue();

        emailPage.populateYourName("Manish Aatreya");
        emailPage.populateYourEmail("manish.aatreya@gmail.com");
        emailPage.populateRecipientName("Jared Gilmore");
        emailPage.populateRecipientEmail("jgilmore+SeleniumTest@build.com");
        emailPage.populateMessage("This is Manish Aatreya, sending you a cart from my automation!");
        emailPage.sendEmail();
        softly.assertThat(emailPage.emailSentSuccess())
        .as("The website should display success message for emailing the cart.")
        .isTrue();
	}

	/** 
	 * Go to a category drop page (such as Bathroom Faucets) and narrow by
	 * at least two filters (facets), e.g: Finish=Chromes and Theme=Modern
	 * @assert that the correct filters are being narrowed, and the result count
	 * is correct, such that each facet selection is narrowing the product count.
	 * @difficulty Hard
	 */
	@Test
	public void facetNarrowBysResultInCorrectProductCounts() { 
	    navigateToHomePage();
	    closeSubscriberDialogueIfDisplayed();
        new HomePage(driver, wait).goToBathRoomFaucet();
        CategoryPage categoryPage = new CategoryPage(driver, wait);
        closeSubscriberDialogueIfDisplayed();

        // Apply filter Finish=Chromes
        int chromeCount = categoryPage.count("Chromes");
        LOG.info("Chrome count : {}", chromeCount);
        categoryPage.selectFilter("Chromes");
        softly.assertThat(categoryPage.totalResultsCount())
        .as("The product count should be narrowed down by selected category of Finish=Chromes.")
        .isEqualTo(chromeCount);

        // Apply filter Theme=Modern
        int modernCount = categoryPage.count("Modern");
        LOG.info("Modern count : {}", modernCount);
        categoryPage.selectFilter("Modern");
        softly.assertThat(categoryPage.totalResultsCount())
        .as("The product count should be narrowed down by selected category of Theme=Modern.")
        .isEqualTo(modernCount);
	}
}
