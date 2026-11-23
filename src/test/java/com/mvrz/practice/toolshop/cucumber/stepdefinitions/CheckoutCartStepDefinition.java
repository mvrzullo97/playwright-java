package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.mvrz.practice.toolshop.catalog.pageobjects.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutCartStepDefinition {

    NavigationBar navigationBar;
    SearchComponent searchComponent;
    ProductList productList;
    ProductDetails productDetails;
    CheckoutCart checkoutCart;

    @Before
    public void setupPageObjects() {
        navigationBar = new NavigationBar(PlaywrightCucumberFixtures.getPage());
        searchComponent = new SearchComponent(PlaywrightCucumberFixtures.getPage());
        productList = new ProductList(PlaywrightCucumberFixtures.getPage());
        productDetails = new ProductDetails(PlaywrightCucumberFixtures.getPage());
        checkoutCart = new CheckoutCart(PlaywrightCucumberFixtures.getPage());
    }

    @Given("he selects {string} and adds it to the cart")
    public void selectProductAndAddToTheCart(String productName) {
        navigationBar.goToHomePage();
        searchComponent.searchBy(productName);
        productList.selectProductFromList(productName);
        productDetails.selectQuantity(2);
        productDetails.addProductInCart();
        navigationBar.goToCart();
        Assertions.assertThat(checkoutCart.checkOutItem()).isEqualTo(productName);
        assertTrue(productDetails.isCartIconVisible());
    }

    @Given("a Customer added {string} to the cart and compiled guest form")
    public void addProductToTheCartAndCompileGuestForm(String productName) {
        navigationBar.openHomePage();
        selectProductAndAddToTheCart(productName);
        assertTrue(checkoutCart.checkCurrentPhase("Cart"));
        proceedToCheckout();
        assertTrue(checkoutCart.checkCurrentPhase("Sign in"));
        compileGuestForm();
        submitGuestForm();
        proceedToCheckout();
        assertTrue(checkoutCart.checkCurrentPhase("Billing Address"));
    }

    @When("he proceeds to checkout")
    public void proceedToCheckout() {
        checkoutCart.clickCheckOutButton();
    }

    @Then("the checkout phase message {string} should be displayed")
    public void loggedCustomerMessageShouldBeDisplayed(String actualMessage) {
        Assertions.assertThat(checkoutCart.getMessage()).contains(actualMessage);
    }

    @Then("a log-in form should be displayed")
    public void logInFormShouldBeDisplayed() {
        checkoutCart.checkLogInForm();
    }

    @And("he compiles the guest form")
    public void compileGuestForm() {
        checkoutCart.compileForm(
                "guestaccount@gmail.com",
                "Guest",
                "Account");
    }

    @And("he submits the guest form")
    public void submitGuestForm() {
        checkoutCart.submitForm();
    }

    @And("he deletes {string}")
    public void deleteField(String fieldName) {
        checkoutCart.clearFieldName(fieldName);
    }

    @Then("the alert message {string} should be displayed")
    public void alertMessageShouldBeDisplayed(String actualAlert) {
        Assertions.assertThat(checkoutCart.getAlertMessage()).isEqualTo(actualAlert);
    }

    @When("he compiles the billing address form")
    public void compileBillingAddressForm() {
        checkoutCart.compileBillingForm(
                "Italy",
                "86170",
                "205");
    }

    @Then("the payment phase should be displayed")
    public void paymentPhaseShouldBeDisplayed() {
        assertTrue(checkoutCart.checkCurrentPhase("Payment"));
    }

    @And("he chooses the {string} payment method")
    public void choosePaymentMethod(String paymentMethod) {
        checkoutCart.compilePaymentMethod(paymentMethod);
    }

    @And("he confirms the payment")
    public void confirmPayment() {
        checkoutCart.clickOnConfirmButton();
    }

    @Then("the operation success message {string} should be displayed")
    public void theOperationSuccessMessageShouldBeDisplayed(String expectedMessage) {
        Assertions.assertThat(checkoutCart.getConfirmationMessage()).contains(expectedMessage);
    }
}
