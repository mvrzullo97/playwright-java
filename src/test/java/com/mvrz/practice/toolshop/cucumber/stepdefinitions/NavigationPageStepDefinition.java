package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.mvrz.practice.toolshop.catalog.pageobjects.NavigationBar;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class NavigationPageStepDefinition {
    NavigationBar navigationBar;

    @Before
    public void setupPageObjects() {
        navigationBar = new NavigationBar(PlaywrightCucumberFixtures.getPage());
    }

    @Given("A Customer is on the Home Page")
    public void aCustomerIsOnTheHomePage() {
        navigationBar.openHomePage();
    }

    @And("he goes to Login Page")
    public void heGoesToLoginPage() {
        navigationBar.goToLogInPage();
    }

    @And("he goes to Home Page")
    public void heGoesToHomePage() {
        navigationBar.goToHomePage();
    }
}
