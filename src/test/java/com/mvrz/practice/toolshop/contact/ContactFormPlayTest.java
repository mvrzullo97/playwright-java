package com.mvrz.practice.toolshop.contact;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.mvrz.practice.exercises.utils.HeadlessChromeOptions;
import com.mvrz.practice.toolshop.catalog.pageobjects.NavigationBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UsePlaywright(HeadlessChromeOptions.class)
public class ContactFormPlayTest {

    ContactForm contactForm;
    NavigationBar navigationBar;

    @BeforeEach
    void openToolShopPage(Page page) {
        contactForm = new ContactForm(page);
        navigationBar = new NavigationBar(page);

        page.navigate("https://practicesoftwaretesting.com");
    }

    @Test
    @DisplayName("when compiling a contact form correctly")
    void whenCompilingContactForm(Page page) {

        navigationBar.goToContactPage();
        contactForm.setFirstName("Alessio");
        contactForm.setLastName("Marzullo");
        contactForm.setEmail("alessio@gmail.com");
        contactForm.setMessage("A very long message: Never Give Up! Never Give Up! Never Give Up!");
        contactForm.setSubject("Payments");
        contactForm.submitForm();
        org.assertj.core.api.Assertions.assertThat(contactForm.getConfirmMessage())
                .contains(" Thanks for your message! We will contact you shortly. ");
    }

    @ParameterizedTest
    @DisplayName("test mandatory fields with parameters")
    @ValueSource(strings = {"First name", "Last name", "Email", "Message"})
    void checkMandatoryFields(String deletedField, Page page) {

        navigationBar.goToContactPage();

        contactForm.setFirstName("Alessio");
        contactForm.setLastName("Marzullo");
        contactForm.setEmail("alessio@gmail.com");
        contactForm.setMessage("A very long message: Never Give Up! Never Give Up! Never Give Up!");
        contactForm.setSubject("Payments");
        contactForm.clearField(deletedField);
        contactForm.submitForm();
        contactForm.checkMandatoryFieldMessage(deletedField);

    }
}
