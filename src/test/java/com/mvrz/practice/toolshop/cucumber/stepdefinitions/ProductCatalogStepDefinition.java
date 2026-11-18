package com.mvrz.practice.toolshop.cucumber.stepdefinitions;

import com.mvrz.practice.toolshop.catalog.pageobjects.NavBar;
import com.mvrz.practice.toolshop.catalog.pageobjects.ProductList;
import com.mvrz.practice.toolshop.catalog.pageobjects.SearchComponent;
import com.mvrz.practice.toolshop.domain.ProductSummary;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Data;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

public class ProductCatalogStepDefinition {

    NavBar navBar;
    SearchComponent searchComponent;
    ProductList productList;

    @Before
    public void setupPageObjects() {
        navBar = new NavBar(PlaywrightCucumberFixtures.getPage());
        searchComponent = new SearchComponent(PlaywrightCucumberFixtures.getPage());
        productList = new ProductList(PlaywrightCucumberFixtures.getPage());
    }

    @Given("Alessio is on the Home Page")
    public void alessio_is_on_the_home_page() {
        navBar.openHomePage();
    }

    @When("he searches for {string}")
    public void he_searches_for(String searchTerm) {
        searchComponent.searchBy(searchTerm);
    }

    @Then("the {string} product should be displayed")
    public void the_product_should_be_displayed(String productName) {
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

}
