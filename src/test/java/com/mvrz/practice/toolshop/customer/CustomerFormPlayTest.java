package com.mvrz.practice.toolshop.customer;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.mvrz.practice.exercises.utils.HeadlessChromeOptions;
import com.mvrz.practice.toolshop.catalog.pageobjects.NavigationBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@UsePlaywright(HeadlessChromeOptions.class)
public class CustomerFormPlayTest {

    NavigationBar navigationBar;
    CustomerForm customerForm;

    @BeforeEach
    void openToolShopPage(Page page) {
        customerForm = new CustomerForm(page);
        navigationBar = new NavigationBar(page);
        page.navigate("https://practicesoftwaretesting.com");
    }

    @DisplayName("register new account")
    @Test
    public void registerNewAccount() {
        navigationBar.openRegistrationPage();
        customerForm.compileCustomerForm(
                "Mario",
                "Rossi",
                "1990-12-24",
                "Italy",
                "50139",
                "10D",
                "3332258701",
                "playwright.udemy.rossdv@gmail.com",
                "Mario@Rossi90"
        );
        customerForm.checkAutoFill();
        customerForm.submitRegistrationForm();
        customerForm.checkSuccessfulRegistration();
    }
}
