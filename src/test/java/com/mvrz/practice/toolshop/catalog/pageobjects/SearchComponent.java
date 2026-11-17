package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

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
        page.waitForResponse("**/products**", () -> {
            page.getByTestId("search-reset").click();
        });
    }
}
