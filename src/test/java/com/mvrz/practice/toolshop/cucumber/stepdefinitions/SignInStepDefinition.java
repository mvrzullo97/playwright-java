package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.mvrz.practice.toolshop.catalog.pageobjects.*;
import com.mvrz.practice.toolshop.customer.CustomerForm;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.junit.Assert;

import java.util.Random;
import java.util.stream.Collectors;

public class SignInStepDefinition {

    SearchComponent searchComponent;
    ProductList productList;
    ProductDetails productDetails;
    NavigationBar navigationBar;
    LogInForm logInForm;
    CustomerForm customerForm;

    @Before
    public void setupPageObjects() {
        searchComponent = new SearchComponent(PlaywrightCucumberFixtures.getPage());
        productList = new ProductList(PlaywrightCucumberFixtures.getPage());
        productDetails = new ProductDetails(PlaywrightCucumberFixtures.getPage());
        navigationBar = new NavigationBar(PlaywrightCucumberFixtures.getPage());
        logInForm = new LogInForm(PlaywrightCucumberFixtures.getPage());
        customerForm = new CustomerForm(PlaywrightCucumberFixtures.getPage());
    }

    public String generateRandomString() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        return new Random().ints(10, 0, chars.length())
                .mapToObj(chars::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }


    @Given("On-Line Customer is on the Login Page")
    public void customerIsOnTheLoginPage() {
        navigationBar.openSignInPage();
    }


    @When("he logs with {string} and {string}")
    public void logInWith(String email, String password) {
        logInForm.insertCredentials(email, password);
        logInForm.submitLoginForm();
    }

    @Then("an error message {string} should be displayed")
    public void anErrorMessageShouldBeDisplayed(String errorMessage) {
        Assertions.assertThat(logInForm.getErrorMessage())
                .isEqualTo(
                errorMessage
        );
    }

    @When("he deletes the field {string}")
    public void DeleteTheField(String deletedField) {
        logInForm.deleteField(deletedField);
    }

    @Then("the account page should be displayed")
    public void theAccountPageShouldBeDisplayed() {
        logInForm.checkLoginSuccessfully();
    }

    @And("he goes to the Registration Page")
    public void goToRegistrationPage() {
        navigationBar.openRegistrationPage();
    }

    @When("he compiles correctly the Registration Form")
    public void compileRegistrationForm() {
        customerForm.compileCustomerForm(
                "Alessio",
                "Marzullo",
                "1997-11-14",
                "Italy",
                "86170",
                "205",
                "1110000678",
                "ale.marz@gmail.com",
                "Marz@777");
        customerForm.checkAutoFill();
    }

    @And("submits the Registration Form")
    public void submitTheRegistrationForm() {customerForm.submitRegistrationForm();}

    @Then("the Login Page should be displayed")
    public void theLoginPageShouldBeDisplayed() {
        Assertions.assertThat(navigationBar.getActualPage())
                .isEqualTo("https://practicesoftwaretesting.com/auth/login");
    }
}
