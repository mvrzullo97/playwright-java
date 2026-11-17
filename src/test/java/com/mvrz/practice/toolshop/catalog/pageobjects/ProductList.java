package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class ProductList {
    private final Page page;

    public ProductList(Page page) {
        this.page = page;
    }

    public void selectProductFromList(String productName) {
        var selectedItem = page.locator(".card-title").getByText(productName,
                new Locator.GetByTextOptions().setExact(true));
        selectedItem.click();
    }

    public List<String> getProductNames() {
        return page.getByTestId("product-name").allInnerTexts();
    }

    public String getSearchMessageCompletedMessage() {
        return page.getByTestId("no-results").textContent();
    }
}
