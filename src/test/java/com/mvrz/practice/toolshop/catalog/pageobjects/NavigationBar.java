package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;

public class NavigationBar {

    private final Page page;

    public NavigationBar(Page page) {this.page = page;}

    public void openCart() {page.getByTestId("nav-cart").click();}

    public void openContactPage() {
        page.getByTestId("nav-contact").click();
    }

    public void openSignInPage() {page.navigate("https://practicesoftwaretesting.com/auth/login");}

    public void openRegistrationPage() {page.navigate("https://practicesoftwaretesting.com/auth/register");}

    public void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
    }

    public String getActualPage() {return page.url();}

}


