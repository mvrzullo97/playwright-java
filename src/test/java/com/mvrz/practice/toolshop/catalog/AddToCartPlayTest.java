package com.mvrz.practice.toolshop.catalog;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.mvrz.practice.exercises.utils.HeadlessChromeOptions;
import com.mvrz.practice.toolshop.catalog.pageobjects.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@UsePlaywright(HeadlessChromeOptions.class)
public class AddToCartPlayTest {
    SearchComponent searchComponent;
    ProductList productList;
    ProductDetails productDetails;
    NavBar navBar;
    CheckoutCart checkoutCart;

    @BeforeEach
    void openToolShopPage(Page page) {
        searchComponent = new SearchComponent(page);
        productList = new ProductList(page);
        productDetails = new ProductDetails(page);
        navBar = new NavBar(page);
        checkoutCart = new CheckoutCart(page);

        page.navigate("https://practicesoftwaretesting.com");
    }

    @DisplayName("when checking out an item in the cart")
    @Test()
    public void whenCheckingOutItemInCart(Page page) {
        searchComponent.searchBy("Hammer");
        productList.selectProductFromList("Claw Hammer");
        productDetails.selectQuantity(3);
        productDetails.addProductInCart();

        var actQuantity = page.getByTestId("cart-quantity").textContent();
        Assertions.assertEquals(3, Integer.parseInt(actQuantity));

        navBar.openCart();
        String actItemInCart = checkoutCart.checkOutItem();
        org.assertj.core.api.Assertions.assertThat(actItemInCart).isEqualTo("Claw Hammer");
    }

    @DisplayName("when trying to add an item out of stock in the cart")
    @Test()
    public void whenItemOutOfStock(Page page) {
        searchComponent.searchBy("long nose pliers");
        productList.selectProductFromList("Long Nose Pliers");
        PlaywrightAssertions.assertThat(page.getByTestId("out-of-stock")).isVisible();
    }

}
