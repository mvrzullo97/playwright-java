package com.mvrz.practice.wait;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.mvrz.practice.utils.HeadlessChromeOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(HeadlessChromeOptions.class)
public class WaitingTest {

    @BeforeEach
    void openToolShopPage(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @Test
    @DisplayName("waitFor")
    void waitFor(Page page) {

        var mainMenu = page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main menu"));
        var categoriesItem = page.getByRole(AriaRole.MENUITEM).getByText("Categories");
        var categoryItem = page.getByRole(AriaRole.MENUITEM).getByText("Power Tools");

        assertThat(mainMenu).isVisible();
        categoriesItem.click();
        categoryItem.click();

        // load the page and then retrieve the list of products
        page.waitForSelector(".card",
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        var filteredProducts = page.getByTestId("product-name").allInnerTexts();
        Assertions.assertThat(filteredProducts).contains("Sheet Sandler", "Belt Sander");

    }

    @Test
    @DisplayName("appear/disappear")
    void waitForToastMessage(Page page) {

        var productPliers = page.getByTestId("product-name").getByText("Combination Pliers");
        var imagePliers = page.locator(".figure-img.img-fluid");
        var cartButton = page.getByTestId("add-to-cart");

        productPliers.click();
        assertThat(imagePliers).isVisible();
        cartButton.click();

        assertThat(page.getByRole(AriaRole.ALERT)).isVisible();
        assertThat(page.getByRole(AriaRole.ALERT)).hasText("Product added to shopping cart.");

        page.waitForCondition(() -> page.getByRole(AriaRole.ALERT).isHidden());
        // check if the cart item counter updated
        page.waitForCondition(() -> page.getByTestId("cart-quantity").textContent().equals("1"));



    }

}
