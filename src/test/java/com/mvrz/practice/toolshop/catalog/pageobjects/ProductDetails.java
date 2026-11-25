package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;

import java.util.Objects;

public class ProductDetails {
    private final Page page;

    public ProductDetails(Page page) {
        this.page = page;
    }

    public void selectQuantity(int quantity) {
        var increaseQuantityBtn = page.getByTestId("increase-quantity");
        int i;
        for (i = 1; i < quantity; i++) {increaseQuantityBtn.click();}
    }

    public int getProductQuantityInTheCart() {
        return Integer.
                parseInt(Objects.requireNonNull(
                        page.getByTestId("cart-quantity").textContent())
                );
    }

    public void addProductInCart() {
        page.getByTestId("add-to-cart").click();
        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT,
                new Page.GetByRoleOptions().setName(" Product added to shopping cart. "))).isVisible();

    }

    public void addProductToFavouriteList() {
        page.getByTestId("add-to-favorites").click();
        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT,
                new Page.GetByRoleOptions()
                        .setName(" Product added to your favorites list.")))
                .isVisible();
    }

    public String getProductName() {
        return Objects.requireNonNull(page.getByTestId("product-name")
                .textContent()).strip();
    }

    public void checkOutOfStockProduct() {
        PlaywrightAssertions.assertThat(page.getByTestId("add-to-cart")).isDisabled();
    }

    public String getCo2ActiveRate() {
        return page.locator(".co2-letter.active").textContent();
    }

    public boolean isCartIconVisible() {
        return page.getByTestId("cart-quantity").isVisible();
    }

    public void deleteProductFromTheCart() {
        page.waitForResponse(response -> response.url().contains("/carts")
                && response.status() == 200
                && response.request().method().equals("GET"),
                () -> {
                    page.locator(".btn-danger").click();
                });
        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT,
                new Page.GetByRoleOptions().setName("Product deleted."))).isVisible();
        PlaywrightAssertions.assertThat(page.locator(".ng-star-inserted")
                .getByText("The cart is empty. Nothing to display."))
                .isVisible();
    }

    public boolean productIsPresentInTheCart(String productName) {
        return page.getByTestId("product-title").getByText(productName).isVisible();
    }
}
