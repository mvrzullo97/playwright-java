package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;

public class CheckoutCart {
    private final Page page;

    public CheckoutCart(Page page) {
        this.page = page;
    }

    public String checkOutItem() {
        return page.getByTestId("product-title")
                .innerText()
                .replaceAll("[\\s\\u00A0]+$", "");
    }
}
