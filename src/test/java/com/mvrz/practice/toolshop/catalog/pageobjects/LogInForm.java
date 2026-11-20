package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class LogInForm {

    private final Page page;

    public LogInForm(Page page) {this.page = page;}

    public void insertCredentials(String email, String password) {
        page.getByLabel("Email address *").fill(email);
        page.getByLabel("Password *").fill(password);
    }

    public void submitLoginForm() {page.getByTestId("login-submit").click();}

    public String getErrorMessage() {return page.locator(".alert-danger").textContent();}

    public void deleteField(String fieldName) {page.getByPlaceholder(fieldName).clear();}

    public void checkLoginSuccessfully() {
        PlaywrightAssertions.assertThat(page)
                .hasURL("https://practicesoftwaretesting.com/account");
    }
}
