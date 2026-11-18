package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.mvrz.practice.toolshop.domain.ProductSummary;

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

    public List<ProductSummary> getProductSummaries() {
        return page.locator(".card").all()
                .stream()
                .map(productCard -> {
                    String productName = productCard.getByTestId("product-name").textContent().strip();
                    String productPrice = productCard.getByTestId("product-price").textContent();
                    return new ProductSummary(productName, productPrice);
                }).toList();

    }
}
