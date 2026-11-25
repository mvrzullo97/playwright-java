package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assertions;

import java.util.Objects;

public class SearchComponent {
    Page page;

    public SearchComponent(Page page) {
        this.page = page;
    }

    public void switchPageOfCatalog(String pageNumber) {
        page.locator(".page-item")
                .getByText(pageNumber)
                .click();
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
                        && response.request().postData() != null,
                        //&& Objects.requireNonNull(response.request().postData()).contains("is_rental"),
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

    public void selectPriceRange(int lowerBound, int upperBound) {

        int actualLowerBound = 1;
        int actualUpperBound = 100;
        String direction="";
        int offsetUpper = upperBound-actualUpperBound;
        int offsetLower = lowerBound-actualLowerBound;

        direction = offsetUpper > 0 ? "ArrowRight" : "ArrowLeft";
        for (int i=1; i <= offsetUpper; i++) {
            page.getByRole(AriaRole.SLIDER,
                    new Page.GetByRoleOptions().setName("ngx-slider-max")
                            .setExact(true)).press(direction);
        }
        for (int i=1; i <= offsetLower; i++) {
            page.getByRole(AriaRole.SLIDER,
                    new Page.GetByRoleOptions().setName("ngx-slider")
                            .setExact(true)).press("ArrowRight");
        }
    }
}
