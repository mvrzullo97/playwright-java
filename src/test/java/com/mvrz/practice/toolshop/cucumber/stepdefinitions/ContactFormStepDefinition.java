package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.mvrz.practice.toolshop.catalog.pageobjects.ContactForm;
import com.mvrz.practice.toolshop.catalog.pageobjects.NavigationBar;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

public class ContactFormStepDefinition {

    ContactForm contactForm;
    NavigationBar navigationBar;

    @Before
    public void setUpPageObjects() {
        contactForm = new ContactForm(PlaywrightCucumberFixtures.getPage());
        navigationBar = new NavigationBar(PlaywrightCucumberFixtures.getPage());
    }

    @When("he compiles correctly the contact form")
    public void heCompilesTheContactForm() {
        contactForm.setFirstName("Alessio");
        contactForm.setLastName("Marzullo");
        contactForm.setEmail("alealeale@gmail.com");
        contactForm.setSubject("Payments");
        contactForm.setMessage("NeverGiveUp NeverGiveUp NeverGiveUp NeverGiveUp NeverGiveUp");
    }

    @And("he submits the contact form")
    public void heSubmitsTheContactForm() {
        contactForm.submitForm();
    }

    @Then("the contact message {string} should be displayed")
    public void theContactMessageShouldBeDisplayed(String expectedMessage) {
        Assertions.assertThat(contactForm.getConfirmMessage()).contains(expectedMessage);
    }

    @And("he deletes the contact field {string}")
    public void heDeletesTheContactField(String deletedField) {
        contactForm.clearField(deletedField);
    }

    @Then("the error message {string} should be displayed")
    public void theErrorMessageShouldBeDisplayed(String expectedMessage) {
        Assertions.assertThat(contactForm.getMandatoryFieldMessage()).contains(expectedMessage);
    }
}
