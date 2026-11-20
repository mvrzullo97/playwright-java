package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import org.assertj.core.api.Assertions;

public class FavouriteList {

    private final Page page;

    public FavouriteList(Page page) {this.page = page;}

    public void checkProductInList(String productName) {
        Assertions.assertThat(page.getByTestId("product-name").textContent()).
                isEqualTo(productName);
    }

}
