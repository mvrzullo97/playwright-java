package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.Assertions;

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

    public void compileCustomerForm(String firstName, String lastName, String dateBirth, String country, String postalCode, String houseNumber, String phoneNumber, String email, String password) {
        this.firstName.fill(firstName);
        this.lastName.fill(lastName);
        this.birthDate.fill(dateBirth);
        this.country.selectOption(country);
        this.postalCode.fill(postalCode);
        page.waitForResponse(
                response -> response.url().contains("/postcode-lookup")
                        && response.status() == 200,
                () -> {
                    this.houseNumber.fill(houseNumber);
                });
        this.phoneNumber.fill(phoneNumber);
        this.email.fill(email);
        this.password.fill(password);
    }

    public void submitRegistrationForm() {
        Response response = page.waitForResponse(
                res -> res.url().contains("/users/register")
                        && res.request().method().equals("POST"),
                () -> page.getByTestId("register-submit").click()
        );
        org.assertj.core.api.Assertions.assertThat(response.status()).isEqualTo(201);
        page.waitForURL("**/auth/login");
    }

    public void checkAutoFill() {
        Assertions.assertFalse(page.getByLabel("Street").inputValue().isBlank());
        Assertions.assertFalse(page.getByLabel("City").inputValue().isBlank());
        Assertions.assertFalse(page.getByLabel("State").inputValue().isBlank());
    }

    public void checkSuccessfulRegistration() {
        PlaywrightAssertions.assertThat(page)
                .hasURL("https://practicesoftwaretesting.com/auth/login");
    }
}
