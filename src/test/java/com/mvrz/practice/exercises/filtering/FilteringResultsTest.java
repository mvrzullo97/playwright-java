package com.mvrz.practice.exercises.filtering;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.mvrz.practice.exercises.utils.HeadlessChromeOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@UsePlaywright(HeadlessChromeOptions.class)
public class FilteringResultsTest {

    @BeforeEach
    void openToolShopPage(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @Test
    @DisplayName("filter results")
    void getAllProductsThatOutOfStock(Page page) {
        Assertions.assertTrue(page.getByRole(AriaRole.MENUBAR).isVisible());
        List<String> outOfStockProds = page.locator(".card")
                .filter(new Locator.FilterOptions().setHas(page.getByText("Out of stock")))
                .getByTestId("product-name")
                .allTextContents();
        Assertions.assertFalse(outOfStockProds.isEmpty());


    }

    @Test
    @DisplayName("check the result")
    void searchByKeywordAndCheckResults(Page page) {
        Assertions.assertTrue(page.getByRole(AriaRole.MENUBAR).isVisible());
        page.getByTestId("search-query").fill("Pliers");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search ")).click();

        page.waitForSelector(".card-img-top");

        PlaywrightAssertions.assertThat(page.locator(".card")).hasCount(4);
        List<String> productNames = page.getByTestId("product-name").allTextContents();
        assertThat(productNames).allMatch(name -> name.contains("Pliers"));
        Locator outOfStockItem = page.locator(".card")
                .filter(new Locator.FilterOptions().setHasText("Out of stock"))
                .getByTestId("product-name");
        PlaywrightAssertions.assertThat(outOfStockItem).hasText("Long Nose Pliers");

    }

    @Test
    @DisplayName("filtering results and check the Co2 class")
    void retrieveItemsWithCo2ClassC(Page page) {

        var checkBoxCategory =  page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(" Hand Tools "));
        var page3Items = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("3"));
        var page3Active = page.locator("li.page-item.active button[aria-label='Page-3']");

        checkBoxCategory.click();
        Assertions.assertTrue(checkBoxCategory.isChecked());
        page3Items.click();

        Assertions.assertTrue(page3Active.isVisible());

        List<String> itemsRetrieved = page.locator(".card")
                .filter(new Locator.FilterOptions().setHas(page.locator("span.co2-letter.active.rating-c")))
                .getByTestId("product-name")
                .allTextContents();
        Assertions.assertFalse(itemsRetrieved.isEmpty());
    }
}
