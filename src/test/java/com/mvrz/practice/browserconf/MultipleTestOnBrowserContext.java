package com.mvrz.practice.browserconf;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class MultipleTestOnBrowserContext {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    private static Page page;

    @BeforeAll
    static void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
        );
        browserContext = browser.newContext();
    }

    @BeforeEach
    void setUp() {
        page = browserContext.newPage();
    }

    @AfterAll
    static void teardown() {
        browser.close();
        playwright.close();
    }


    @Test
    void shouldShowThePageTitle() {

        page.navigate("https://practicesoftwaretesting.com");
        String title = page.title();
        Assertions.assertTrue(title.contains("Practice Software Testing"));
    }

    @Test
    void shouldSearchByKeyword() {

        page.navigate("https://practicesoftwaretesting.com");
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();
        int matchingResults = page.locator(".card").count();
        Assertions.assertTrue(matchingResults > 0);
    }
}
