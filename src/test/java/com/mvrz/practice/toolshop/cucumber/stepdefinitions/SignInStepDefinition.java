package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.mvrz.practice.toolshop.catalog.pageobjects.*;
import com.mvrz.practice.toolshop.customer.CustomerForm;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

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

    public String generateRandomString(int size) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        return new Random().ints(size, 0, chars.length())
                .mapToObj(chars::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    @Given("a new random customer registers your account and logs in")
    public void registerNewCustomer() {
        navigationBar.openHomePage();
        navigationBar.goToLogInPage();
        navigationBar.goToRegistrationPage();
        String email = generateRandomString(5) +"ABC@gmail.com";
        String password = generateRandomString(5) + "ABC@12345";

        customerForm.compileCustomerForm(
                "Random",
                "Customer",
                "2000-10-10",
                "Italy",
                "86170",
                "100",
                "111111111",
                    email,
                    password
                );
        customerForm.checkAutoFill();
        customerForm.submitRegistrationForm();
        customerForm.checkSuccessfulRegistration();
        logInSuccessfullyWith(email, password);
    }

    @When("he inserts invalid credentials")
    public void logInWithInvalidCredentials() {
        String email = generateRandomString(10) +"@gmail.com";
        String password = generateRandomString(5) + "@12345";
        logInForm.insertCredentials(email, password);
    }

    @And("tries to submit the form")
    public void tryToSubmitTheForm() {
        logInForm.submitLoginForm();
    }

    @When("he logs in successfully with {string} and {string}")
    public void logInSuccessfullyWith(String email, String password) {
        logInForm.insertCredentials(email, password);
        logInForm.submitLoginForm();
        logInForm.checkLoginSuccessfully();
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
        navigationBar.goToRegistrationPage();
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
                generateRandomString(5) + "ale.marz@gmail.com",
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
