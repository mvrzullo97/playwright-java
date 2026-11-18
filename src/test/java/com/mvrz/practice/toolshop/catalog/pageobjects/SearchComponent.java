package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

import java.util.Objects;

public class SearchComponent {
    Page page;

    public SearchComponent(Page page) {
        this.page = page;
    }

    public void searchBy(String keyword) {

        var searchBar = page.getByPlaceholder("Search");
        var searchBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));

        page.waitForResponse(
                response -> response.url().endsWith("/products/search")
                        && response.request().postData() != null
                        && response.request().postData().contains("\"" + keyword + "\""),
                () -> {
                    searchBar.fill(keyword);
                    searchBtn.click();
                });
    }

    public void clearSearch() {
        page.waitForResponse(
                response -> response.url().endsWith("/products")
                        && response.request().postData() != null
                        && Objects.requireNonNull(response.request().postData()).contains("is_rental"),
                () -> {
                    page.getByTestId("search-reset").click();
                });
    }

    public void filterBy(String filterName) {
        page.waitForResponse(
                response -> response.url().endsWith("/products")
                        && response.request().postData() != null
                        && Objects.requireNonNull(response.request().postData()).contains("by_category"),
                () -> {
                    page.getByLabel(filterName).click();
                });
    }

    public void sortBy(String sortFilter) {
        page.waitForResponse(
                response -> response.url().endsWith("/products")
                        && response.request().postData() != null
                        && Objects.requireNonNull(response.request().postData()).contains("sort"),
                () -> {
                    page.getByTestId("sort").selectOption(sortFilter);
                });
    }
}
