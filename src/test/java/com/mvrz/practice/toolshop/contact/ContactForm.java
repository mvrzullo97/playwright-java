package com.mvrz.practice.toolshop.contact;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ContactForm {

    private final Page page;

    private final Locator firstName;
    private final Locator lastName;
    private final Locator email;
    private final Locator subject;
    private final Locator message;

    public ContactForm(Page page) {
        this.page = page;
        this.firstName = page.getByLabel("First name");
        this.lastName = page.getByLabel("Last name");
        this.email = page.getByLabel("Email");
        this.subject = page.getByLabel("Subject");
        this.message = page.getByLabel("Message *");
    }

    public void setFirstName(String firstName) {
        this.firstName.fill(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.fill(lastName);
    }

    public void setEmail(String email) {
        this.email.fill(email);
    }

    public void setSubject(String subject) {this.subject.selectOption(subject);}

    public void setMessage(String message) {this.message.fill(message);}

    public void submitForm() {
        page.locator(".btnSubmit").click();
    }

    public String getConfirmMessage() {
        return page.getByRole(AriaRole.ALERT).textContent();
    }

    public void clearField(String deletedField) {
        page.getByLabel(deletedField).clear();
    }

    public void checkMandatoryFieldMessage(String deletedField) {
        page.getByRole(AriaRole.ALERT).getByText(deletedField + " is required");
    }

}
