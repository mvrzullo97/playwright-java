package com.mvrz.practice.toolshop.catalog.pageobjects;

import com.microsoft.playwright.Page;

public class NavigationBar {

    private final Page page;

    public NavigationBar(Page page) {this.page = page;}

    public void goToCart() {
        page.getByTestId("nav-cart").click();}

    public void goToContactPage() {
        page.getByTestId("nav-contact").click();
    }

    public void goToLogInPage() {
        page.getByTestId("nav-sign-in").click();
    }

    public void goToRegistrationPage() {
        goToLogInPage();
        page.getByTestId("register-link").click();
    }

    public void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
    }

    public void goToHomePage() {
        page.getByTestId("nav-home").click();
    }

    public void goToFavouriteList() {
        page.getByTestId("nav-menu").click();
        page.getByTestId("nav-my-favorites").click();
    }

    public String getActualPage() {return page.url();}

}


