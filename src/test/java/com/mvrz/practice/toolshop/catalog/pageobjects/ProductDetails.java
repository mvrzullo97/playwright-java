package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

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

    public void addProductInCart() {
        var addToCartBtn = page.getByTestId("add-to-cart");
        var toastMessage = page.getByRole(AriaRole.ALERT,
                new Page.GetByRoleOptions().setName(" Product added to shopping cart. "));
        addToCartBtn.click();
        PlaywrightAssertions.assertThat(toastMessage).isVisible();

    }

}
