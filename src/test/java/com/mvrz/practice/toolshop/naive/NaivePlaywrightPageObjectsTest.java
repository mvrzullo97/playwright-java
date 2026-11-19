package com.mvrz.practice.toolshop.naive;

import com.mvrz.practice.toolshop.fixtures.PlaywrightTestCase;
import com.mvrz.practice.toolshop.catalog.pageobjects.NavigationBar;
import com.mvrz.practice.toolshop.catalog.pageobjects.ProductDetails;
import com.mvrz.practice.toolshop.catalog.pageobjects.ProductList;
import com.mvrz.practice.toolshop.catalog.pageobjects.SearchComponent;
import org.junit.jupiter.api.BeforeEach;

public class NaivePlaywrightPageObjectsTest extends PlaywrightTestCase {

    SearchComponent searchComponent;
    ProductList productList;
    ProductDetails productDetails;
    NavigationBar navigationBar;

    @BeforeEach
    void openToolShopPage() {page.navigate("https://practicesoftwaretesting.com");}
/*
    @Test
    @DisplayName("Add Item without POM")
    void addItemNoPOM() {
        searchComponent = new SearchComponent(page);
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
    void addItemPOM() {
        searchComponent = new SearchComponent(page);
        productList = new ProductList(page);
        productDetails = new ProductDetails(page);
        navBar = new NavBar(page);

        searchComponent.searchBy("pliers");
        productList.selectProductFromList("Pliers");
        productDetails.selectQuantity(2);
        productDetails.addProductInCart();
        navBar.openCart();
    }
*/
}
