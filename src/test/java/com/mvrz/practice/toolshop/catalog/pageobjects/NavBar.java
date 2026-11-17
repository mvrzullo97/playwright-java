package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class NavBar {
    private final Page page;

    public NavBar(Page page) {
        this.page = page;
    }

    public void openCart() {
        var cartIcon = page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main menu"))
                .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("cart"));
        cartIcon.click();
    }

    public void openContactPage() {
        page.getByTestId("nav-contact").click();
    }
}


