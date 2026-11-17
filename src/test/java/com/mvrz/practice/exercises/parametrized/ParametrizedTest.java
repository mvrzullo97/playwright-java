package com.mvrz.practice.exercises.parametrized;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.mvrz.practice.exercises.utils.HeadlessChromeOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UsePlaywright(HeadlessChromeOptions.class)
public class ParametrizedTest {

    @BeforeEach
    void openToolShopPage(Page page) {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @ParameterizedTest
    @DisplayName("test mandatory fields with params")
    @ValueSource(strings = {"First name", "Last name", "Email", "Message"})
    void checkMandatoryFields(String deletedField, Page page) {

        // go to Contact page
        page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main menu"))
                .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("Contact"))
                .click();

        // var init
        var firstName = page.getByLabel("First name");
        var lastName = page.getByLabel("Last name");
        var email = page.getByLabel("Email");
        var subject = page.getByLabel("Subject");
        var message = page.getByLabel("Message *");
        var sendButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send"));

        var alertMessage = page.getByRole(AriaRole.ALERT).getByText(deletedField + " is required");
        var deletedFieldLocator = page.getByLabel(deletedField);

        // fill fields
        firstName.fill("Alessio");
        lastName.fill("Marzullo");
        email.fill("alessio@nevergiveup.com");
        subject.selectOption("Webmaster");
        message.fill("Alessio never give up!! - Alessio never give up!!!");

        // delete one field
        deletedFieldLocator.clear();
        sendButton.click();
        Assertions.assertTrue(alertMessage.isVisible());

        }
    }
