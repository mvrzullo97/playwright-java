package com.mvrz.practice.toolshop.catalog;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.mvrz.practice.exercises.utils.HeadlessChromeOptions;
import com.mvrz.practice.toolshop.catalog.pageobjects.NavBar;
import com.mvrz.practice.toolshop.catalog.pageobjects.ProductList;
import com.mvrz.practice.toolshop.catalog.pageobjects.SearchComponent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@UsePlaywright(HeadlessChromeOptions.class)
public class SearchForProductsPlayTest {

    SearchComponent searchComponent;
    ProductList productList;
    NavBar navBar;

    @BeforeEach
    void openToolShopPage(Page page) {
        searchComponent = new SearchComponent(page);
        productList = new ProductList(page);
        navBar = new NavBar(page);
        page.navigate("https://practicesoftwaretesting.com");
    }

    @DisplayName("when there are matching results")
    @Test()
    public void whenSearchingByKeyword() {
        searchComponent.searchBy("Hammer");
        List<String> matchingResults = productList.getProductNames();
        Assertions.assertThat(matchingResults).contains("Hammer", "Claw Hammer", "Court Hammer");
    }

    @DisplayName("when there are NOT matching results")
    @Test
    public void whenThereIsNotMatchingResults() {
        searchComponent.searchBy("Unknown");
        Assertions.assertThat(productList.getProductNames()).isEmpty();
        Assertions.assertThat(productList.getSearchMessageCompletedMessage())
                .contains("There are no products found.");
    }

    @DisplayName("when we want clear the research")
    @Test
    public void whenClearingTheSearchResults() {
        searchComponent.searchBy("screws");
        searchComponent.clearSearch();
        List<String> matchingResults = productList.getProductNames();
        Assertions.assertThat(matchingResults).hasSize(9);
    }

    @DisplayName("dumb test to verify some stuffs")
    @Test
    public void openTheHomePage() {
        navBar.openHomePage();
        //searchComponent.filterBy("Hand Saw");
        searchComponent.switchPageOfCatalog("2");
    }

}
