package com.mvrz.practice.locator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
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

    @Test
    @DisplayName("locator -> getByRole")
    void locatorByRole(Page page) {
        Assertions.assertTrue(page.getByRole(AriaRole.MENUBAR).isVisible());

        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Hand Tools")).check();

        Assertions.assertTrue(page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Hammer")).isChecked());
        Assertions.assertFalse(page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Fasteners")).isChecked());

        page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Contact")).click();
        page.getByLabel("First name").fill("Alessio");
        page.getByPlaceholder("Your last name *").fill("Marzullo");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send")).click();

    }

    @Test
    @DisplayName("locator -> getByTestId")
    void locatorByTestId(Page page) {
        page.getByTestId("search-query").fill("Hammer");
        page.getByTestId("search-submit").click();
        //bisogna attendere qualche istante -> si vedrà più in avanti sul waiting
        String text = page.getByTestId("search-result-count").textContent();
        Assertions.assertEquals(" 6 products found for 'Hammer' ", text);
    }

    @Test
    @DisplayName("nested locators")
    void locateNestedElements(Page page) {
        page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu"))
                .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("Contact"))
                .click();

    }

    @Test
    @DisplayName("interacting with dropdowns")
    void interactWithDropdownMenu(Page page) {
        var dropdownMenu = page.getByTestId("sort");
        dropdownMenu.selectOption("Name (A - Z)");
    }

    @Test
    @DisplayName("test contact page using some diff locators")
    void compileContactForm(Page page) {
        // AriaRole to locate menu bar and menu item
        Assertions.assertTrue(page.getByRole(AriaRole.MENUBAR).isVisible());
        // nested AriaRole locator
        page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu"))
                        .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("Contact")).click();
        // byPlaceholder to locate first name field
        page.getByPlaceholder("Your first name *").fill("Alessio");
        // byLabel to locate last name field
        page.getByLabel("Last name").fill("Marzullo");
        // byCSS to locate email field
        page.locator("#email").fill("alessio@nevergiveup.com");
        // byTestId to locate Message field
        page.getByTestId("message").fill("Never Give Up!");
        // SelectOption byLabel
        page.getByLabel("subject").selectOption("Payments");
        // by CSS
        page.locator(".btnSubmit").click();
    }

}
