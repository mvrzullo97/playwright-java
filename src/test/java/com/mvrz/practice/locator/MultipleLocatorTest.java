package com.mvrz.practice.locator;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.mvrz.practice.utils.HeadlessChromeOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@UsePlaywright(HeadlessChromeOptions.class)
public class MultipleLocatorTest {

    @BeforeEach
    void openToolShopPage(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
    }


    @Test
    @DisplayName("locator -> getByText")
    void locatorByText(Page page) {
        page.locator("input[placeholder='Search']").fill("Hammer");
        page.locator("button:has-text('Search')").click();
        page.getByText("Thor Hammer").click();
        PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools ")).isVisible();
    }

    @Test
    @DisplayName("locator -> getByPlaceholder")
    void locatorByPlaceholder(Page page) {
        page.getByPlaceholder("Search").fill("Cutters");
        page.locator("button:has-text('Search')").click();
        int countingResults = page.locator(".card-img-top").count();
        Assertions.assertTrue(countingResults > 0);
    }

    @Test
    @DisplayName("locator -> getByLabel")
    void locatorByLabel(Page page) {
        page.getByText("Contact").click();
        page.getByLabel("First name").fill("Alessio");
        page.getByLabel("Last name").fill("Marzullo");
        page.getByPlaceholder("Your email *").fill("alessio@gmail.com");

    }
}
