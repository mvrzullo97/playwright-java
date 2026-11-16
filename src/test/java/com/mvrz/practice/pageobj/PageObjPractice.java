package com.mvrz.practice.pageobj;

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
public class PageObjPractice {

    SearchComponent searchComponent;
    ProductList productList;
    ProductDetails productDetails;

    @BeforeEach
    void openToolShopPage(Page page) {
        searchComponent = new SearchComponent(page);
        productList = new ProductList(page);
        productDetails = new ProductDetails(page);
        page.navigate("https://practicesoftwaretesting.com");
    }

    @Test
    @DisplayName("Add Item without POM")
    void addItemNoPOM(Page page) {

        var searchBar = page.getByPlaceholder("Search");
        var searchBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));

        var selectedItem = page.locator(".card-title").getByText("Pliers",
                new Locator.GetByTextOptions().setExact(true));

        var increaseQuantityBtn = page.getByTestId("increase-quantity");
        var addToCartBtn = page.getByTestId("add-to-cart");
        var toastMessage = page.getByRole(AriaRole.ALERT,
                new Page.GetByRoleOptions().setName(" Product added to shopping cart. "));
        var cartQuantity = page.getByTestId("cart-quantity");
        var cartIcon = page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main menu"))
                .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("cart"));
        var item = page.getByTestId("product-title").getByText("Pliers&nbsp;");

        //search component
        searchBar.fill("pliers");
        searchBtn.click();
        //product details
        PlaywrightAssertions.assertThat(selectedItem).isVisible();
        selectedItem.click();
        PlaywrightAssertions.assertThat(addToCartBtn).isVisible();
        increaseQuantityBtn.click();
        increaseQuantityBtn.click();
        //add to cart
        addToCartBtn.click();
        PlaywrightAssertions.assertThat(toastMessage).isVisible();
        PlaywrightAssertions.assertThat(cartQuantity).hasText("3");
        PlaywrightAssertions.assertThat(cartIcon).isVisible();
        cartIcon.click();
        PlaywrightAssertions.assertThat(item).isVisible();
    }

    @Test
    @DisplayName("Add Item with POM")
    void addItemPOM(Page page) {

        searchComponent.searchBy("pliers");
        productList.selectProductFromList("Pliers");
        productDetails.increaseQuantityBy(2);
        productDetails.addProductInCart();
    }

    class SearchComponent {
        private final Page page;

        SearchComponent(Page page) {
            this.page = page;
        }

        public void searchBy(String keyword) {
            var searchBar = page.getByPlaceholder("Search");
            var searchBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));

            searchBar.fill(keyword);
            searchBtn.click();
        }
    }

    class ProductList {
        private final Page page;

        ProductList(Page page) {
            this.page = page;
        }

        public void selectProductFromList(String productName) {
            var selectedItem = page.locator(".card-title").getByText(productName,
                    new Locator.GetByTextOptions().setExact(true));
            selectedItem.click();
        }
    }

    class ProductDetails {
        private final Page page;

        ProductDetails(Page page) {
            this.page = page;
        }

        public void increaseQuantityBy (int increase) {
            var increaseQuantityBtn = page.getByTestId("increase-quantity");
            int i;
            for (i = 1; i <= increase; i++) {increaseQuantityBtn.click();}
        }

        public void addProductInCart() {
            var addToCartBtn = page.getByTestId("add-to-cart");
            var toastMessage = page.getByRole(AriaRole.ALERT,
                    new Page.GetByRoleOptions().setName(" Product added to shopping cart. "));
            addToCartBtn.click();
            PlaywrightAssertions.assertThat(toastMessage).isVisible();

        }
    }

    class NavBar {
        private final Page page;

        NavBar(Page page) {
            this.page = page;
        }

        public void openCart() {
            var cartIcon = page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main menu"))
                    .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("cart"));
            cartIcon.click();
        }
    }


}
