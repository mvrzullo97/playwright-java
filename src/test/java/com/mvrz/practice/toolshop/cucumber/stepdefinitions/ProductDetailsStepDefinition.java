package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.mvrz.practice.toolshop.catalog.pageobjects.ProductDetails;
import com.mvrz.practice.toolshop.catalog.pageobjects.ProductList;
import com.mvrz.practice.toolshop.catalog.pageobjects.SearchComponent;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class ProductDetailsStepDefinition {
    SearchComponent searchComponent;
    ProductList productList;
    ProductDetails productDetails;

    @Before
    public void setupPageObjects() {
        searchComponent = new SearchComponent(PlaywrightCucumberFixtures.getPage());
        productList = new ProductList(PlaywrightCucumberFixtures.getPage());
        productDetails = new ProductDetails(PlaywrightCucumberFixtures.getPage());
    }

    @And("he selects {string}")
    public void heSelects(String productName) {
        productList.selectProductFromList(productName);
    }

    @Then("the product details of {string} should be displayed")
    public void theProductDetailsOfShouldBeDisplayed(String expectedProductName) {
        String actualProductName = productDetails.getProductName();
        Assertions.assertEquals(actualProductName, expectedProductName);
    }

    @Then("the product should not be able to added to the cart")
    public void theProductShouldNotBeAbleToAddedToTheCart() {
        productDetails.checkOutOfStockProduct();
    }

    @Then("the co2 active rate should be {string}")
    public void theCoActiveRateShouldBe(String expectedCo2Rate) {
        String actualCo2ActiveRate = productDetails.getCo2ActiveRate();
        Assertions.assertEquals(actualCo2ActiveRate, expectedCo2Rate);
    }

    @And("he increases the product quantity of {int}")
    public void heIncreasesTheProductQuantityOf(int quantity) {
        productDetails.selectQuantity(quantity);
    }

    @And("adds product to the cart")
    public void addsProductToTheCart() {
        productDetails.addProductInCart();
    }

    @Then("the cart icon should be displayed with quantity of {int}")
    public void theCartIconShouldBeDisplayedWithQuantityOf(int expectedQuantity) {
        Assertions.assertEquals(
                productDetails.getProductQuantityInTheCart(),
                expectedQuantity);
    }
}
