package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.mvrz.practice.toolshop.catalog.pageobjects.*;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class ProductDetailsStepDefinition {
    SearchComponent searchComponent;
    ProductList productList;
    ProductDetails productDetails;
    FavouriteList favouriteList;
    NavigationBar navigationBar;

    @Before
    public void setupPageObjects() {
        searchComponent = new SearchComponent(PlaywrightCucumberFixtures.getPage());
        productList = new ProductList(PlaywrightCucumberFixtures.getPage());
        productDetails = new ProductDetails(PlaywrightCucumberFixtures.getPage());
        favouriteList = new FavouriteList(PlaywrightCucumberFixtures.getPage());
        navigationBar = new NavigationBar(PlaywrightCucumberFixtures.getPage());
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

    @And("he adds product to the cart")
    public void addsProductToTheCart() {
        productDetails.addProductInCart();
    }

    @Then("the cart icon should be displayed with quantity of {int}")
    public void theCartIconShouldBeDisplayedWithQuantityOf(int expectedQuantity) {
        Assertions.assertEquals(
                productDetails.getProductQuantityInTheCart(),
                expectedQuantity);
    }

    @And("add the product to the Favourite List")
    public void addProductToTheFavouriteList() {
        productDetails.addProductToFavouriteList();
    }

    @Then("the {string} product should be displayed in the List")
    public void theProductShouldBeDisplayedInTheList(String productName) {
        navigationBar.goToFavouriteList();
        favouriteList.checkProductInList(productName);
    }

    @And("the cart icon should not be visible")
    public void cartIconShouldNotBeVisible() {
        Assertions.assertFalse(productDetails.isCartIconVisible());
    }

    @When("he deletes the product form the cart")
    public void heDeletesTheProductFormTheCart() {
        productDetails.deleteProductFromTheCart();
    }

    @Then("the product {string} should not be displayed into the cart")
    public void theProductShouldNotBeDisplayedIntoTheCart(String productName) {
        Assertions.assertFalse(productDetails.productIsPresentInTheCart(productName));
    }
}
