package com.mvrz.practice.toolshop.customer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;

public class CustomerForm {
    private final Page page;

    private final Locator firstName;
    private final Locator lastName;
    private final Locator birthDate;
    private final Locator country;
    private final Locator postalCode;
    private final Locator houseNumber;
    private final Locator phoneNumber;
    private final Locator email;
    private final Locator password;

    public CustomerForm(Page page) {
        this.page = page;
        this.firstName = page.getByLabel("First name");
        this.lastName = page.getByLabel("Last name");
        this.birthDate = page.getByLabel("Date of Birth *");
        this.country = page.getByLabel("Country");
        this.postalCode = page.getByLabel("Postal code");
        this.houseNumber = page.getByLabel("House number");
        this.phoneNumber = page.getByLabel("Phone");
        this.email = page.getByLabel("Email address");
        this.password = page.getByLabel("Password");
    }

    public void compileCustomerForm() {
        this.firstName.fill("Mario");
        this.lastName.fill("Rossi");
        this.birthDate.fill("1990-12-24");
        this.country.selectOption("Italy");
        this.postalCode.fill("50139");

        page.waitForResponse(
                response -> response.url().contains("/postcode-lookup")
                        && response.status() == 200,
                () -> {
                    this.houseNumber.fill("10C");
                });

        this.phoneNumber.fill("3334440187");
        this.email.fill("playwright.udemy@gmail.com");
        this.password.fill("Mario97Rossi@");
    }

    public void submitCustomerForm() {
        page.getByTestId("register-submit").click();
    }

    public void checkAutoFill() {
        Assertions.assertFalse(page.getByLabel("Street").inputValue().isBlank());
        Assertions.assertFalse(page.getByLabel("City").inputValue().isBlank());
        Assertions.assertFalse(page.getByLabel("State").inputValue().isBlank());
    }
}
