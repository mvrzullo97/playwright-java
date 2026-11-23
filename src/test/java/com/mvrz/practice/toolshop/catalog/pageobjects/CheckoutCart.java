package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;

public class CheckoutCart {
    private final Page page;

    public CheckoutCart(Page page) {
        this.page = page;
    }

    public boolean checkCurrentPhase(String phase) {
        return page.locator("li.current")
                .filter(new Locator.FilterOptions().setHasText(phase))
                .count() == 1;
    }

    public String checkOutItem() {
        return page.getByTestId("product-title")
                .innerText()
                .replaceAll("[\\s\\u00A0]+$", "");
    }

    public void clickCheckOutButton() {
        Locator checkoutButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Proceed to checkout"))
                        .and(page.locator(":not([disabled])"));
        checkoutButton.click();
    }

    public String getMessage() {
        return page.locator("p.ng-star-inserted").textContent();
    }

    public void checkLogInForm() {
        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.TABPANEL)
                .and(page.locator("#signin-tab")))
                .isVisible();
    }

    public void compileForm(String email, String firstName, String lastName) {

        page.getByRole(AriaRole.TAB,
                new Page.GetByRoleOptions().setName("Continue as Guest"))
                .click();

        // per evitare il conflitto con il sign-in form
        Locator guestForm = page.locator("#guest-tab");
        guestForm.getByPlaceholder("Your email").fill(email);
        guestForm.getByPlaceholder("Your first name").fill(firstName);
        guestForm.getByPlaceholder("Your last name").fill(lastName);
    }

    public void submitForm() {
        page.getByTestId("guest-submit").click();
    }

    public void clearFieldName(String fieldName) {
        Locator guestForm = page.locator("#guest-tab");
        guestForm.getByPlaceholder(fieldName).clear();
    }

    public String getAlertMessage() {
        return page.locator(".alert.alert-danger").textContent();
    }

    public void checkAddressAutoFill() {
        Assertions.assertFalse(page.getByLabel("Street").inputValue().isBlank());
        Assertions.assertFalse(page.getByLabel("City").inputValue().isBlank());
        Assertions.assertFalse(page.getByLabel("State").inputValue().isBlank());
    }

    public void compileBillingForm(String country, String postalCode, String houseNumber) {
        page.getByLabel("Country").selectOption(country);
        page.getByLabel("Postal code").fill(postalCode);
        page.waitForResponse(response -> response.url().contains("/postcode-lookup")
        && response.status() == 200, () -> {
            page.getByLabel("House number").fill(houseNumber);
        });
        checkAddressAutoFill();
    }

    public void clickOnConfirmButton() {
        page.waitForResponse(response -> response.url().contains("/carts/")
                && response.status() == 204
                && response.request().method().equals("DELETE"), () -> {
            page.getByTestId("finish").getByText(" Confirm ").click();
        });
    }

    public void submitPaymentForm() {
        page.getByTestId("finish").getByText(" Check payment ").click();
    }

    public void checkPaymentMessage() {
        PlaywrightAssertions.assertThat(page.getByTestId("payment-success-message")).isVisible();
    }

    public String getConfirmationMessage() {
        return page.locator("#order-confirmation").textContent();
    }

    public void compilePaymentMethod(String paymentMethod) {

        page.getByTestId("payment-method").selectOption(paymentMethod);

        switch (paymentMethod) {
            case "Bank Transfer":
                compileBankTransferOpt();
                break;
            case "Cash on Delivery":
                break;
            case "Credit Card":
                compileCreditCardOpt();
                break;
            case "Buy Now Pay Later":
                compileBuyNowPayLaterOpt();
                break;
            case "Gift Card":
                compileGiftCardOpt();
                break;
        }
        submitPaymentForm();
        checkPaymentMessage();
    }

    private void compileGiftCardOpt() {
        page.getByPlaceholder("Gift Card Number").fill("AAAA0000BBBB1111");
        page.getByPlaceholder("Validation Code").fill("ABCD");
    }

    private void compileBuyNowPayLaterOpt() {
        page.getByTestId("monthly_installments").selectOption("12 Monthly Installments");
    }

    private void compileCreditCardOpt() {
        page.getByPlaceholder("Credit Card Number").fill("0000-1111-2222-3333");
        page.getByPlaceholder("Expiration Date").fill("12/2027");
        page.getByPlaceholder("CVV").fill("1234");
        page.getByPlaceholder("Card Holder Name").fill("Random Customer");
    }

    private void compileBankTransferOpt() {
        page.getByPlaceholder("Bank Name").fill("Random Bank");
        page.getByPlaceholder("Account Name").fill("Random Account");
        page.getByPlaceholder("Account Number").fill("12345");
    }
}
