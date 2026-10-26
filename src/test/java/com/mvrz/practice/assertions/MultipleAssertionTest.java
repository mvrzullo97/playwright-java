package com.mvrz.practice.assertions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.mvrz.practice.utils.HeadlessChromeOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(HeadlessChromeOptions.class)
public class MultipleAssertionTest {

    @BeforeEach
    void openToolShopPage(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @Test
    @DisplayName("Playwright assertions: assertThat")
    void playwrightAssertThat(Page page) {

        page.getByPlaceholder("Search").fill("Pliers");
        page.getByTestId("search-submit").click();

        var searchResults = page.getByTestId("search-result-count");
        var outOfStockItem = page.getByTestId("out-of-stock");
        // PlaywrightAssertion -> Playwright object
        PlaywrightAssertions.assertThat(searchResults).hasText(" 4 products found for 'Pliers' ");
        PlaywrightAssertions.assertThat(outOfStockItem).isVisible();

    }

    @Test
    @DisplayName("assertJ")
    void assertJ(Page page) {

        var searchField = page.getByTestId("search-query");
        var searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));
        List<String> products = page.getByTestId("product-name").allTextContents();
        searchField.fill("Hammer");
        searchButton.click();
        //assertJ -> general java object
        Assertions.assertThat(products).allMatch(prod -> prod.contains("Hammer"));


    }

    @Test
    @DisplayName("assertJ && PlaywrightAssertion")
    void assertContactForm(Page page) {

        var contactPage = page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Contact"));
        var firstNameField = page.getByLabel("First name");

        contactPage.click();
        firstNameField.fill("Alessio");

        // check the state of a field
        assertThat(firstNameField).hasValue("Alessio");
        assertThat(firstNameField).not().isDisabled();
        assertThat(firstNameField).isVisible();
        assertThat(firstNameField).isEditable();
    }

    @Test
    @DisplayName("assertJ some stuffs")
    void assertProductPrices(Page page) {

        var searchField = page.getByTestId("search-query");
        var searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));

        searchField.fill("Hammer");
        searchButton.click();

        List<Double> productPrices = page.getByTestId("product-price")
                .allInnerTexts()
                .stream()
                .map(price -> Double.parseDouble(price.replace("$", " ")))
                .toList();

        Assertions.assertThat(productPrices)
                .isNotEmpty()
                .allMatch(price -> price > 0)
                .doesNotContain(0.0)
                .allMatch(price -> price < 10000);
    }


    @Test
    @DisplayName("assertJ sorting")
    void assertAlphaOrder(Page page) {

        var dropdownMenu = page.getByTestId("sort");

        dropdownMenu.selectOption("Name (A - Z)");
        page.waitForLoadState(LoadState.NETWORKIDLE);

        List<String> productNames = page.getByTestId("product-names").allTextContents();

        Assertions.assertThat(productNames)
                .isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);

    }
}
