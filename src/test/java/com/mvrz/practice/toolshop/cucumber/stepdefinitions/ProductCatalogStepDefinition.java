package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.mvrz.practice.toolshop.catalog.pageobjects.NavigationBar;
import com.mvrz.practice.toolshop.catalog.pageobjects.ProductList;
import com.mvrz.practice.toolshop.catalog.pageobjects.SearchComponent;
import com.mvrz.practice.toolshop.domain.ProductSummary;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import java.util.List;
import java.util.Map;

public class ProductCatalogStepDefinition {

    NavigationBar navigationBar;
    SearchComponent searchComponent;
    ProductList productList;

    @Before
    public void setupPageObjects() {
        navigationBar = new NavigationBar(PlaywrightCucumberFixtures.getPage());
        searchComponent = new SearchComponent(PlaywrightCucumberFixtures.getPage());
        productList = new ProductList(PlaywrightCucumberFixtures.getPage());
    }

    @Given("Alessio is on the Home Page")
    public void AlessioIsOnTheHomePage() {
        navigationBar.openHomePage();
    }

    @When("he searches for {string}")
    public void heSearchesFor(String searchTerm) {
        searchComponent.searchBy(searchTerm);
    }

    @Then("the {string} product should be displayed")
    public void theProductShouldBeDisplayed(String productName) {
        List<String> matchingResults = productList.getProductNames();
        Assertions.assertThat(matchingResults).contains(productName);
    }

    @DataTableType
    public ProductSummary productSummaryRow(Map<String, String> productData) {
        return new ProductSummary(productData.get("Product"), productData.get("Price"));
    };

    @Then("the following products should be displayed:")
    public void theFollowingProductsShouldBeDisplayed(List<ProductSummary> expectedProductSummaries) {
        List<ProductSummary> matchingResults = productList.getProductSummaries();
        Assertions.assertThat(matchingResults).containsExactlyElementsOf(expectedProductSummaries);
        }

    @Then("no product should be displayed")
    public void noProductShouldBeDisplayed() {
        List<String> matchingResults = productList.getProductNames();
        Assertions.assertThat(matchingResults).isEmpty();
    }

    @And("the message {string} should be displayed")
    public void theMessageShouldBeDisplayed(String messageProductNotFound) {
        String messageRetrieved = productList.getSearchMessageCompletedMessage();
        Assertions.assertThat(messageRetrieved).isEqualTo(messageProductNotFound);
    }

    @And("he filters by {string}")
    public void heFiltersBy(String filterName) {
        searchComponent.filterBy(filterName);
    }

    @When("he sorts by {string}")
    public void heSortsBy(String sortFilter) {
        searchComponent.sortBy(sortFilter);
    }

    @Then("the first product displayed should be {string}")
    public void theFirstProductDisplayedShouldBeFirstProduct(String firstProductName) {
        List<String> matchingResults = productList.getProductNames();
        Assertions.assertThat(matchingResults).startsWith(firstProductName);
    }

    @And("he clears the research")
    public void heClearsTheResearch() {
        searchComponent.clearSearch();
    }

    @Then("the number of product displayed should be {int}")
    public void theNumberOfProductDisplayedShouldBe(int expectedNumberOfElements) {
        List<String> matchingResults = productList.getProductNames();
        org.junit.jupiter.api.Assertions.assertEquals(expectedNumberOfElements, matchingResults.size());
    }

    @When("he switches to page {string}")
    public void heSwitchesToPage(String pageNumber) {
        searchComponent.switchPageOfCatalog(pageNumber);
    }
}
